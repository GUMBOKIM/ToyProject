package com.union.placeorderAutomation.dto.Task;

import lombok.Data;

@Data
public class StockRequestDto {
    private String partBwCode;
    private String lot;
    private int stock;
}
