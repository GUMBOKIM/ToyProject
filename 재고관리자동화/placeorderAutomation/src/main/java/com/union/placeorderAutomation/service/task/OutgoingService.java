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
import java.util.HashMap;
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
        HashMap<String, PartInventoryDto> partInvenMap = restTemplateService.getPartInventory(companyCode, plantCode);
        List<Part> findPartList = partRepo.findByCompany(companyCode);
        HashMap<String, PartInventoryDto> partInvenResult = new HashMap<>();
        for(Part part : findPartList){
            String bwCode = part.getBwCode();
            if(partInvenMap.containsKey(bwCode)){
                partInvenResult.put(bwCode, partInvenMap.get(bwCode));
            }
        }

        // 생산계획(BOM) 찾기(공장, 라인으로 검색)
        HashMap<String, ProductPlanDto> planBomMap = new HashMap<>();
        planBomMap.putAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CS"));
        planBomMap.putAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CP"));
        planBomMap.putAll(restTemplateService.getProductPlanning(companyCode, plantCode, "MC"));
        // 회사 제품이 들어간 BOM 필터링
        List<Bom> findBomList = bomRepo.findByCompanyCode(companyCode);
        List<ProductPlanDto> planBomResult = new ArrayList<>();
        for (Bom bom : findBomList){
            String bomBwCode = bom.getBwCode();
            if(planBomMap.containsKey(bomBwCode)){
                ProductPlanDto productPlanDto = planBomMap.get(bomBwCode);
                List<BomPart> bomParts = bom.getBomParts();
                List<PartInventoryDto> partInventory = new ArrayList<>();
                for(BomPart bomPart : bomParts){
                    String partBwCode = bomPart.getPart().getBwCode();
                    int usage = bomPart.getAmount();

                    if(partInvenResult.containsKey(partBwCode)){
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