package com.union.placeorderAutomation.dto.common;

import com.union.placeorderAutomation.entity.Company;
import lombok.Data;

@Data
public class CompanyListDto {
    private Long companyId;
    private String companyCode;
    private String companyName;

    public CompanyListDto(Company company) {
        this.companyId = company.getCompanyId();
        this.companyCode = company.getCompanyCode();
        this.companyName = company.getCompanyName();
    }
}
