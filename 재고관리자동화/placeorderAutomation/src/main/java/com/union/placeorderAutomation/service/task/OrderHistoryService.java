package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.outgoing.OrderHistoryDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.entity.*;
import com.union.placeorderAutomation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepo;
    private final CompanyRepository companyRepo;
    private final PlantRepository plantRepo;
    private final OutcomeLogRepository outcomeLogRepo;
    private final PartInventoryRepository partInventoryRepo;

    @Transactional(readOnly = true)
    public List<OrderHistoryDto> findOrderHistoryList(String companyCode, String date) {
        List<OrderHistoryDto> result = new ArrayList<>();
        List<OrderHistory> orderHistoryList = orderHistoryRepo.findOrderHistoryList(Company.builder().companyCode(companyCode).build(), date);
        for (OrderHistory orderHistory : orderHistoryList) {
            result.add(new OrderHistoryDto(orderHistory));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryDto> findLatestOrderHistoryList(String date) {
        List<OrderHistoryDto> result = new ArrayList<>();
        for (Company company: companyRepo.findAll()) {
            for(Plant plant: plantRepo.findAll()){
                OrderHistory history = null;
                for(OrderHistory orderHistory : orderHistoryRepo.findOrderHistoryDetail(company, plant, date)){
                    if(history == null){
                        history = orderHistory;
                    } else {
                        if(orderHistory.getOrderSeq() > history.getOrderSeq()){
                            history = orderHistory;
                        }
                    }
                }
                if(history != null) {
                    result.add(new OrderHistoryDto(history));
                }
            }
        }

        return result;
    }

    public void cancelOrder(OutgoingSubmitDto outgoingSubmitDto) {
        Company company = Company.builder().companyCode(outgoingSubmitDto.getCompanyCode()).build();
        Plant plant = Plant.builder().plantCode(outgoingSubmitDto.getPlantCode()).build();

        List<OutcomeLog> outcomeLogList = outcomeLogRepo.findOutcomeLogByCompanyAndPlantAndDateAndOrderSeq(
                company,
                plant,
                outgoingSubmitDto.getDate(),
                outgoingSubmitDto.getOrderSeq()
        );

        for (OutcomeLog outcomeLog : outcomeLogList) {
            Optional<PartInventory> partInventoryOpt = partInventoryRepo.findByPartAndLot(outcomeLog.getPart().getBwCode(), outcomeLog.getLot());
            if (partInventoryOpt.isPresent()) {
                PartInventory partInventory = partInventoryOpt.get();
                partInventory.setStock(partInventory.getStock() + outcomeLog.getAmount());
                partInventoryRepo.save(partInventory);
            } else {
                PartInventory newPartInventory = PartInventory.builder()
                        .part(outcomeLog.getPart())
                        .lot(outcomeLog.getLot())
                        .stock(outcomeLog.getAmount())
                        .build();
                partInventoryRepo.save(newPartInventory);
            }
            outcomeLogRepo.delete(outcomeLog);
        }

        OrderHistory orderHistory = orderHistoryRepo.findOrderHistoryForDelete(
                Company.builder().companyCode(outgoingSubmitDto.getCompanyCode()).build(),
                Plant.builder().plantCode(outgoingSubmitDto.getPlantCode()).build(),
                outgoingSubmitDto.getDate(),
                outgoingSubmitDto.getOrderSeq()
        );
        orderHistoryRepo.delete(orderHistory);
    }


}
