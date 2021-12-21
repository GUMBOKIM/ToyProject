package com.union.placeorderAutomation.restcontroller.task.part;

import com.union.placeorderAutomation.dto.task.part.stock.PartStockDetailDto;
import com.union.placeorderAutomation.dto.task.part.stock.PartStockDto;
import com.union.placeorderAutomation.dto.task.part.stock.PartStockExcelDto;
import com.union.placeorderAutomation.dto.task.part.stock.PartStockModifyDto;
import com.union.placeorderAutomation.service.task.TaskPartStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-stock")
@RestController
public class PartStockStatusController {

    private final TaskPartStockService partStockService;

    @GetMapping("/{companyCode}")
    public ResponseEntity getPartList(@PathVariable String companyCode) {
        List<PartStockDto> partList = partStockService.getPartStockList(companyCode);
        return new ResponseEntity(partList, HttpStatus.OK);
    }

}
