package com.union.placeorderAutomation.dto.task.part.manage;

import lombok.Data;

@Data
public class PartCheckDto {
    private String bwCode;
    private String companyName;
    private String spCode;
    private String name;
    private String lot;
    private int quantity;
    private int loadAmount;
    private String check;
}
