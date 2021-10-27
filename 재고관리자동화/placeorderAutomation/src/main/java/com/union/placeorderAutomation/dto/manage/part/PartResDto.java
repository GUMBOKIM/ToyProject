package com.union.placeorderAutomation.dto.manage.part;

import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class PartResDto {
    private Long partId;
    private Long companyId;
    private String partName;
    private String bwCode;
    private String spCode;
    private String poCode;
    private int loadAmount;
    private String location;
    private String description;

    public PartResDto(Part part) {
        this.partId = part.getPartId();
        this.companyId = part.getCompany().getCompanyId();
        this.partName = part.getPartName();
        this.bwCode = part.getBwCode();
        this.spCode = part.getSpCode();
        this.poCode = part.getPoCode();
        this.loadAmount = part.getLoadAmount();
        this.location = part.getLocation();
        this.description = part.getDescription();
    }
}
