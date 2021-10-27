package com.union.placeorderAutomation.service.manage;

import com.union.placeorderAutomation.dto.manage.part.PartReqDto;
import com.union.placeorderAutomation.dto.manage.part.PartResDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.CompanyRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PartManageService {
    private final PartRepository partRepo;

    @Transactional(readOnly = true)
    public List<PartResDto> getPartList() {
        List<Part> partList = partRepo.findAll();
        List<PartResDto> result = new ArrayList<>();
        partList.forEach(part -> result.add(new PartResDto(part)));
        return result;
    }

    public PartResDto createPart(PartReqDto request) {
        Company company = Company.builder()
                .companyId(request.getCompanyId())
                .build();
        Part part = Part.builder()
                .company(company)
                .partName(request.getPartName())
                .bwCode(request.getBwCode())
                .spCode(request.getSpCode())
                .poCode(request.getPoCode())
                .loadAmount(request.getLoadAmount())
                .location(request.getLocation())
                .description(request.getDescription())
                .build();
        partRepo.save(part);
        return new PartResDto(part);
    }

    public PartResDto updatePart(Long partId, PartReqDto request) {
        Company company = Company.builder()
                .companyId(request.getCompanyId())
                .build();
        Part part = Part.builder()
                .partId(partId)
                .company(company)
                .partName(request.getPartName())
                .bwCode(request.getBwCode())
                .spCode(request.getSpCode())
                .poCode(request.getPoCode())
                .loadAmount(request.getLoadAmount())
                .location(request.getLocation())
                .description(request.getDescription())
                .build();
        partRepo.save(part);
        return new PartResDto(part);
    }

    public void deletePart(Long partId) {
        partRepo.deleteById(partId);
    }

}
