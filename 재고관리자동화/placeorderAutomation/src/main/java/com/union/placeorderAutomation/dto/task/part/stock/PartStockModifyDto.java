package com.union.placeorderAutomation.dto.task.part.stock;

import lombok.Data;

@Data
public class PartStockModifyDto {
    private String lot;
    private int loadAmount;
}
