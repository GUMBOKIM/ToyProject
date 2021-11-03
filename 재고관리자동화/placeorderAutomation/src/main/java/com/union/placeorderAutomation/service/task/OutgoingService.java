package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.resttemplate.ProductInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.repository.BomRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class OutgoingService {

    private final RestTemplateService restTemplateService;
    private final BomRepository bomRepo;

//    public void inquirePlanAndInventory(String companyCode, String plantCode) {
//        List<partList
//        List<ProductPlanDto> productPlanning = restTemplateService.getProductPlanning(companyCode, plantCode);
//        List<ProductInventoryDto> partInventory = restTemplateService.getPartInventory(companyCode, plantCode);
//    }
}
