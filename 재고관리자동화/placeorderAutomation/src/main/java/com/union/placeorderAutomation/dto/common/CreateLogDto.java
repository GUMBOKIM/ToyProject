package com.union.placeorderAutomation.dto.common;

import com.union.placeorderAutomation.dto.task.part.manage.StockSendResDto;
import lombok.Data;

@Data
public class CreateLogDto {
    private String partBwCode;
    private String plantCode;
    private String date;
    private int orderSeq;
    private String lot;
    private int amount;

    public CreateLogDto(String date, String orderSeq, StockSendResDto sendResDto) {
        this.partBwCode = sendResDto.getBwCode();
        this.lot = sendResDto.getLot();
        this.amount = sendResDto.getQuantity();
        this.date = date;
        this.orderSeq = Integer.parseInt(orderSeq);
    }
}
