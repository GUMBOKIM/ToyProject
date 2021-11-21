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

    public StockSendResDto(String bwCode, String lot, int quantity, int beforeStock, int afterStock, String check) {
        this.bwCode = bwCode;
        this.lot = lot;
        this.quantity = quantity;
        this.beforeStock = beforeStock;
        this.afterStock = afterStock;
        this.check = check;
    }
}
