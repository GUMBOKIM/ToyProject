package com.union.placeorderAutomation.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderHistoryDto {
    private String companyCode;
    private String plantCode;
    private String date;
    private int orderSeq;
    private String time;
}
