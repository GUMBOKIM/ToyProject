package com.union.placeorderAutomation.dto.task.outgoing;

import com.union.placeorderAutomation.entity.OutcomeLog;
import lombok.Data;

@Data
public class OutcomeLogDto {

    private Long outcomeLogId;
    private String bwCode;
    private int quantity;

    public OutcomeLogDto(OutcomeLog outcomeLog) {
        this.outcomeLogId = outcomeLog.getOutcomeId();
        this.bwCode = outcomeLog.getPart().getBwCode();
        this.quantity = outcomeLog.getAmount();
    }
}
