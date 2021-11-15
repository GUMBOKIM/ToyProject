package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.part.IncomeResultDto;
import com.union.placeorderAutomation.dto.task.part.StockRequestDto;
import com.union.placeorderAutomation.dto.task.part.PartStockDetailDto;
import com.union.placeorderAutomation.dto.task.part.PartStockDto;
import com.union.placeorderAutomation.service.task.PartStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-stock")
@RestController
public class PartStockController {

    private final PartStockService partStockService;

    @GetMapping("/{companyCode}")
    public ResponseEntity getPartList(@PathVariable String companyCode) {
        List<PartStockDto> partList = partStockService.getPartStockList(companyCode);
        return new ResponseEntity(partList, HttpStatus.OK);
    }

    @GetMapping("/inventory/{partBwCode}")
    public ResponseEntity getPartStockList(@PathVariable String partBwCode) {
        List<PartStockDetailDto> partDetailList = partStockService.getPartStockDetailList(partBwCode);
        return new ResponseEntity(partDetailList, HttpStatus.OK);
    }

    @DeleteMapping("/inventory/{partInventoryId}")
    public ResponseEntity removePartStock(@PathVariable Long partInventoryId){
        partStockService.removePartStock(partInventoryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/inventory/manual")
    public ResponseEntity manualModifyPartStock(@RequestBody StockRequestDto stockRequest) {
        PartStockDetailDto partStockDetail = partStockService.manualModifyPartStock(stockRequest);
        return new ResponseEntity(partStockDetail, HttpStatus.OK);
    }

    @PostMapping("/inventory/income")
    public ResponseEntity incomePartStock(@RequestBody List<StockRequestDto> stockRequestList) {
        IncomeResultDto result = partStockService.incomePartStock(stockRequestList);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
