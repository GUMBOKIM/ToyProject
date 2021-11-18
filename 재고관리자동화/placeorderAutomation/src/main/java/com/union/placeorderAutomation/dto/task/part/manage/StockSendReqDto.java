package com.union.placeorderAutomation.dto.task.part.manage;

import lombok.Data;

@Data
public class StockSendReqDto {
    private String date;
    private String orderSeq;
    private PartCheckDto[] partCheck;
}
