package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.part.stock.PartStockDetailDto;
import com.union.placeorderAutomation.dto.task.part.stock.PartStockExcelDto;
import com.union.placeorderAutomation.dto.task.part.stock.PartStockModifyDto;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.PartInventory;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 재고 조회 페이지에서 사용
@Transactional
@RequiredArgsConstructor
@Service
public class TaskPartStockService {

    private final PartInventoryRepository partInventoryRepo;


    // 전체 재고 조회(부품 정보, 수량 합계)
    @Transactional(readOnly = true)
    public List<com.union.placeorderAutomation.dto.task.part.stock.PartStockDto> getPartStockListAll() {
        List<com.union.placeorderAutomation.dto.task.part.stock.PartStockDto> partStockList = new ArrayList<>();
        List<Object[]> partList = partInventoryRepo.findPartStockInventoryList();
        for (Object[] part : partList) {
            partStockList.add(new com.union.placeorderAutomation.dto.task.part.stock.PartStockDto(part));
        }
        return partStockList;
    }

    // 회사 이름으로 재고 조회(부품 정보, 수량 합계)
    @Transactional(readOnly = true)
    public List<com.union.placeorderAutomation.dto.task.part.stock.PartStockDto> getPartStockList(String companyCode) {
        List<com.union.placeorderAutomation.dto.task.part.stock.PartStockDto> partStockList = new ArrayList<>();
        List<Object[]> partList = partInventoryRepo.findPartStockInventoryListByCompanyCode(companyCode);
        for (Object[] part : partList) {
            partStockList.add(new com.union.placeorderAutomation.dto.task.part.stock.PartStockDto(part));
        }
        return partStockList;
    }

    // BW CODE로 재고 상세 조회(로트, 수량)
    @Transactional(readOnly = true)
    public List<PartStockDetailDto> getPartStockDetailList(String partBwCode) {
        List<PartStockDetailDto> partStockDetailList = new ArrayList<>();
        List<PartInventory> partInventories = partInventoryRepo.findInventoryListByPart(partBwCode);
        for (PartInventory partInventory : partInventories) {
            partStockDetailList.add(new PartStockDetailDto(partInventory));
        }
        return partStockDetailList;
    }

    public void modifyInventory(Long inventoryId, PartStockModifyDto modifyDto) {
        Optional<PartInventory> originInventoryOpt = partInventoryRepo.findById(inventoryId);
        if (originInventoryOpt.isPresent()) {
            PartInventory originInventory = originInventoryOpt.get();
            String bwCode = originInventory.getPart().getBwCode();
            String originLot = originInventory.getLot();
            int originQuantity = originInventory.getStock();
            int loadAmount = modifyDto.getLoadAmount();
            if(originLot.equals(modifyDto.getLot())){
                originInventory.setLoadAmount(modifyDto.getLoadAmount());
            } else {
                Optional<PartInventory> targetInventoryOpt = partInventoryRepo.findByPartAndLot(bwCode, modifyDto.getLot());
                if (targetInventoryOpt.isPresent()) {
                    PartInventory targetInventory = targetInventoryOpt.get();
                    int targetQuantity = targetInventory.getStock();
                    targetInventory.setStock(targetQuantity + originQuantity);
                    targetInventory.setLoadAmount(loadAmount);
                    partInventoryRepo.save(targetInventory);
                } else if (targetInventoryOpt.isEmpty()) {
                    PartInventory newInventory = PartInventory.builder()
                            .part(originInventory.getPart())
                            .lot(modifyDto.getLot())
                            .loadAmount(loadAmount)
                            .stock(originQuantity)
                            .build();
                    partInventoryRepo.save(newInventory);
                }
                originInventory.setStock(0);
            }
            partInventoryRepo.save(originInventory);
        }
    }

    public List<PartStockExcelDto> getInventoryTotal(String companyCode) {
        Company company = Company.builder().companyCode(companyCode).build();
        List<PartInventory> partInventoryList = partInventoryRepo.findPartInventoryByCompany(company);

        List<PartStockExcelDto> result = new ArrayList<>();
        for (PartInventory partInventory : partInventoryList) {
            result.add(new PartStockExcelDto(partInventory));
        }
        return result;
    }
}
