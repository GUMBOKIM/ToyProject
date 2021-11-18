package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.part.status.PartStockDetailDto;
import com.union.placeorderAutomation.dto.task.part.status.PartStockDto;
import com.union.placeorderAutomation.service.task.PartStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-stock")
@RestController
public class PartStockStatusController {

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

}
