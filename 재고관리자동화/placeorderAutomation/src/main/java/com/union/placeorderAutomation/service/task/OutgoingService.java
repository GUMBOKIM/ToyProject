package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.resttemplate.ProductInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.InquirePartDto;
import com.union.placeorderAutomation.dto.task.outgoing.InquireResultDto;
import com.union.placeorderAutomation.entity.Bom;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.BomRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OutgoingService {

    private final RestTemplateService restTemplateService;
    private final BomRepository bomRepo;
    private final PartRepository partRepo;

    public void inquirePlanAndInventory(String companyCode, String plantCode) {
        List<Bom> findBomList = bomRepo.findByCompanyCode(companyCode);
        System.out.println("findBomList = " + findBomList.toString());
        List<Part> findPartList = partRepo.findByCompany(companyCode);
        System.out.println("findPartList = " + findPartList.toString());

        List<InquirePartDto> partList = new ArrayList<>();
        List<ProductInventoryDto> partInventory = restTemplateService.getPartInventory(companyCode, plantCode);
        partInventory.forEach(inventory -> {
            findPartList.forEach(findPart -> {
                if (inventory.getPartNo().equals(findPart.getBwCode())) {
                    partList.add(InquirePartDto
                            .builder()
                            .partBwCode(findPart.getBwCode())
                            .loadAmount(findPart.getLoadAmount())
                            .stockQTY(inventory.getStockQTY())
                            .build());
                }
            });
        });
        List<InquireResultDto> result = new ArrayList<>();
        List<ProductPlanDto> productPlanning = restTemplateService.getProductPlanning(companyCode, plantCode);
        productPlanning.forEach(productPlan -> {
            findBomList.forEach(findBom -> {
                if (productPlan.getBomBwCode().equals(findBom.getBwCode())) {
                    InquireResultDto inquireDto = InquireResultDto
                            .builder()
                            .bomBwCode(findBom.getBwCode())
                            .workCenter(productPlan.getWorkCenter())
                            .totalQTY(productPlan.getTotalQTY())
                            .deliveredQTY(productPlan.getDeliveredQTY())
                            .remainQTY(productPlan.getRemainQTY())
                            .seq(productPlan.getSeq())
                            .build();

                }
            });
        });

    }
}
