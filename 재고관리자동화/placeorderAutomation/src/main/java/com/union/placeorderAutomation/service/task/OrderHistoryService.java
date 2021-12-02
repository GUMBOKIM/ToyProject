package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.outgoing.OrderHistoryDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.OrderHistory;
import com.union.placeorderAutomation.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepo;

    public List<OrderHistoryDto> findOrderHistoryList(String companyCode, String date) {
        List<OrderHistoryDto> result = new ArrayList<>();
        List<OrderHistory> orderHistoryList = orderHistoryRepo.findOrderHistoryList(Company.builder().companyCode(companyCode).build(), date);
        for(OrderHistory orderHistory : orderHistoryList){
            result.add(new OrderHistoryDto(orderHistory));
        }
        return result;
    }

    public void cancelOrder(String companyCode, String date, int orderSeq) {
    }
}
