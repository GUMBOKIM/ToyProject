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

    private final PartLogRepository partLogRepo;
    private final DefectiveLogRepository defectiveLogRepo;
    private final IncomeLogRepository incomeLogRepo;
    private final ModifyLogRepository modifyLogRepo;
    private final OutcomeLogRepository outcomeLogRepo;

    public void createDefectiveLog(CreateLogDto logDto) {
        PartLog partLog = createPartLog(logDto.getPartBwCode(), logDto.getDate());
        DefectiveLog defectiveLog = DefectiveLog.builder()
                .partLog(partLog)
                .lot(logDto.getLot())
                .amount(logDto.getAmount())
                .build();
        defectiveLogRepo.save(defectiveLog);
    }

    public void createIncomeLog(CreateLogDto logDto) {
        PartLog partLog = createPartLog(logDto.getPartBwCode(), logDto.getDate());
        IncomeLog incomeLog = IncomeLog.builder()
                .partLog(partLog)
                .orderSeq(logDto.getOrderSeq())
                .lot(logDto.getLot())
                .amount(logDto.getAmount())
                .build();
        incomeLogRepo.save(incomeLog);
    }

    public void createModifyLogs(CreateLogDto logDto) {
        PartLog partLog = createPartLog(logDto.getPartBwCode(), logDto.getDate());
        ModifyLog modifyLog = ModifyLog.builder()
                .partLog(partLog)
                .orderSeq(logDto.getOrderSeq())
                .lot(logDto.getLot())
                .amount(logDto.getAmount())
                .build();
        modifyLogRepo.save(modifyLog);
    }


    public void createOutcomeLogs(OutgoingSubmitDto submitDto, List<CreateDeliveryDto> deliveryList) {
        for(CreateDeliveryDto deliveryDto : deliveryList){
            PartLog partLog = createPartLog(deliveryDto.getBwCode(), submitDto.getDate());
            Plant plant = Plant.builder().plantCode(submitDto.getPlantCode()).build();
            OutcomeLog outcomeLog = OutcomeLog.builder()
                    .partLog(partLog)
                    .plant(plant)
                    .orderSeq(submitDto.getOrderSeq())
                    .lot(deliveryDto.getLot())
                    .amount(deliveryDto.getQuantity())
                    .build();
            outcomeLogRepo.save(outcomeLog);
        }

    }


    public PartLog createPartLog(String partBwCode, String date) {
        Part part = Part.builder().bwCode(partBwCode).build();

        Optional<PartLog> partLog = partLogRepo.findPartLogByPartAndDate(part, date);
        if (partLog.isEmpty()) {
            PartLog newPartLog = PartLog.builder()
                    .part(part)
                    .date(date)
                    .build();
            partLogRepo.save(newPartLog);
            return newPartLog;
        }
        return partLog.get();
    }

}
