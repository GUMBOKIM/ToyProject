package com.union.placeorderAutomation.dto.task;

import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

@Data
public class PartStockDto {
    private String bwCode;
    private String spCode;
    private String poCode;
    private int stock;

    public PartStockDto(Part part) {
        this.bwCode = part.getBwCode();
        this.spCode = part.getSpCode();
        this.poCode = part.getPoCode();
        this.stock = part.sumStock();
    }
}
