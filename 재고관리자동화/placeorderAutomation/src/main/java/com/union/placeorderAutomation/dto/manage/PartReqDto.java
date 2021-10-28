package com.union.placeorderAutomation.dto.manage;

import lombok.Data;

@Data
public class PartReqDto {
    private Long companyId;
    private String partName;
    private String bwCode;
    private String spCode;
    private String poCode;
    private int loadAmount;
    private String location;
    private String description;

}
