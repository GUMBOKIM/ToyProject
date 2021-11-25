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
    private String poCode;
    private int loadAmount;
    private String location;
    private String selectYn;
    private String useYn;

    public PartResDto(Part part) {
        this.companyName = part.getCompany().getCompanyName();
        this.partName = part.getPartName();
        this.bwCode = part.getBwCode();
        this.inventoryBwCode = part.getInventoryBwCode();
        this.spCode = part.getSpCode();
        this.poCode = part.getPoCode();
        this.loadAmount = part.getLoadAmount();
        this.location = part.getLocation();
        this.selectYn = part.getSelectYn();
        this.useYn = part.getUseYn();
    }
}
