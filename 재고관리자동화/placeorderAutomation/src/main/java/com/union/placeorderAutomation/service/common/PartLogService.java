package com.union.placeorderAutomation.service.common;

import com.union.placeorderAutomation.dto.common.CreateLogDto;
import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.entity.*;
import com.union.placeorderAutomation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class PartLogService {

    private final PartRepository partRepo;
    private final DefectiveLogRepository defectiveLogRepo;
    private final IncomeLogRepository incomeLogRepo;
    private final ModifyLogRepository modifyLogRepo;
    private final OutcomeLogRepository outcomeLogRepo;

    public void createDefectiveLog(CreateLogDto logDto) {
        DefectiveLog defectiveLog = DefectiveLog.builder()
                .part(Part.builder().bwCode(logDto.getPartBwCode()).build())
                .date(logDto.getDate())
                .amount(logDto.getAmount())
                .build();
        defectiveLogRepo.save(defectiveLog);
    }

    public void createIncomeLog(CreateLogDto logDto) {
        IncomeLog incomeLog = IncomeLog.builder()
                .part(Part.builder().bwCode(logDto.getPartBwCode()).build())
                .date(logDto.getDate())
                .orderSeq(logDto.getOrderSeq())
                .amount(logDto.getAmount())
                .build();
        incomeLogRepo.save(incomeLog);
    }

    public void createModifyLogs(CreateLogDto logDto) {
        ModifyLog modifyLog = ModifyLog.builder()
                .part(Part.builder().bwCode(logDto.getPartBwCode()).build())
                .date(logDto.getDate())
                .orderSeq(logDto.getOrderSeq())
                .amount(logDto.getAmount())
                .build();
        modifyLogRepo.save(modifyLog);
    }


    public void createOutcomeLogs(OutgoingSubmitDto submitDto, List<CreateDeliveryDto> deliveryList) {
        Plant plant = Plant.builder().plantCode(submitDto.getPlantCode()).build();
        Company company = Company.builder().companyCode(submitDto.getCompanyCode()).build();
        for (CreateDeliveryDto deliveryDto : deliveryList) {

            Part part = partRepo.findByBwCode(deliveryDto.getBwCode()).get();
            part.setStock(part.getStock() - deliveryDto.getQuantity());
            partRepo.save(part);

            OutcomeLog outcomeLog = OutcomeLog.builder()
                    .part(Part.builder().bwCode(deliveryDto.getBwCode()).build())
                    .company(company)
                    .date(submitDto.getDate())
                    .plant(plant)
                    .orderSeq(submitDto.getOrderSeq())
                    .lot(deliveryDto.getLot())
                    .amount(deliveryDto.getQuantity())
                    .build();
            outcomeLogRepo.save(outcomeLog);
        }

    }

}
