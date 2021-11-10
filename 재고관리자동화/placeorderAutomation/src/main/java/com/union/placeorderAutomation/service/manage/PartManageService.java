package com.union.placeorderAutomation.service.manage;

import com.union.placeorderAutomation.dto.manage.PartReqDto;
import com.union.placeorderAutomation.dto.manage.PartResDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .companyCode(request.getCompanyCode())
                .build();
        Part part = Part.builder()
                .company(company)
                .partName(request.getPartName())
                .bwCode(request.getBwCode())
                .spCode(request.getSpCode())
                .poCode(request.getPoCode())
                .loadAmount(request.getLoadAmount())
                .location(request.getLocation())
                .build();
        partRepo.save(part);
        return new PartResDto(part);
    }

    public void modifyPart(PartReqDto request) {
        Company company = Company.builder()
                .companyCode(request.getCompanyCode())
                .build();
        Part part = Part.builder()
                .company(company)
                .partName(request.getPartName())
                .bwCode(request.getBwCode())
                .spCode(request.getSpCode())
                .poCode(request.getPoCode())
                .loadAmount(request.getLoadAmount())
                .location(request.getLocation())
                .build();
        partRepo.save(part);
    }

    public void deletePart(String partBwCode) {
        partRepo.delete(Part.builder().bwCode(partBwCode).build());
    }

    @Transactional(readOnly = true)
    public List<PartResDto> getPartListByCompanyCode(String companyCode) {
        List<Part> partList = partRepo.findByCompany(companyCode);
        List<PartResDto> result = new ArrayList<>();
        partList.forEach(part -> result.add(new PartResDto(part)));
        return result;
    }
}
