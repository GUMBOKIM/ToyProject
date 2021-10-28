package com.union.placeorderAutomation.dto.Task;

import lombok.Data;

@Data
public class StockModifyBWDto {
    private String bwCode;
    private String lot;
    private int stock;
}
