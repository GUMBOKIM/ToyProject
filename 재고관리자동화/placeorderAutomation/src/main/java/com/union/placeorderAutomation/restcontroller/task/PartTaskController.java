package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.Task.PartStockDetailDto;
import com.union.placeorderAutomation.dto.Task.PartStockDto;
import com.union.placeorderAutomation.dto.Task.StockModifyBWDto;
import com.union.placeorderAutomation.service.task.PartTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part")
@RestController
public class PartTaskController {

    private final PartTaskService partTaskService;

    @GetMapping("/{companyId}")
    public ResponseEntity getPartList(@PathVariable("companyId") Long companyId){
        List<PartStockDto> partList = partTaskService.getPartStockList(companyId);
        return new ResponseEntity(partList, HttpStatus.OK);
    }

    @GetMapping("/part/{partId}")
    public ResponseEntity getPartList(@PathVariable("partId") Long partId){
        List<PartStockDetailDto> partDetailList = partTaskService.getPartStockDetailList(partId);
        return new ResponseEntity(partDetailList, HttpStatus.OK);
    }

    @PostMapping("/income")
    public ResponseEntity incomePartByBwCode(@RequestBody StockModifyBWDto request) {
        PartStockDetailDto partDetailStock = partTaskService.incomePartByBwCode(request);
        return new ResponseEntity(partDetailStock, HttpStatus.OK);
    }

    @PostMapping("/modfiy")
    public ResponseEntity modifyPartByBwCode(@RequestBody StockModifyBWDto request) {
        PartStockDetailDto partDetailStock = partTaskService.modifyPartByBwCode(request);
        return new ResponseEntity(partDetailStock, HttpStatus.OK);
    }


}
