package com.union.placeorderAutomation.dto.task.part.manage;

import lombok.Data;

@Data
public class StockSendResDto {
    private String bwCode;
    private String lot;
    private int quantity;
    private int beforeStock;
    private int afterStock;
    private String check;
}
