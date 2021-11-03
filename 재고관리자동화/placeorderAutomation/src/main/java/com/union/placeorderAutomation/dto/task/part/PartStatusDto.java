package com.union.placeorderAutomation.dto.task.part;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartStatusDto {
    private String bwCode;
    private String day;
    private String division;
    private int amount;
    private String time;
    private String factory;

    public PartStatusDto(Object[] object) {
        this.bwCode = (String) object[0];
        this.day = (String) object[1];
        this.division = (String) object[2];
        this.amount = ((BigDecimal) object[3]).intValue();
        this.time = (String) object[4];
        this.factory = (String) object[5];
    }
}
