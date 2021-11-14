package com.union.placeorderAutomation.service.common;

import com.union.placeorderAutomation.dto.common.CompanyListDto;
import com.union.placeorderAutomation.dto.common.PlantDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Plant;
import com.union.placeorderAutomation.repository.CompanyRepository;
import com.union.placeorderAutomation.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommonService {
    private final CompanyRepository companyRepo;
    private final PlantRepository plantRepository;

    private List<CompanyListDto> companyList;

    @Transactional(readOnly = true)
    public List<CompanyListDto> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        List<CompanyListDto> result = new ArrayList<>();
        companyList.forEach(company -> result.add(new CompanyListDto(company)));
        return result;
    }

    @Transactional(readOnly = true)
    public List<PlantDto> getPlantList() {
        List<Plant> plantList = plantRepository.findAll();
        List<PlantDto> result = new ArrayList<>();
        plantList.forEach(plant -> result.add(new PlantDto(plant)));
        return result;
    }


    public void addCompanyList(Model model) {
        if (companyList == null) {
            companyList = getCompanyList();
        }
        model.addAttribute("companyList", companyList);
    }
}
