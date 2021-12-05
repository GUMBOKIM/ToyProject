package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutgoingSubmitDto {
    private String companyCode;
    private String date;
    private String plantCode;
    private String time;
    private int orderSeq;
    List<OutgoingPartDto> partList;
}
