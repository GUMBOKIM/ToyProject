package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.part.*;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartInventory;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class PartStockService {

    private final PartLogService partLogService;
    private final PartRepository partRepo;
    private final PartInventoryRepository partInventoryRepo;


    public PartCheckDto[] checkPart(PartCheckDto[] partCheckDtoList) {
        for(PartCheckDto partCheckDto : partCheckDtoList){
            Optional<Part> findPart = partRepo.findByBwCode(partCheckDto.getBwCode());
            if(findPart.isPresent()){
                Part part = findPart.get();
                partCheckDto.setCheck("Y");
                partCheckDto.setCompanyName(part.getCompany().getCompanyName());
                partCheckDto.setSpCode(part.getSpCode());
                partCheckDto.setPoCode(part.getPoCode());
                partCheckDto.setName(part.getPartName());
            } else {
                partCheckDto.setCheck("N");
            }
        }
        return partCheckDtoList;
    }

    @Transactional(readOnly = true)
    public List<PartStockDto> getPartStockListAll() {
        List<PartStockDto> partStockList = new ArrayList<>();
        List<Object[]> partList = partInventoryRepo.findPartStockInventoryList();
        partList.forEach(part -> partStockList.add(new PartStockDto(part)));
        return partStockList;
    }

    @Transactional(readOnly = true)
    public List<PartStockDto> getPartStockList(String companyCode) {
        List<PartStockDto> partStockList = new ArrayList<>();
        List<Object[]> partList = partInventoryRepo.findPartStockInventoryListByCompanyCode(companyCode);
        partList.forEach(part -> partStockList.add(new PartStockDto(part)));
        return partStockList;
    }

    @Transactional(readOnly = true)
    public List<PartStockDetailDto> getPartStockDetailList(String partBwCode) {
        List<PartStockDetailDto> partStockDetailList = new ArrayList<>();
        List<PartInventory> partInventories = partInventoryRepo.findInventoryListByPart(partBwCode);
        partInventories.forEach(partInventory -> partStockDetailList.add(new PartStockDetailDto(partInventory)));
        return partStockDetailList;
    }

    public void removePartStock(Long partInventoryId) {
        partInventoryRepo.deleteById(partInventoryId);
    }

    public PartStockDetailDto manualModifyPartStock(StockRequestDto stockRequest) {
        Optional<Part> partOpt = partRepo.findByBwCode(stockRequest.getPartBwCode());
        if (partOpt.isPresent()) {
            Part part = partOpt.get();
            Optional<PartInventory> originInventoryOpt = partInventoryRepo.findByPartAndLot(part, stockRequest.getLot());
            if (originInventoryOpt.isPresent()) {
                PartInventory originInventory = originInventoryOpt.get();
                if (originInventory != null) {
                    if (originInventory.getStock() + stockRequest.getStock() >= 0) {
                        originInventory.setStock(originInventory.getStock() + stockRequest.getStock());
                        partInventoryRepo.save(originInventory);
                        partLogService.createPartLog(part, "M", stockRequest.getStock(), LocalDateTime.now(), "");
                        return new PartStockDetailDto(originInventory);
                    }
                } else {
                    if (stockRequest.getStock() > 0) {
                        PartInventory newInventory = PartInventory.builder()
                                .part(part)
                                .useYn("Y")
                                .lot(stockRequest.getLot())
                                .stock(stockRequest.getStock())
                                .build();
                        partInventoryRepo.save(newInventory);

                        partLogService.createPartLog(part, "M", stockRequest.getStock(), LocalDateTime.now(), "");
                        return new PartStockDetailDto(newInventory);
                    }
                }
            }
        }
        return null;
    }

    public IncomeResultDto incomePartStock(List<StockRequestDto> stockRequestList) {
        IncomeResultDto result = new IncomeResultDto();
        stockRequestList.forEach(request -> {
            if (request.getStock() > 0) {
                Optional<Part> partOpt = partRepo.findByBwCode(request.getPartBwCode());
                if (partOpt.isPresent()) {
                    Part part = partOpt.get();
                    Optional<PartInventory> originInventoryOpt = partInventoryRepo.findByPartAndLot(part, request.getLot());
                    if (originInventoryOpt.isPresent()) {
                        PartInventory originInventory = originInventoryOpt.get();
                        originInventory.setStock(originInventory.getStock() + request.getStock());
                        partInventoryRepo.save(originInventory);
                        result.getSuccess().add(new PartStockDetailDto(originInventory));

                        partLogService.createPartLog(part, "I", request.getStock(), LocalDateTime.now(), null);
                    } else {
                        PartInventory newInventory = PartInventory.builder()
                                .part(part)
                                .useYn("Y")
                                .lot(request.getLot())
                                .stock(request.getStock())
                                .build();
                        partInventoryRepo.save(newInventory);
                        result.getSuccess().add(new PartStockDetailDto(newInventory));

                        partLogService.createPartLog(part, "I", request.getStock(), LocalDateTime.now(), null);
                    }
                } else {
                    result.getFail().add(request);
                }
            } else{
                result.getFail().add(request);
            }
        });
        return result;
    }

}
