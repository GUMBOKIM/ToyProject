package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OutgoingSubmitDto {
    private String companyCode;
    private String date;
    private String plantCode;
    private String time;
    private int orderSeq;
    List<OutgoingPartDto> partList;
}
