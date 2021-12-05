package com.union.placeorderAutomation.dto.task.part.stock;

import lombok.Data;

@Data
public class PartStockExcelDto {
    private String bwCode;
    private String lot;
    private String loadAmount;
    private String quantity;
}
