package com.union.placeorderAutomation.dto.task.part.stock;

import lombok.Data;

@Data
public class PartStockDetailDto {

    private Long partInventoryId;
    private String partBwCode;
    private String lot;
    private int loadAmount;
    private int stock;

}
