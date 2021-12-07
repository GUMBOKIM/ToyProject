package com.union.placeorderAutomation.restcontroller.task;

import com.union.placeorderAutomation.dto.task.outgoing.OrderHistoryDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
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

    @GetMapping("/latest/{date}")
    public ResponseEntity findOrderHistoryLatest(@PathVariable String date){
        List<OrderHistoryDto> orderHistoryList = orderHistoryService.findLatestOrderHistoryList(date);
        return new ResponseEntity(orderHistoryList, HttpStatus.OK);
    }

    @GetMapping("/{companyCode}/{date}")
    public ResponseEntity findOrderHistoryList(@PathVariable String companyCode, @PathVariable String date){
            List<OrderHistoryDto> orderHistoryList = orderHistoryService.findOrderHistoryList(companyCode, date);
            return new ResponseEntity(orderHistoryList, HttpStatus.OK);
    }

    @DeleteMapping("/cancel-order")
    public ResponseEntity cancelHistoryList(@RequestBody OutgoingSubmitDto outgoingSubmitDto){
        orderHistoryService.cancelOrder(outgoingSubmitDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
