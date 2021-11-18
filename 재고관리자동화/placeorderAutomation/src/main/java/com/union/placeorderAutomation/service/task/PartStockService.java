package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.part.manage.PartCheckDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendReqDto;
import com.union.placeorderAutomation.dto.task.part.manage.StockSendResDto;
import com.union.placeorderAutomation.dto.task.part.status.PartStockDetailDto;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartInventory;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
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
public class PartStockService {

    private final PartLogService partLogService;
    private final PartRepository partRepo;
    private final PartInventoryRepository partInventoryRepo;

    // 재고 조회 페이지에서 사용
    // 전체 재고 조회(부품 정보, 수량 합계)
    @Transactional(readOnly = true)
    public List<com.union.placeorderAutomation.dto.task.part.status.PartStockDto> getPartStockListAll() {
        List<com.union.placeorderAutomation.dto.task.part.status.PartStockDto> partStockList = new ArrayList<>();
        List<Object[]> partList = partInventoryRepo.findPartStockInventoryList();
        partList.forEach(part -> partStockList.add(new com.union.placeorderAutomation.dto.task.part.status.PartStockDto(part)));
        return partStockList;
    }

    // 회사 이름으로 재고 조회(부품 정보, 수량 합계)
    @Transactional(readOnly = true)
    public List<com.union.placeorderAutomation.dto.task.part.status.PartStockDto> getPartStockList(String companyCode) {
        List<com.union.placeorderAutomation.dto.task.part.status.PartStockDto> partStockList = new ArrayList<>();
        List<Object[]> partList = partInventoryRepo.findPartStockInventoryListByCompanyCode(companyCode);
        partList.forEach(part -> partStockList.add(new com.union.placeorderAutomation.dto.task.part.status.PartStockDto(part)));
        return partStockList;
    }

    // BW CODE로 재고 상세 조회(로트, 수량)
    @Transactional(readOnly = true)
    public List<PartStockDetailDto> getPartStockDetailList(String partBwCode) {
        List<PartStockDetailDto> partStockDetailList = new ArrayList<>();
        List<PartInventory> partInventories = partInventoryRepo.findInventoryListByPartBwCode(partBwCode);
        partInventories.forEach(partInventory -> partStockDetailList.add(new PartStockDetailDto(partInventory)));
        return partStockDetailList;
    }

    // 입고 페이지에서 사용
    // BW CODE를 가진 부품이 있는지 없는 체크 -> 없을 경우, 체크해서 반환(필드명 check -> Y,N)
    public PartCheckDto[] checkPart(PartCheckDto[] partCheckDtoList) {
        for (PartCheckDto partCheckDto : partCheckDtoList) {
            Optional<Part> findPart = partRepo.findByBwCode(partCheckDto.getBwCode());
            if (findPart.isPresent()) {
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


    public List<StockSendResDto> incomePartStock(StockSendReqDto stockSendReqDto) {
        String date = stockSendReqDto.getDate();
        String orderSeq = stockSendReqDto.getOrderSeq();
        PartCheckDto[] partCheckArr = stockSendReqDto.getPartCheck();

        List<StockSendResDto> result = new ArrayList<>();
        for (PartCheckDto partCheck : partCheckArr) {
            String bwCode = partCheck.getBwCode();
            String lot = partCheck.getLot();
            int quantity = partCheck.getQuantity();
            StockSendResDto sendResDto = new StockSendResDto();
            sendResDto.setBwCode(bwCode);
            sendResDto.setLot(lot);
            sendResDto.setQuantity(quantity);

            Optional<PartInventory> findPartInventory = partInventoryRepo.findByPartAndLot(bwCode, lot);
            if (findPartInventory.isPresent()) {
                PartInventory partInventory = findPartInventory.get();
                sendResDto.setBeforeStock(partInventory.getStock());

                partInventory.setStock(partInventory.getStock() + quantity);
                partInventoryRepo.save(partInventory);
                sendResDto.setAfterStock(partInventory.getStock());
            } else if (findPartInventory.isEmpty()) {
                sendResDto.setBeforeStock(0);

                Part part = new Part();
                part.setBwCode(bwCode);
                PartInventory newPartInventory = PartInventory.builder()
                        .part(part)
                        .lot(lot)
                        .stock(quantity)
                        .build();
                partInventoryRepo.save(newPartInventory);
                sendResDto.setAfterStock(newPartInventory.getStock());
            }

            sendResDto.setCheck("Y");
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
            StockSendResDto sendResDto = new StockSendResDto();
            sendResDto.setBwCode(bwCode);
            sendResDto.setLot(lot);
            sendResDto.setQuantity(quantity);

            Optional<PartInventory> findPartInventory = partInventoryRepo.findByPartAndLot(bwCode, lot);
            if (findPartInventory.isPresent()) {
                PartInventory partInventory = findPartInventory.get();
                sendResDto.setBeforeStock(partInventory.getStock());
                if (partInventory.getStock() >= partCheck.getQuantity()) {
                    sendResDto.setAfterStock(partInventory.getStock() - quantity);
                    partInventory.setStock(partInventory.getStock() - quantity);
                    partInventoryRepo.save(partInventory);
                } else {
                    sendResDto.setAfterStock(partInventory.getStock());
                }
            } else {
                sendResDto.setBeforeStock(0);
                sendResDto.setAfterStock(0);
            }

            if (sendResDto.getBeforeStock() == sendResDto.getAfterStock() || sendResDto.getBeforeStock() == 0) {
                sendResDto.setCheck("N");
            } else {
                sendResDto.setCheck("Y");
            }
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

            StockSendResDto sendResDto = new StockSendResDto();
            sendResDto.setBwCode(bwCode);
            sendResDto.setLot(lot);
            sendResDto.setQuantity(quantity);
            Optional<PartInventory> findPartInventory = partInventoryRepo.findByPartAndLot(bwCode, lot);
            if (findPartInventory.isPresent()) {
                PartInventory partInventory = findPartInventory.get();
                sendResDto.setBeforeStock(partInventory.getStock());
                if (partInventory.getStock() >= partCheck.getQuantity()) {
                    sendResDto.setAfterStock(partInventory.getStock() - quantity);
                    partInventory.setStock(partInventory.getStock() - quantity);
                    partInventoryRepo.save(partInventory);
                } else {
                    sendResDto.setAfterStock(partInventory.getStock());
                }
            } else {
                sendResDto.setBeforeStock(0);
                sendResDto.setAfterStock(0);
            }

            if (sendResDto.getBeforeStock() == sendResDto.getAfterStock() || sendResDto.getBeforeStock() == 0) {
                sendResDto.setCheck("N");
            } else {
                sendResDto.setCheck("Y");
            }
            result.add(sendResDto);
        }
        return result;
    }
}
