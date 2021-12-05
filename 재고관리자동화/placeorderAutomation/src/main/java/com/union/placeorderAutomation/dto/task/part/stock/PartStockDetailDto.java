package com.union.placeorderAutomation.dto.task.part.stock;

import com.union.placeorderAutomation.entity.PartInventory;
import lombok.Data;

@Data
public class PartStockDetailDto {

    private Long partInventoryId;
    private String partBwCode;
    private String lot;
    private int loadAmount;
    private int stock;

    public PartStockDetailDto(PartInventory partInventory) {
        this.partInventoryId = partInventory.getId();
        this.partBwCode = partInventory.getPart().getBwCode();
        this.lot = partInventory.getLot();
        this.loadAmount = partInventory.getLoadAmount();
        this.stock = partInventory.getStock();
    }
}
