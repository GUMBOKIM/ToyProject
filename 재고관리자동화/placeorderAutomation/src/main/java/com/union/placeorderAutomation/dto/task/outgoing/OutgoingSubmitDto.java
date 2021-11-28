package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutgoingSubmitDto {
    private String companyCode;
    private String date;
    private String plantCode;
    private String time;
    private int orderSeq;
    List<OutgoingPartDto> partList;
}
