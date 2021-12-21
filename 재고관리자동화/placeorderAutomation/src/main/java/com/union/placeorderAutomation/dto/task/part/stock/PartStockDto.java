package com.union.placeorderAutomation.dto.task.part.stock;

import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartStockDto {
    private String companyName;
    private String bwCode;
    private String spCode;
    private int stock;

    public PartStockDto(Part part) {
        this.companyName = part.getCompany().getCompanyName();
        this.bwCode = part.getBwCode();
        this.spCode = part.getSpCode();
        this.stock = part.getStock();
    }
}
