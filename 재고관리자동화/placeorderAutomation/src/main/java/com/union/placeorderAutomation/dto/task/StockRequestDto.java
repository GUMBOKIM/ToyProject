package com.union.placeorderAutomation.dto.task;

import lombok.Data;

@Data
public class StockRequestDto {
    private String partBwCode;
    private String lot;
    private int stock;
}
