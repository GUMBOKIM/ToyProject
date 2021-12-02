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

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/outgoing")
@RestController
public class OutgoingTaskController {

    private final OutgoingService outgoingService;

    @GetMapping("/checkPart/{bwCode}/{quantity}")
    public ResponseEntity checkPartQuantity(@PathVariable String bwCode, @PathVariable int quantity) {
        int partStock = outgoingService.findPartStock(bwCode);
        if (quantity <= partStock) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(partStock, HttpStatus.BAD_REQUEST);
        }
    }

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
        List<CreateDeliveryDto> result = outgoingService.submitPart(request);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
