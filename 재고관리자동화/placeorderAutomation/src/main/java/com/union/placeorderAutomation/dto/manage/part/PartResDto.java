package com.union.placeorderAutomation.dto.manage.part;

import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class PartResDto {
    private String companyName;
    private String partName;
    private String bwCode;
    private String inventoryBwCode;
    private String spCode;
    private String poCode1;
    private String poCode2;
    private String location1;
    private String location2;
    private int loadAmount;
    private String selectYn;
    private String useYn;

    public PartResDto(Part part) {
        this.companyName = part.getCompany().getCompanyName();
        this.partName = part.getPartName();
        this.bwCode = part.getBwCode();
        this.inventoryBwCode = part.getInventoryBwCode();
        this.spCode = part.getSpCode();
        this.poCode1 = part.getPoCode1();
        this.poCode2 = part.getPoCode2();
        this.loadAmount = part.getLoadAmount();
        this.location1 = part.getLocation1();
        this.location2 = part.getLocation2();
        this.selectYn = part.getSelectYn();
        this.useYn = part.getUseYn();
    }
}
