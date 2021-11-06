package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.InquirePartDto;
import com.union.placeorderAutomation.dto.task.outgoing.InquireResultDto;
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

    public List<InquireResultDto> inquirePlanAndInventory(String companyCode, String plantCode) {
        List<Part> findPartList = partRepo.findByCompany(companyCode);
        List<InquirePartDto> partList = new ArrayList<>();
        List<ProductInventoryDto> partInventory = restTemplateService.getPartInventory(companyCode, plantCode);
        System.out.println("partInventory = " + partInventory);
        for (ProductInventoryDto findPart : partInventory) {
            for (Part part : findPartList) {
                if (findPart.getPartNo().equals(part.getBwCode())) {
                    partList.add(
                            InquirePartDto.builder()
                                    .partBwCode(part.getBwCode())
                                    .stockQTY(findPart.getStockQTY())
                                    .loadAmount(part.getLoadAmount())
                                    .build()
                    );
                    break;
                }
            }
        }

        List<Bom> findBomList = bomRepo.findByCompanyCode(companyCode);
        List<InquireResultDto> result = new ArrayList<>();
        List<ProductPlanDto> planBomList = restTemplateService.getProductPlanning(companyCode, plantCode);
        System.out.println("planBomList = " + planBomList);
        for(ProductPlanDto planBom : planBomList){
            for(Bom bom : findBomList){
                if(bom.getBwCode().equals(planBom.getBomBwCode())){
                    InquireResultDto temp = new InquireResultDto(planBom);
                    List<BomPart> bomPartList = bom.getBomParts();
                    for(BomPart bomPart : bomPartList){
                        for(InquirePartDto selectPart: partList){
                            if(bomPart.getPart().getBwCode().equals(selectPart.getPartBwCode())){
                                selectPart.setUsage(bomPart.getAmount());
                                temp.addParts(selectPart);
                            }
                        }
                    }
                    result.add(temp);
                    break;
                }
            }
        }
        return result;
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
            Part findPart = partRepo.findByBwCode(part.getBwCode()).get();
            List<PartInventory> inventoryList = partInventoryRepo.findByPart(findPart);
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
                                    .standardYn(p.getStandardYn())
                                    .build()
                    );
                    PartLog partLog = PartLog.builder()
                            .part(inventory.getPart())
                            .division("O")
                            .amount(inventory.getStock())
                            .date(date)
                            .time(submitDto.getTime())
                            .plant(submitDto.getPlantCode())
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
                                    .standardYn(p.getStandardYn())
                                    .build()
                    );
                    PartLog partLog = PartLog.builder()
                            .part(inventory.getPart())
                            .division("O")
                            .amount(amount)
                            .date(date)
                            .time(submitDto.getTime())
                            .plant(submitDto.getPlantCode())
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