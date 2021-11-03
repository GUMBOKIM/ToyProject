package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.task.PartStatusDto;
import com.union.placeorderAutomation.repository.PartLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PartStatusService {

    private final PartLogRepository PartLogRepository;

    public List<PartStatusDto> createPartStatus(String companyCode, String ym){
        List<PartStatusDto> result = new ArrayList<>();

        int year = Integer.parseInt(ym.substring(0,4));
        int month = Integer.parseInt(ym.substring(4));
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        List<Object[]> partStatus = PartLogRepository.findPartStatus(companyCode, startDate.toString(), endDate.toString());
        partStatus.forEach( object -> {
            result.add(new PartStatusDto(object));
        });
        return result;
    }
}
