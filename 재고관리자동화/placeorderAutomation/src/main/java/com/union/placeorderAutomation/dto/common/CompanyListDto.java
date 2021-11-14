package com.union.placeorderAutomation.dto.common;

import com.union.placeorderAutomation.entity.Company;
import lombok.Data;

@Data
public class CompanyListDto {
    private String companyCode;
    private String companyName;

    public CompanyListDto(String companyCode, String companyName) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }
}
