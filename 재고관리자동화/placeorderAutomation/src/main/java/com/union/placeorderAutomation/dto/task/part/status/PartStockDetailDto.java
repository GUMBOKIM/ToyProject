package com.union.placeorderAutomation.dto.task.part.status;

import com.union.placeorderAutomation.entity.PartInventory;
import lombok.Data;

@Data
public class PartStockDetailDto {

    private Long partInventoryId;
    private String partBwCode;
    private String lot;
    private int stock;

    public PartStockDetailDto(PartInventory partInventory) {
        this.partInventoryId = partInventory.getId();
        this.partBwCode = partInventory.getPart().getBwCode();
        this.lot = partInventory.getLot();
        this.stock = partInventory.getStock();
    }
}
