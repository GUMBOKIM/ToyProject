package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartLog;
import com.union.placeorderAutomation.repository.PartLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Transactional
@RequiredArgsConstructor
@Service
public class PartLogService {

    private final PartLogRepository partLogRepo;

    public void createPartLog(Part part, String division, int amount, LocalDateTime date, String time) {
        PartLog partLog = PartLog.builder()
                .part(part)
                .build();
        partLogRepo.save(partLog);
    }
}
