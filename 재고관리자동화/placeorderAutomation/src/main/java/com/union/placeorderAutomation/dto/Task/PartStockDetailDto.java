package com.union.placeorderAutomation.dto.Task;

import lombok.Data;

@Data
public class PartStockDetailDto {

    private Long partInventoryId;
    private String lot;
    private int stock;

}
