package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.part.stock.PartStockDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// 재고 조회 페이지에서 사용
@Transactional
@RequiredArgsConstructor
@Service
public class TaskPartStockService {

    private final PartRepository partRepo;


    // 전체 재고 조회(부품 정보, 수량 합계)
    @Transactional(readOnly = true)
    public List<com.union.placeorderAutomation.dto.task.part.stock.PartStockDto> getPartStockListAll() {
        List<PartStockDto> result = new ArrayList<>();
        List<Part> partList = partRepo.findAll();
        for (Part part: partList) {
            result.add(new PartStockDto(part));
        }
        return result;
    }

    // 회사 이름으로 재고 조회(부품 정보, 수량 합계)
    @Transactional(readOnly = true)
    public List<com.union.placeorderAutomation.dto.task.part.stock.PartStockDto> getPartStockList(String companyCode) {
        List<PartStockDto> result = new ArrayList<>();
        List<Part> partList = partRepo.findPartsByCompany(Company.builder().companyCode(companyCode).build());
        for (Part part: partList) {
            result.add(new PartStockDto(part));
        }
        return result;
    }
}
