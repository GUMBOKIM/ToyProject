package com.union.placeorderAutomation.dto.task.part.stock;

import lombok.Data;

@Data
public class PartStockExcelDto {
    private String bwCode;
    private String lot;
    private int loadAmount;
    private int stock;
    private String boxQuantity;

}
