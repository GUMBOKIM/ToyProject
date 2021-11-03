package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.SubmitDto;
import com.union.placeorderAutomation.service.task.OutgoingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/task/outgoing")
@RestController
public class OutgoingTaskController {

    private final OutgoingService outgoingService;

    @GetMapping("inquire/{companyCode}/{plantCode}")
    public ResponseEntity inquirePlanNInventory(@PathVariable String companyCode, @PathVariable String plantCode){
//        outgoingService.inquirePlanAndInventory(companyCode, plantCode);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("submit")
    public ResponseEntity submitInventory(@RequestBody SubmitDto submitDto){

        return new ResponseEntity(HttpStatus.OK);
    }
}
