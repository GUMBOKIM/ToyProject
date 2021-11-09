package com.union.placeorderAutomation.dto.manage;

import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class PartResDto {
    private String companyName;
    private String partName;
    private String bwCode;
    private String spCode;
    private String poCode;
    private int loadAmount;
    private String location;
    private String standardYn;

    public PartResDto(Part part) {
        this.companyName = part.getCompany().getCompanyName();
        this.partName = part.getPartName();
        this.bwCode = part.getBwCode();
        this.spCode = part.getSpCode();
        this.poCode = part.getPoCode();
        this.loadAmount = part.getLoadAmount();
        this.location = part.getLocation();
        this.standardYn = part.getStandardYn();
    }
}
