package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.common.OrderHistoryDto;
import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingManualDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.service.common.CommonService;
import com.union.placeorderAutomation.service.common.PartLogService;
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
    private final CommonService commonService;
    private final PartLogService partLogService;

    @PostMapping("")
    public ResponseEntity outgoingManual(@RequestBody OutgoingManualDto request) {
        System.out.println("request = " + request);
        List<CreateDeliveryDto> deliveryList = Arrays.asList(request.getDeliveryList());
        OutgoingSubmitDto submitDto = OutgoingSubmitDto.builder()
                .companyCode(request.getCompanyCode())
                .plantCode(request.getPlantCode())
                .date(request.getDate())
                .orderSeq(10)
                .time(request.getTime())
                .build();
        restTemplateService.createDeliveryCard(
                submitDto,
                deliveryList
        );
        restTemplateService.registryDelivery(
                submitDto,
                deliveryList);
        partLogService.createOutcomeLogs(submitDto, deliveryList);

        commonService.addCompanyOrderHistory(
                OrderHistoryDto.builder()
                        .orderSeq(submitDto.getOrderSeq())
                        .date(submitDto.getDate())
                        .time(submitDto.getTime())
                        .plantCode(submitDto.getPlantCode())
                        .companyCode(submitDto.getCompanyCode())
                        .build()
        );

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{companyCode}/{date}/{plantCode}")
    public ResponseEntity findCard(@PathVariable String companyCode, @PathVariable String date, @PathVariable String plantCode) {
        String[] result = restTemplateService.findDeliveryCard(companyCode, date, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
