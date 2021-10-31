package com.union.placeorderAutomation.dto.manage;

import lombok.Data;

@Data
public class PartReqDto {
    private String bwCode;
    private String companyCode;
    private String partName;
    private String spCode;
    private String poCode;
    private int loadAmount;
    private String location;
    private String description;

}
