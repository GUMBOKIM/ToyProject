package com.union.placeorderAutomation.dto.manage;

import com.union.placeorderAutomation.entity.BomPart;
import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class BomPartDto {
    private String companyName;
    private String bwCode;
    private int amount;

    public BomPartDto(BomPart bomPart) {
        Part part = bomPart.getPart();
        this.companyName = part.getCompany().getCompanyName();
        this.bwCode = part.getBwCode();
        this.amount = bomPart.getAmount();
    }
}
