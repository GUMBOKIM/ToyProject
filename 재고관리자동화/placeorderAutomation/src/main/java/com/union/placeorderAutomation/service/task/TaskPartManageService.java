package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.common.CreateLogDto;
import com.union.placeorderAutomation.dto.task.part.manage.PartCheckDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendReqDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendResDto;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.common.PartLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskPartManageService {

    private final PartLogService partLogService;
    private final PartRepository partRepo;

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
            int quantity = partCheck.getQuantity();
            int beforeStock = 0;
            int afterStock = 0;
            String check = "Y";

            Optional<Part> partOpt = partRepo.findByBwCode(bwCode);
            if (partOpt.isPresent()) {
                Part part = partOpt.get();
                beforeStock = part.getStock();
                afterStock = beforeStock + quantity;

                part.setStock(afterStock);
                partRepo.save(part);
            }
            StockSendResDto sendResDto = new StockSendResDto(bwCode, quantity, beforeStock, afterStock, check);
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
            int quantity = partCheck.getQuantity();
            int beforeStock = 0;
            int afterStock = 0;
            String check = "Y";

            Optional<Part> partOpt = partRepo.findByBwCode(bwCode);
            if (partOpt.isPresent()) {
                Part part = partOpt.get();
                beforeStock = part.getStock();
                if (part.getStock() >= partCheck.getQuantity()) {
                    afterStock = beforeStock - quantity;
                    part.setStock(afterStock);
                    partRepo.save(part);
                } else {
                    afterStock = part.getStock();
                }
            }

            if (beforeStock == afterStock || beforeStock == 0) {
                check = "N";
            } else {
                check = "Y";
            }
            StockSendResDto sendResDto = new StockSendResDto(bwCode, quantity, beforeStock, afterStock, check);
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
            int quantity = partCheck.getQuantity();

            int beforeStock = 0;
            int afterStock = 0;
            String check = "Y";

            Optional<Part> partOpt = partRepo.findByBwCode(bwCode);
            if (partOpt.isPresent()) {
                Part part = partOpt.get();
                beforeStock = part.getStock();
                if (part.getStock() >= partCheck.getQuantity()) {
                    afterStock = part.getStock() - quantity;
                    part.setStock(afterStock);
                    partRepo.save(part);
                } else {
                    afterStock = beforeStock;
                }
            }

            if (beforeStock == afterStock || beforeStock == 0) {
                check = "N";
            } else {
                check = "Y";
            }
            StockSendResDto sendResDto = new StockSendResDto(bwCode, quantity, beforeStock, afterStock, check);
            partLogService.createDefectiveLog(new CreateLogDto(date, orderSeq, sendResDto));
            result.add(sendResDto);
        }
        return result;
    }
}
