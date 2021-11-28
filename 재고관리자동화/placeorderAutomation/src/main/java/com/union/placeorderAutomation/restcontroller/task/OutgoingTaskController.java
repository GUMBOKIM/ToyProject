package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.PartInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.service.task.OutgoingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/outgoing")
@RestController
public class OutgoingTaskController {

    private final OutgoingService outgoingService;

    @GetMapping("/inquire/{companyCode}/{plantCode}")
    public ResponseEntity inquirePlanNInventory(@PathVariable String companyCode,
                                                @PathVariable String plantCode) {
        List<ProductPlanDto> result = outgoingService.findPlanAndInventory(companyCode, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/inquire-select/{companyCode}/{plantCode}")
    public ResponseEntity inquireSelectPlanNInventory(@PathVariable String companyCode,
                                                      @PathVariable String plantCode) {
        List<PartInventoryDto> result = outgoingService.findSelectPartInventory(companyCode, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity submitPartList(@RequestBody OutgoingSubmitDto request) {
//        List<CreateDeliveryDto> result = outgoingService.submitPart(request);
        List<CreateDeliveryDto> result = new ArrayList<>();
        result.add(CreateDeliveryDto.builder().bwCode("AAAA").lot("AAA").quantity(1000).loadAmount(100).build());
        result.add(CreateDeliveryDto.builder().bwCode("BBBB").lot("BBB").quantity(2000).loadAmount(100).build());
        result.add(CreateDeliveryDto.builder().bwCode("CCCC").lot("CCC").quantity(3000).loadAmount(100).build());
        result.add(CreateDeliveryDto.builder().bwCode("DDDD").lot("DDD").quantity(4000).loadAmount(100).build());
        result.add(CreateDeliveryDto.builder().bwCode("EEEE").lot("EEE").quantity(5000).loadAmount(100).build());
        result.add(CreateDeliveryDto.builder().bwCode("FFFF").lot("FFF").quantity(6000).loadAmount(100).build());

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
