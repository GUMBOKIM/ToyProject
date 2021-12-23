package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.PartInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingManualDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingManualPartDto;
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
//
//    @GetMapping("/checkPart/{bwCode}/{quantity}")
//    public ResponseEntity checkPartQuantity(@PathVariable String bwCode, @PathVariable int quantity) {
//        int partStock = outgoingService.findPartStock(bwCode);
//        if (quantity <= partStock) {
//            return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(partStock, HttpStatus.BAD_REQUEST);
//        }
//    }
//
    @GetMapping("/inquire/{companyCode}/{plantCode}")
    public ResponseEntity inquirePlanNInventory(@PathVariable String companyCode,
                                                @PathVariable String plantCode) {
        List<ProductPlanDto> result = outgoingService.findPlanAndInventory(companyCode, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/inquire-select/{companyCode}/{plantCode}")
    public ResponseEntity inquireSelectInventory(@PathVariable String companyCode,
                                                 @PathVariable String plantCode) {
        List<PartInventoryDto> result = outgoingService.findSelectPartInventory(companyCode, plantCode);
        return new ResponseEntity(result, HttpStatus.OK);
    }
//
//    @PostMapping("/check")
//    public ResponseEntity checkPartList(@RequestBody OutgoingSubmitDto request) {
//        List<CreateDeliveryDto> result = outgoingService.checkPartList(request);
//        return new ResponseEntity(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/submit")
//    public ResponseEntity submitPartList(@RequestBody OutgoingSubmitDto request) {
//        List<CreateDeliveryDto> result = outgoingService.submitPart(request);
//        return new ResponseEntity(result, HttpStatus.OK);
//    }

    @PostMapping("/manual/check")
    public ResponseEntity checkManualExcelList(@RequestBody OutgoingManualDto request) {
        List<OutgoingManualPartDto> result = outgoingService.checkManual(request);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/manual/submit")
    public ResponseEntity submitManualPartList(@RequestBody OutgoingManualDto request) {
        List<CreateDeliveryDto> result = outgoingService.submitManualPart(request);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
