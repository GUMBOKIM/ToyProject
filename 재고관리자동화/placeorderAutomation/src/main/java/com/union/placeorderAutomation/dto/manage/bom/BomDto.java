package com.union.placeorderAutomation.dto.manage.bom;

import com.union.placeorderAutomation.entity.Bom;
import lombok.Data;

@Data
public class BomDto {
    private String bwCode;

    public BomDto(Bom bom) {
        this.bwCode = bom.getBwCode();
    }
}
