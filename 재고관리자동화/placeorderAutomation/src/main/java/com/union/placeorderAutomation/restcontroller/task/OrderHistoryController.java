package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.outgoing.OrderHistoryDto;
import com.union.placeorderAutomation.service.task.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/task/order-history")
@RestController
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @GetMapping("/{companyCode}/{date}")
    public ResponseEntity findOrderHistoryList(@PathVariable String companyCode, @PathVariable String date){
            List<OrderHistoryDto> orderHistoryList = orderHistoryService.findOrderHistoryList(companyCode, date);
            return new ResponseEntity(orderHistoryList, HttpStatus.OK);
    }

    @DeleteMapping("/cancel-order/{companyCode}/{date}/{orderSeq}")
    public ResponseEntity cancelHistoryList(@PathVariable String companyCode, @PathVariable String date, @PathVariable int orderSeq){
        orderHistoryService.cancelOrder(companyCode, date, orderSeq);
        return new ResponseEntity(HttpStatus.OK);
    }
}
