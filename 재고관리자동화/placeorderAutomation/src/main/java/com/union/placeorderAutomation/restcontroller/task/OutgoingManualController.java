package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingManualDto;
import com.union.placeorderAutomation.service.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/outgoing-manual")
@RestController
public class OutgoingManualController {

    private final RestTemplateService restTemplateService;

    @PostMapping("")
    public ResponseEntity outgoingManual(@RequestBody OutgoingManualDto request) {
        List<CreateDeliveryDto> deliveryList = Arrays.asList(request.getDeliveryList());
        restTemplateService.createDeliveryCard(
                request.getCompanyCode(),
                request.getPlantCode(),
                request.getDate(),
                deliveryList
        );
        restTemplateService.registryDelivery(
                request.getCompanyCode(),
                request.getPlantCode(),
                request.getDate(),
                request.getTime(),
                deliveryList
        );
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{companyCode}/{date}/{plantCode}")
    public ResponseEntity findCard(@PathVariable String companyCode, @PathVariable String date, @PathVariable String plantCode) {
        String[] result = restTemplateService.findDeliveryCard(companyCode, date, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
