package com.union.placeorderAutomation.dto.manage.part;

import lombok.Data;

@Data
public class PartReqDto {
    private String bwCode;
    private String inventoryBwCode;
    private String companyCode;
    private String partName;
    private String spCode;
    private String poCode1;
    private String poCode2;
    private String location1;
    private String location2;
    private int loadAmount;
    private String selectYn;
    private String useYn;

}
