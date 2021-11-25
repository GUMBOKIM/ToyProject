package com.union.placeorderAutomation.dto.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderHistoryDto {
    private String companyCode;
    private String plantCode;
    private String date;
    private int orderSeq;
    private String time;
}
