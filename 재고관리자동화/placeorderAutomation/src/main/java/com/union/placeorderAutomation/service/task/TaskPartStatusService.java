package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.part.status.PartLogDto;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartLog;
import com.union.placeorderAutomation.entity.Plant;
import com.union.placeorderAutomation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class TaskPartStatusService {

    private final PartLogRepository partLogRepo;
    private final PartRepository partRepo;
    private final PlantRepository plantRepo;

    private final IncomeLogRepository incomeLogRepo;
    private final ModifyLogRepository modifyLogRepo;
    private final DefectiveLogRepository defectiveLogRepo;
    private final OutcomeLogRepository outcomeLogRepo;

    @Transactional(readOnly = true)
    public List<PartLogDto> createPartStatus(String companyCode, String date){
        List<PartLogDto> result = new ArrayList<>();

        List<Part> partList = partRepo.findPartByCompany(companyCode);
        for(Part part : partList){
            PartLogDto partLogDto = new PartLogDto(part.getBwCode());
            Optional<PartLog> partLogOpt = partLogRepo.findPartLogByPartAndDate(part, date);
            if(partLogOpt.isPresent()){
                PartLog partLog = partLogOpt.get();
                //입고
                partLogDto.setIncomeLog(incomeLogRepo.sumQuantityByPartLog(partLog));
                //보정
                partLogDto.setModifyLog(modifyLogRepo.sumQuantityByPartLog(partLog));
                //불량
                partLogDto.setDefectiveLog(defectiveLogRepo.sumQuantityByPartLog(partLog));
                //출고
                //1공장
                Plant plant1 = plantRepo.getById("5300");
                Plant plant2 = plantRepo.getById("5330");
                partLogDto.setOutcomeLog1(outcomeLogRepo.sumQuantityByPartLog(partLog, plant1));
                partLogDto.setOutcomeLog2(outcomeLogRepo.sumQuantityByPartLog(partLog, plant2));
            }
            result.add(partLogDto);
        }
        return result;
    }
}
