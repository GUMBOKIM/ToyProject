package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.Data;

import java.util.List;

@Data
public class OutgoingManualDto {
    private String companyCode;
    private String date;
    private String plantCode;
    private String time;
    private int orderSeq;
    private List<OutgoingManualPartDto> excelPartList;
}
