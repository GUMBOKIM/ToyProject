package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.task.outgoing.InquireResultDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.service.task.OutgoingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/outgoing")
@RestController
public class OutgoingTaskController {

    private final OutgoingService outgoingService;

    @GetMapping("/inquire/{companyCode}/{plantCode}")
    public ResponseEntity inquirePlanNInventory(@PathVariable String companyCode,
                                                @PathVariable String plantCode) {
        List<InquireResultDto> result = outgoingService.inquirePlanAndInventory(companyCode, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity submitInventory(@RequestBody OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> result = outgoingService.submitInventory(submitDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
