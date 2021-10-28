package com.union.placeorderAutomation.dto.Task;

import lombok.Data;

@Data
public class PartStockDto {
    private Long partId;
    private String bwCode;
    private String spCode;
    private String poCode;
    private int stock;
}
