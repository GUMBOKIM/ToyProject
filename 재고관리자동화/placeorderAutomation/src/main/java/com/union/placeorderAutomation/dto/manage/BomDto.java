package com.union.placeorderAutomation.dto.manage;

import com.union.placeorderAutomation.entity.Bom;
import lombok.Data;

@Data
public class BomDto {
    private Long bomId;
    private String bwCode;

    public BomDto(Bom bom) {
        this.bomId = bom.getBomId();
        this.bwCode = bom.getBwCode();
    }
}
