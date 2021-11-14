package com.union.placeorderAutomation.dto.task.part;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartStockDto {
    private String companyName;
    private String bwCode;
    private String spCode;
    private String poCode;
    private int stock;

    public PartStockDto(Object[] part) {
        this.companyName = (String) part[0];
        this.bwCode = (String) part[1];
        this.spCode = (String) part[2];
        this.poCode = (String) part[3];
        this.stock =  ((BigDecimal) part[4]).intValue();
    }
}
