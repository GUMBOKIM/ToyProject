package com.union.placeorderAutomation.dto.task.part.stock;

import com.union.placeorderAutomation.entity.PartInventory;
import lombok.Data;

@Data
public class PartStockExcelDto {
    private String bwCode;
    private String lot;
    private int loadAmount;
    private int stock;
    private String boxQuantity;

    public PartStockExcelDto(PartInventory partInventory) {
        this.bwCode = partInventory.getPart().getBwCode();
        this.lot = partInventory.getLot();
        this.loadAmount = partInventory.getLoadAmount();
        this.stock = partInventory.getStock();
        this.boxQuantity = String.format("%.1f", (double) (partInventory.getStock()/ partInventory.getLoadAmount()));
    }
}
