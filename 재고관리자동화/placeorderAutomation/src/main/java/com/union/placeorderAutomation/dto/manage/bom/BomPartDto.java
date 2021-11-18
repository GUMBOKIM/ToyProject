package com.union.placeorderAutomation.dto.manage.bom;

import com.union.placeorderAutomation.entity.BomPart;
import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class BomPartDto {
    private String companyCode;
    private String bwCode;
    private int amount;
    private String selectYn;

    public BomPartDto(BomPart bomPart) {
        Part part = bomPart.getPart();
        this.companyCode = part.getCompany().getCompanyCode();
        this.bwCode = part.getBwCode();
        this.amount = bomPart.getAmount();
        this.selectYn = bomPart.getSelectYn();
    }
}
