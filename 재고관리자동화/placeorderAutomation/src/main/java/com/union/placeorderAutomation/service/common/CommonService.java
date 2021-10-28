package com.union.placeorderAutomation.service.common;

import com.union.placeorderAutomation.dto.common.CompanyListDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommonService {
    private final CompanyRepository companyRepo;

    @Transactional(readOnly = true)
    public List<CompanyListDto> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        List<CompanyListDto> result = new ArrayList<>();
        companyList.forEach( company -> result.add(new CompanyListDto(company)));
        return result;
    }

}
