package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.part.*;
import com.union.placeorderAutomation.service.task.PartStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-manage")
@RestController
public class PartStockManageController {

    private final PartStockService partStockService;

    @PostMapping("/check")
    public ResponseEntity checkPart(@RequestBody PartCheckDto[] partCheckDtoList) {
        PartCheckDto[] result = partStockService.checkPart(partCheckDtoList);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @PostMapping("/stock")
    public ResponseEntity manualModifyPartStock(@RequestBody StockRequestDto stockRequest) {
        PartStockDetailDto partStockDetail = partStockService.manualModifyPartStock(stockRequest);
        return new ResponseEntity(partStockDetail, HttpStatus.OK);
    }

    @PostMapping("/income")
    public ResponseEntity incomePartStock(@RequestBody List<StockRequestDto> stockRequestList) {
        IncomeResultDto result = partStockService.incomePartStock(stockRequestList);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
