package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.common.OrderHistoryDto;
import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.PartInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.entity.*;
import com.union.placeorderAutomation.repository.BomRepository;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.common.CommonService;
import com.union.placeorderAutomation.service.common.PartLogService;
import com.union.placeorderAutomation.service.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OutgoingService {

    private final RestTemplateService restTemplateService;
    private final CommonService commonService;
    private final PartLogService partLogService;
    private final BomRepository bomRepo;
    private final PartRepository partRepo;
    private final PartInventoryRepository partInventoryRepo;

    @Transactional(readOnly = true)
    public int findPartStock(String bwCode) {
        return partInventoryRepo.sumPartStock(bwCode).intValue();
    }

    //일반 납품
    @Transactional(readOnly = true)
    public List<ProductPlanDto> findPlanAndInventory(String companyCode, String plantCode) {
        // 재고 찾기(공장) -> 회사 전체 부품으로 걸러냄
        HashMap<String, PartInventoryDto> partInvenMap = restTemplateService.getPartInventory(companyCode, plantCode);
        List<Part> findPartList = partRepo.findPartByCompany(companyCode);
        HashMap<String, PartInventoryDto> partInvenResult = new HashMap<>();
        for (Part part : findPartList) {
            String bwCode = part.getBwCode();
            if (partInvenMap.containsKey(bwCode)) {
                PartInventoryDto partInventoryDto = partInvenMap.get(bwCode);
                partInventoryDto.setSpCode(part.getSpCode());
                partInventoryDto.setLoadAmount(part.getLoadAmount());
                partInvenResult.put(bwCode, partInventoryDto);
            }
        }

        // 생산계획(BOM) 찾기(공장, 라인으로 검색)
        HashMap<String, ProductPlanDto> planBomMap = new HashMap<>();
        planBomMap.putAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CS"));
        planBomMap.putAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CP"));
        planBomMap.putAll(restTemplateService.getProductPlanning(companyCode, plantCode, "MC"));
        // 회사 제품이 들어간 BOM 필터링
        List<Bom> findBomList = bomRepo.findByCompanyCode(Company.builder().companyCode(companyCode).build());
        List<ProductPlanDto> planBomResult = new ArrayList<>();
        for (Bom bom : findBomList) {
            String bomBwCode = bom.getBwCode();
            if (planBomMap.containsKey(bomBwCode)) {
                ProductPlanDto productPlanDto = planBomMap.get(bomBwCode);
                List<BomPart> bomParts = bom.getBomParts();
                List<PartInventoryDto> partInventory = new ArrayList<>();
                for (BomPart bomPart : bomParts) {
                    String partBwCode = bomPart.getPart().getBwCode();
                    int usage = bomPart.getAmount();

                    if (partInvenResult.containsKey(partBwCode)) {
                        PartInventoryDto partInventoryDto = partInvenResult.get(partBwCode);
                        partInventoryDto.setUsage(usage);
                        partInventory.add(partInventoryDto);
                    }
                }
                productPlanDto.setPartInventory(partInventory);
                planBomResult.add(productPlanDto);
            }
        }
        return planBomResult;
    }

    //선택품 찾기
    @Transactional(readOnly = true)
    public List<PartInventoryDto> findSelectPartInventory(String companyCode, String plantCode) {
        // 재고 찾기(공장) -> 회사 전체 부품으로 걸러냄
        HashMap<String, PartInventoryDto> partInvenMap = restTemplateService.getPartInventory(companyCode, plantCode);
        List<Part> findPartList = partRepo.findSelectPartByCompany(companyCode);
        List<PartInventoryDto> partInvenResult = new ArrayList<>();
        for (Part part : findPartList) {
            String bwCode = part.getBwCode();
            if (partInvenMap.containsKey(bwCode)) {
                PartInventoryDto partInventoryDto = partInvenMap.get(bwCode);
                partInventoryDto.setSpCode(part.getSpCode());
                partInventoryDto.setLoadAmount(part.getLoadAmount());
                partInvenResult.add(partInventoryDto);
            }
        }
        return partInvenResult;
    }

    public List<CreateDeliveryDto> submitPart(OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> deliveryList = createDeliveryCard(submitDto);
        System.out.println("deliveryList = " + deliveryList);
        submitDto.setPartList(null);
        restTemplateService.createDeliveryCard(submitDto, deliveryList);
        log.info("납입카드 성공");
        restTemplateService.registryDelivery(submitDto, deliveryList);
        log.info("재고 등록 성공");
        partLogService.createOutcomeLogs(submitDto, deliveryList);
        log.info("로그 생성 성공");
        commonService.addCompanyOrderHistory(
                OrderHistoryDto.builder()
                        .orderSeq(submitDto.getOrderSeq())
                        .date(submitDto.getDate())
                        .time(submitDto.getTime())
                        .plantCode(submitDto.getPlantCode())
                        .companyCode(submitDto.getCompanyCode())
                        .build()
        );
        log.info("주문 기록 생성 성공");
        return deliveryList;
    }

    private List<CreateDeliveryDto> createDeliveryCard(OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> result = new ArrayList<>();
        submitDto.getPartList().forEach(part -> {
            int amount = part.getAmount();
            List<CreateDeliveryDto> temp = new ArrayList<>();
            // 총 수량 >= 납품 양
            if (amount <= partInventoryRepo.sumPartStock(part.getBwCode()).intValue()) {
                List<PartInventory> inventoryList = partInventoryRepo.findInventoryListByPart(part.getBwCode());
                Part p = partRepo.getById(part.getBwCode());
                for (PartInventory inventory : inventoryList) {
                    if (amount > inventory.getStock()) {
                        amount -= inventory.getStock();
                        temp.add(
                                CreateDeliveryDto
                                        .builder()
                                        .bwCode(p.getBwCode())
                                        .partName(p.getPartName())
                                        .inventoryBwCode(p.getInventoryBwCode())
                                        .poCode(p.getPoCode())
                                        .quantity(inventory.getStock())
                                        .lot(inventory.getLot())
                                        .loadAmount(p.getLoadAmount())
                                        .location(p.getLocation())
                                        .build()
                        );
                        inventory.setStock(0);
                        partInventoryRepo.save(inventory);
                    } else {
                        temp.add(
                                CreateDeliveryDto
                                        .builder()
                                        .bwCode(p.getBwCode())
                                        .partName(p.getPartName())
                                        .inventoryBwCode(p.getInventoryBwCode())
                                        .poCode(p.getPoCode())
                                        .lot(inventory.getLot())
                                        .quantity(amount)
                                        .loadAmount(p.getLoadAmount())
                                        .location(p.getLocation())
                                        .build()
                        );
                        inventory.setStock(inventory.getStock() - amount);
                        partInventoryRepo.save(inventory);
                        result.addAll(temp);
                        break;
                    }
                }
            }
        });
        return result;
    }
}