package com.union.placeorderAutomation.service.manage;

import com.union.placeorderAutomation.dto.manage.BomDto;
import com.union.placeorderAutomation.dto.manage.BomPartDto;
import com.union.placeorderAutomation.entity.Bom;
import com.union.placeorderAutomation.entity.BomPart;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.BomPartRepository;
import com.union.placeorderAutomation.repository.BomRepository;
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
public class BomManageService {

    private final BomRepository bomRepo;
    private final BomPartRepository bomPartRepo;
    private final PartRepository partRepo;

    @Transactional(readOnly = true)
    public List<String> getBomList() {
        List<Bom> bomEntityList = bomRepo.findAll(Sort.by(Sort.Direction.ASC, "bwCode"));
        List<String> result = new ArrayList<>();
        bomEntityList.forEach(bom -> result.add(bom.getBwCode()));
        return result;
    }

    public BomDto createBom(String bwCode) {
        Bom bom = Bom.builder()
                .bwCode(bwCode)
                .build();
        bomRepo.save(bom);
        return new BomDto(bom);
    }

    public void deleteBom(String bwCode) {
        Bom bom = Bom.builder()
                .bwCode(bwCode)
                .build();
        bomRepo.delete(bom);
    }

    @Transactional(readOnly = true)
    public List<BomPartDto> getBomPartList(String bomBwCode) {
        Bom bom = bomRepo.findByBwCode(bomBwCode);
        List<BomPart> bomPartList = bom.getBomParts();
        List<BomPartDto> partList = new ArrayList<>();
        bomPartList.forEach(bomPart -> partList.add(new BomPartDto(bomPart)));
        return partList;
    }

    public BomPartDto createBomPart(String bomBwCode, String partBwCode) {
        Bom bom = bomRepo.findByBwCode(bomBwCode);
        Optional<Part> partOpt = partRepo.findByBwCode(partBwCode);
        if(partOpt.isPresent()) {
            Part part = partOpt.get();
            BomPart bomPart = BomPart.builder()
                    .bom(bom)
                    .part(part)
                    .build();
            bomPartRepo.save(bomPart);
            return new BomPartDto(bomPart);
        }
        return null;
    }

    public void deleteBomPart(String bomBwCode, String partBwCode) {
        Bom bom = bomRepo.findByBwCode(bomBwCode);
        Optional<Part> partOpt = partRepo.findByBwCode(partBwCode);
        if(partOpt.isPresent()) {
            Part part = partOpt.get();
            BomPart bompart = bomPartRepo.findByBomAndPart(bom, part);
            bomPartRepo.delete(bompart);
        }
    }
}
