package com.union.placeorderAutomation.dto.task.part.status;

import com.union.placeorderAutomation.entity.OutcomeLog;
import lombok.Data;

@Data
public class PartOutgoingLogDto {
    private String date;
    private String companyName;
    private String plantName;
    private int orderSeq;
    private String bwCode;
    private String spCode;
    private String lot;
    private int quantity;

    public PartOutgoingLogDto(OutcomeLog outcomeLog) {
        this.companyName = outcomeLog.getCompany().getCompanyName();
        this.plantName = outcomeLog.getPlant().getPlantName();
        this.date = outcomeLog.getDate();
        this.orderSeq = outcomeLog.getOrderSeq();
        this.bwCode = outcomeLog.getPart().getBwCode();
        this.spCode = outcomeLog.getPart().getSpCode();
        this.lot = outcomeLog.getLot();
        this.quantity = outcomeLog.getAmount();
    }
}
