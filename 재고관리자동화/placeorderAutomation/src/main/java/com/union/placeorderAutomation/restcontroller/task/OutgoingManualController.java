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
        restTemplateService.createDeliveryCardManual(
                request.getCompanyCode(),
                request.getPlantCode(),
                request.getDate(),
                request.getSeqNo(),
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
}
