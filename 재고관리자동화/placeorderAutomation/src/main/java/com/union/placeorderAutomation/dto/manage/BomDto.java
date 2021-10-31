package com.union.placeorderAutomation.dto.manage;

import com.union.placeorderAutomation.entity.Bom;
import lombok.Data;

@Data
public class BomDto {
    private String bwCode;

    public BomDto(Bom bom) {
        this.bwCode = bom.getBwCode();
    }
}
