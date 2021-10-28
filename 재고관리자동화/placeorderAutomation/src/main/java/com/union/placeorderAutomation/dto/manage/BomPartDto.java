package com.union.placeorderAutomation.dto.manage;

import com.union.placeorderAutomation.entity.BomPart;
import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class BomPartDto {
    private Long bomPartId;
    private String companyName;
    private String partBwCode;

    public BomPartDto(BomPart bomPart) {
        this.bomPartId = bomPart.getBomPartId();
        Part part = bomPart.getPart();
        this.companyName = part.getCompany().getCompanyName();
        this.partBwCode = part.getBwCode();
    }
}
