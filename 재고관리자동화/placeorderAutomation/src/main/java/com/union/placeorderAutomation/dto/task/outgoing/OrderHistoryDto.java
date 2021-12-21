package com.union.placeorderAutomation.dto.task.outgoing;

import com.union.placeorderAutomation.entity.OrderHistory;
import lombok.Data;

@Data
public class OrderHistoryDto {
    private Long orderId;
    private String companyCode;
    private String plantCode;
    private String date;
    private int orderSeq;
    private String time;

    public OrderHistoryDto(OrderHistory orderHistory) {
        this.orderId = orderHistory.getOrderId();
        this.companyCode = orderHistory.getCompany().getCompanyName();
        this.plantCode = orderHistory.getPlant().getPlantName();
        this.date = orderHistory.getDate();
        this.orderSeq = orderHistory.getOrderSeq();
        this.time = orderHistory.getTime();
    }
}
