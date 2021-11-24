package com.union.placeorderAutomation.service.common;

import com.union.placeorderAutomation.dto.common.CompanyListDto;
import com.union.placeorderAutomation.dto.common.PlantDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.OrderHistory;
import com.union.placeorderAutomation.entity.Plant;
import com.union.placeorderAutomation.repository.CompanyRepository;
import com.union.placeorderAutomation.repository.OrderHistoryRepository;
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
    private final OrderHistoryRepository orderHistoryRepo;

    private List<CompanyListDto> companyList;
    private List<PlantDto> plantList;

    @Transactional(readOnly = true)
    public List<Integer> findCompanyOrderHistoryList(String companyCode, String plantCode, String date){
        Company company = Company.builder().companyCode(companyCode).build();
        Plant plant = Plant.builder().plantCode(plantCode).build();
        List<Integer> findOrderHistory = orderHistoryRepo.findOrderHistory(company, plant, date);

        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            result.add(i);
        }
        for(Integer orderSeq : findOrderHistory){
            result.remove(result.indexOf(orderSeq));
        }
        return result;
    }

    public void addCompanyOrderHistory(String companyCode, String plantCode, String date, int orderSeq){
        Company company = Company.builder().companyCode(companyCode).build();
        Plant plant = Plant.builder().plantCode(plantCode).build();
        OrderHistory orderHistory = OrderHistory.builder()
                .company(company)
                .plant(plant)
                .orderSeq(orderSeq)
                .date(date)
                .build();
        orderHistoryRepo.save(orderHistory);
    }


    //Model 추가
    public void addCompanyList(Model model) {
        if (companyList == null) {
            companyList = getCompanyList();
        }
        model.addAttribute("companyList", companyList);
    }

    public void addPlantList(Model model){
        if(plantList == null){
            plantList = getPlantList();
        }
        model.addAttribute("plantList", plantList);
    }


    @Transactional(readOnly = true)
    public List<CompanyListDto> getCompanyList() {
        List<Company> companyList = companyRepo.findAll();
        List<CompanyListDto> result = new ArrayList<>();
        companyList.forEach(company -> result.add(new CompanyListDto(company.getCompanyCode(), company.getCompanyName())));
        return result;
    }

    @Transactional(readOnly = true)
    public List<PlantDto> getPlantList() {
        List<Plant> plantList = plantRepository.findAll();
        List<PlantDto> result = new ArrayList<>();
        plantList.forEach(plant -> result.add(new PlantDto(plant)));
        return result;
    }
}
