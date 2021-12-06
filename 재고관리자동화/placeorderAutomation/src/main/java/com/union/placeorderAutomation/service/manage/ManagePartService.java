package com.union.placeorderAutomation.service.manage;

import com.union.placeorderAutomation.dto.manage.part.PartReqDto;
import com.union.placeorderAutomation.dto.manage.part.PartResDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ManagePartService {
    private final PartRepository partRepo;

    @Transactional(readOnly = true)
    public List<PartResDto> getPartList() {
        List<Part> partList = partRepo.findAll(Sort.by(Sort.Direction.ASC, "bwCode"));
        List<PartResDto> result = new ArrayList<>();
        for(Part part : partList){
            result.add(new PartResDto(part));
        }
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
                .inventoryBwCode(request.getInventoryBwCode())
                .spCode(request.getSpCode())
                .poCode1(request.getPoCode1())
                .poCode2(request.getPoCode2())
                .location1(request.getLocation1())
                .location2(request.getLocation2())
                .loadAmount(request.getLoadAmount())
                .selectYn(request.getSelectYn())
                .useYn(request.getUseYn())
                .build();
        partRepo.save(part);
        return new PartResDto(part);
    }

    public void modifyPart(PartReqDto request) {
        Company company = Company.builder()
                .companyCode(request.getCompanyCode())
                .build();
        Optional<Part> partOpt = partRepo.findByBwCode(request.getBwCode());
        if (partOpt.isPresent()) {
            Part part = partOpt.get();
            part.setCompany(company);
            part.setPartName(request.getPartName());
            part.setBwCode(request.getBwCode());
            part.setInventoryBwCode(request.getInventoryBwCode());
            part.setSpCode(request.getSpCode());
            part.setPoCode1(request.getPoCode1());
            part.setPoCode2(request.getPoCode2());
            part.setLocation1(request.getLocation1());
            part.setLocation2(request.getLocation2());
            part.setLoadAmount(request.getLoadAmount());
            part.setSelectYn(request.getSelectYn());
            part.setUseYn(request.getUseYn());
            partRepo.save(part);
        }
    }

    public void deletePart(String partBwCode) {
        partRepo.delete(Part.builder().bwCode(partBwCode).build());
    }

    @Transactional(readOnly = true)
    public List<PartResDto> getPartListAllByCompanyCode(String companyCode) {
        List<Part> partList = new ArrayList<>() ;
        partList.addAll(partRepo.findPartByCompany(companyCode));
        partList.addAll(partRepo.findSelectPartByCompany(companyCode));
        List<PartResDto> result = new ArrayList<>();
        for (Part part : partList) {
            result.add(new PartResDto(part));
        }
        return result;
    }
}
