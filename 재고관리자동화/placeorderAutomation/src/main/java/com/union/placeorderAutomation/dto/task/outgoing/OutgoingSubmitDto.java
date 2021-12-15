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

    public OutgoingSubmitDto(OutgoingManualDto manualDto) {
        this.companyCode = manualDto.getCompanyCode();
        this.date = manualDto.getDate();
        this.plantCode = manualDto.getPlantCode();
        this.time = manualDto.getTime();
        this.orderSeq = manualDto.getOrderSeq();
    }
}
