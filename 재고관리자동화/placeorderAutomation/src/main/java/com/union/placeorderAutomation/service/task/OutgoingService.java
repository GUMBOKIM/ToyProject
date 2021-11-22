package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.PartInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.entity.*;
import com.union.placeorderAutomation.repository.BomRepository;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
import com.union.placeorderAutomation.repository.PartLogRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OutgoingService {

    private final RestTemplateService restTemplateService;
    private final BomRepository bomRepo;
    private final PartRepository partRepo;
    private final PartInventoryRepository partInventoryRepo;
    private final PartLogRepository partLogRepository;

    public List<ProductPlanDto> inquirePlanAndInventory(String companyCode, String plantCode) {
        // 재고 찾기(공장) -> 회사 전체 부품으로 걸러냄
        List<PartInventoryDto> partInvenList = restTemplateService.getPartInventory(companyCode, plantCode);
        List<Part> findPartList = partRepo.findByCompany(companyCode);
        List<PartInventoryDto> partInvenResult = new ArrayList<>();
        for (PartInventoryDto findPart : partInvenList) {
            for (Part part : findPartList) {
                if (findPart.getPartBwCode().equals(part.getBwCode())) {
                    partInvenResult.add(findPart);
                    break;
                }
            }
        }

        // 생산계획(BOM) 찾기(공장, 라인으로 검색)
        List<ProductPlanDto> planBomList = new ArrayList<>();
        planBomList.addAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CS"));
        planBomList.addAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CP"));
        planBomList.addAll(restTemplateService.getProductPlanning(companyCode, plantCode, "MC"));
        // 현재 선택한 회사에 속해 있는 BOM으로 걸러냄
        List<Bom> findBomList = bomRepo.findByCompanyCode(companyCode);
        List<ProductPlanDto> planBomResult = new ArrayList<>();
        for (ProductPlanDto planBom : planBomList) {
            for (Bom bom : findBomList) {
                if (bom.getBwCode().equals(planBom.getBomBwCode())) {
                    List<BomPart> bomPartList = bom.getBomParts();
                    List<PartInventoryDto> tempInven = new ArrayList<>();
                    for(PartInventoryDto partInven : partInvenResult){
                        for(BomPart bomPart : bomPartList){
                            if(bomPart.getPart().getBwCode().equals(partInven.getPartBwCode())){
                                partInven.setUsage(bomPart.getAmount());
                                tempInven.add(partInven);
                            }
                        }
                    }
                    planBom.setPartInventory(tempInven);
                    if(planBom.getPartInventory() != null) {
                        planBomResult.add(planBom);
                    }
                    break;
                }
            }
        }

        return planBomResult;
    }

    public List<CreateDeliveryDto> submitInventory(OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> deliveryList = createDeliveryCard(submitDto);
        restTemplateService.createDeliveryCard(submitDto.getCompanyCode(),
                submitDto.getPlantCode(),
                submitDto.getDate(),
                deliveryList
        );
        restTemplateService.registryDelivery(submitDto.getCompanyCode(),
                submitDto.getPlantCode(),
                submitDto.getDate(),
                submitDto.getTime(),
                deliveryList
        );

        return deliveryList;
    }

    private List<CreateDeliveryDto> createDeliveryCard(OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> result = new ArrayList<>();
        LocalDateTime date = LocalDate.parse(submitDto.getDate()).atStartOfDay();
        submitDto.getPartList().forEach(part -> {
            int amount = part.getAmount();
            List<CreateDeliveryDto> temp = new ArrayList<>();
            List<PartInventory> inventoryList = partInventoryRepo.findInventoryListByPartBwCode(part.getBwCode());
            for (PartInventory inventory : inventoryList) {
                Part p = inventory.getPart();
                if (amount > inventory.getStock()) {
                    amount -= inventory.getStock();
                    temp.add(
                            CreateDeliveryDto
                                    .builder()
                                    .bwCode(p.getBwCode())
                                    .partName(p.getPartName())
                                    .poCode(p.getPoCode())
                                    .lot(inventory.getLot())
                                    .loadAmount(p.getLoadAmount())
                                    .boxQuantity(inventory.getStock() / p.getLoadAmount())
                                    .location(p.getLocation())
                                    .build()
                    );
                    PartLog partLog = PartLog.builder()
                            .part(inventory.getPart())
                            .build();
                    partLogRepository.save(partLog);

                    inventory.setStock(0);
                    partInventoryRepo.save(inventory);
                } else {
                    temp.add(
                            CreateDeliveryDto
                                    .builder()
                                    .bwCode(p.getBwCode())
                                    .partName(p.getPartName())
                                    .poCode(p.getPoCode())
                                    .lot(inventory.getLot())
                                    .loadAmount(p.getLoadAmount())
                                    .boxQuantity(amount / p.getLoadAmount())
                                    .location(p.getLocation())
                                    .build()
                    );
                    PartLog partLog = PartLog.builder()
                            .part(inventory.getPart())
                            .build();
                    partLogRepository.save(partLog);

                    inventory.setStock(inventory.getStock() - amount);
                    partInventoryRepo.save(inventory);
                    amount = 0;
                }
                if (amount == 0) {
                    break;
                }
            }
            if (amount == 0) {
                result.addAll(temp);
            }
        });
        return result;
    }
}