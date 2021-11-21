package com.union.placeorderAutomation.restcontroller.task.part;

import com.union.placeorderAutomation.dto.task.part.manage.PartCheckDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendReqDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendResDto;
import com.union.placeorderAutomation.service.task.TaskPartManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/part-manage")
@RestController
public class PartStockManageController {

    private final TaskPartManageService partManageService;

    @PostMapping("/check")
    public ResponseEntity checkPart(@RequestBody PartCheckDto[] partCheckDtoList) {
        PartCheckDto[] result = partManageService.checkPart(partCheckDtoList);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/income")
    public ResponseEntity incomePartStock(@RequestBody StockSendReqDto stockSendReqDto) {
        List<StockSendResDto> result = partManageService.incomePartStock(stockSendReqDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity modifyPartStock(@RequestBody StockSendReqDto stockSendReqDto) {
        List<StockSendResDto> result = partManageService.modifyPartStock(stockSendReqDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/defective")
    public ResponseEntity defectivePartStock(@RequestBody StockSendReqDto stockSendReqDto) {
        List<StockSendResDto> result = partManageService.defectivePartStock(stockSendReqDto);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
