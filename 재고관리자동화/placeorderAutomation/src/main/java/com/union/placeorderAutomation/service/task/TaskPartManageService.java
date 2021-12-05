package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.common.CreateLogDto;
import com.union.placeorderAutomation.dto.task.part.manage.PartCheckDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendReqDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendResDto;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartInventory;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.common.PartLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class TaskPartManageService {

    private final PartLogService partLogService;
    private final PartRepository partRepo;
    private final PartInventoryRepository partInventoryRepo;

    // BW CODE를 가진 부품이 있는지 없는 체크 -> 없을 경우, 체크해서 반환(필드명 check -> Y,N)
    @Transactional(readOnly = true)
    public PartCheckDto[] checkPart(PartCheckDto[] partCheckDtoList) {
        for (PartCheckDto partCheckDto : partCheckDtoList) {
            Optional<Part> findPart = partRepo.findByBwCode(partCheckDto.getBwCode());
            if (findPart.isPresent()) {
                Part part = findPart.get();
                partCheckDto.setCheck("Y");
                partCheckDto.setCompanyName(part.getCompany().getCompanyName());
                partCheckDto.setSpCode(part.getSpCode());
                partCheckDto.setName(part.getPartName());
            } else {
                partCheckDto.setCheck("N");
            }
        }
        return partCheckDtoList;
    }


    public List<StockSendResDto> incomePartStock(StockSendReqDto stockSendReqDto) {
        String date = stockSendReqDto.getDate();
        String orderSeq = stockSendReqDto.getOrderSeq();
        PartCheckDto[] partCheckArr = stockSendReqDto.getPartCheck();

        List<StockSendResDto> result = new ArrayList<>();
        for (PartCheckDto partCheck : partCheckArr) {
            String bwCode = partCheck.getBwCode();
            String lot = partCheck.getLot();
            int quantity = partCheck.getQuantity();
            int beforeStock = 0;
            int afterStock = 0;
            String check = "Y";

            Optional<PartInventory> findPartInventory = partInventoryRepo.findByPartAndLot(bwCode, lot);
            if (findPartInventory.isPresent()) {
                PartInventory partInventory = findPartInventory.get();
                beforeStock = partInventory.getStock();
                afterStock = beforeStock + quantity;

                partInventory.setStock(afterStock);
                partInventoryRepo.save(partInventory);
            } else if (findPartInventory.isEmpty()) {
                afterStock = quantity;

                Part part = new Part();
                part.setBwCode(bwCode);
                PartInventory newPartInventory = PartInventory.builder()
                        .part(part)
                        .lot(lot)
                        .loadAmount(partCheck.getLoadAmount())
                        .stock(afterStock)
                        .build();
                partInventoryRepo.save(newPartInventory);
            }
            StockSendResDto sendResDto = new StockSendResDto(bwCode, lot, quantity, beforeStock, afterStock, check);
            partLogService.createIncomeLog(new CreateLogDto(date, orderSeq, sendResDto));
            result.add(sendResDto);
        }
        return result;
    }

    public List<StockSendResDto> modifyPartStock(StockSendReqDto stockSendReqDto) {
        String date = stockSendReqDto.getDate();
        String orderSeq = stockSendReqDto.getOrderSeq();
        PartCheckDto[] partCheckArr = stockSendReqDto.getPartCheck();
        List<StockSendResDto> result = new ArrayList<>();

        for (PartCheckDto partCheck : partCheckArr) {
            String bwCode = partCheck.getBwCode();
            String lot = partCheck.getLot();
            int quantity = partCheck.getQuantity();
            int beforeStock = 0;
            int afterStock = 0;
            String check = "Y";

            Optional<PartInventory> findPartInventory = partInventoryRepo.findByPartAndLot(bwCode, lot);
            if (findPartInventory.isPresent()) {
                PartInventory partInventory = findPartInventory.get();
                beforeStock = partInventory.getStock();
                if (partInventory.getStock() >= partCheck.getQuantity()) {
                    afterStock = beforeStock - quantity;
                    partInventory.setStock(afterStock);
                    partInventoryRepo.save(partInventory);
                } else {
                    afterStock = partInventory.getStock();
                }
            }

            if (beforeStock == afterStock || beforeStock == 0) {
                check = "N";
            } else {
                check = "Y";
            }
            StockSendResDto sendResDto = new StockSendResDto(bwCode, lot, quantity, beforeStock, afterStock, check);
            partLogService.createModifyLogs(new CreateLogDto(date, orderSeq, sendResDto));
            result.add(sendResDto);
        }
        return result;
    }

    public List<StockSendResDto> defectivePartStock(StockSendReqDto stockSendReqDto) {
        String date = stockSendReqDto.getDate();
        String orderSeq = stockSendReqDto.getOrderSeq();
        PartCheckDto[] partCheckArr = stockSendReqDto.getPartCheck();
        List<StockSendResDto> result = new ArrayList<>();

        for (PartCheckDto partCheck : partCheckArr) {
            String bwCode = partCheck.getBwCode();
            String lot = partCheck.getLot();
            int quantity = partCheck.getQuantity();

            int beforeStock = 0;
            int afterStock = 0;
            String check = "Y";

            Optional<PartInventory> findPartInventory = partInventoryRepo.findByPartAndLot(bwCode, lot);
            if (findPartInventory.isPresent()) {
                PartInventory partInventory = findPartInventory.get();
                beforeStock = partInventory.getStock();
                if (partInventory.getStock() >= partCheck.getQuantity()) {
                    afterStock = partInventory.getStock() - quantity;
                    partInventory.setStock(afterStock);
                    partInventoryRepo.save(partInventory);
                } else {
                    afterStock = beforeStock;
                }
            }

            if (beforeStock == afterStock || beforeStock == 0) {
                check = "N";
            } else {
                check = "Y";
            }
            StockSendResDto sendResDto = new StockSendResDto(bwCode, lot, quantity, beforeStock, afterStock, check);
            partLogService.createDefectiveLog(new CreateLogDto(date, orderSeq, sendResDto));
            result.add(sendResDto);
        }
        return result;
    }
}
