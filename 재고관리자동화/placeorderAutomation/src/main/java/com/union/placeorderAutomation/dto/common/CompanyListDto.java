package com.union.placeorderAutomation.dto.common;

import com.union.placeorderAutomation.entity.Company;
import lombok.Data;

@Data
public class CompanyListDto {
    private String companyCode;
    private String companyName;

    public CompanyListDto(Company company) {
        this.companyCode = company.getCompanyCode();
        this.companyName = company.getCompanyName();
    }
}
