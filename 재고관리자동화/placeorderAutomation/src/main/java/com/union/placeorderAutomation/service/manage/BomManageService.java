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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BomManageService {

    private final BomRepository bomRepo;
    private final BomPartRepository bomPartRepo;
    private final PartRepository partRepo;

    @Transactional(readOnly = true)
    public List<BomDto> getBomList() {
        List<Bom> bomEntityList = bomRepo.findAll();
        List<BomDto> result = new ArrayList<>();
        bomEntityList.forEach(bom -> result.add(new BomDto(bom)));
        return result;
    }

    public BomDto createBom(String bwCode) {
        Bom bom = Bom.builder()
                .bwCode(bwCode)
                .build();
        bomRepo.save(bom);
        return new BomDto(bom);
    }

    public BomDto updateBom(Long bomId, String bwCode) {
        Bom bom = bomRepo.findById(bomId).get();
        bom.setBwCode(bwCode);
        bomRepo.save(bom);
        return new BomDto(bom);
    }

    public void deleteBom(Long bomId) {
        bomRepo.deleteById(bomId);
    }

    @Transactional(readOnly = true)
    public List<BomPartDto> getBomPartList(Long bomId) {
        Bom bom = bomRepo.findById(bomId).get();
        List<BomPart> bomPartList = bom.getBomParts();
        List<BomPartDto> partList = new ArrayList<>();
        bomPartList.forEach(bomPart -> partList.add(new BomPartDto(bomPart)));
        return partList;
    }

    public BomPartDto createBomPart(Long bomId, Long partId) {
        Bom bom = bomRepo.findById(bomId).get();
        Part part = partRepo.findById(partId).get();
        BomPart bomPart = BomPart.builder()
                .bom(bom)
                .part(part)
                .build();
        bomPartRepo.save(bomPart);
        return new BomPartDto(bomPart);
    }

    public void deleteBomPart(Long bomId, Long bomPartId) {
        bomPartRepo.deleteById(bomPartId);
    }

    @Transactional(readOnly = true)
    public Long findPartIdByBwCode(String bwCode){
        Part part = partRepo.findByBwCode(bwCode);
        return part.getPartId();
    }
}
