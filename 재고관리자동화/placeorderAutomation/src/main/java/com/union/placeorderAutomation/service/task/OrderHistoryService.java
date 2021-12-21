package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.outgoing.OrderHistoryDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutcomeLogDto;
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

    private final PartRepository partRepo;
    private final OrderHistoryRepository orderHistoryRepo;
    private final CompanyRepository companyRepo;
    private final PlantRepository plantRepo;
    private final OutcomeLogRepository outcomeLogRepo;

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
        Company company = companyRepo.findCompanyByCompanyName(outgoingSubmitDto.getCompanyCode());
        Plant plant = plantRepo.findPlantByPlantName(outgoingSubmitDto.getPlantCode());

        List<OutcomeLog> outcomeLogList = outcomeLogRepo.findOutcomeLogByCompanyAndPlantAndDateAndOrderSeq(
                company,
                plant,
                outgoingSubmitDto.getDate(),
                outgoingSubmitDto.getOrderSeq()
        );

        for (OutcomeLog outcomeLog : outcomeLogList) {
            Part part = outcomeLog.getPart();
            part.setStock(part.getStock() + outcomeLog.getAmount());
            partRepo.save(part);
            outcomeLogRepo.delete(outcomeLog);
        }

        OrderHistory orderHistory = orderHistoryRepo.findOrderHistoryForDelete(
                company,
                plant,
                outgoingSubmitDto.getDate(),
                outgoingSubmitDto.getOrderSeq()
        );
        orderHistoryRepo.delete(orderHistory);
    }


    public List<OutcomeLogDto> findOrderHistoryDetail(OutgoingSubmitDto submitDto) {
        Company company = companyRepo.findCompanyByCompanyName(submitDto.getCompanyCode());
        Plant plant = plantRepo.findPlantByPlantName(submitDto.getPlantCode());

        List<OutcomeLogDto> result = new ArrayList<>();
        List<OutcomeLog> outcomeLogList = outcomeLogRepo.findOutcomeLogByCompanyAndPlantAndDateAndOrderSeq(company, plant, submitDto.getDate(), submitDto.getOrderSeq());
        for (OutcomeLog outcomeLog: outcomeLogList) {
            result.add(new OutcomeLogDto(outcomeLog));
        }
        return result;
    }

    public OutcomeLogDto modifyOutcomeLog(Long outcomeLogId, int quantity) {
        OutcomeLog outcomeLog = outcomeLogRepo.getById(outcomeLogId);
        int changeQuantity = outcomeLog.getAmount() - quantity;
        outcomeLog.setAmount(quantity);

        Part part = outcomeLog.getPart();
        part.setStock(part.getStock() + changeQuantity);
        partRepo.save(part);

        return new OutcomeLogDto(outcomeLog);
    }
}
