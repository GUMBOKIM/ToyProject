package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.Data;

@Data
public class CancelPartStockDto {
    private String bwCode;
    private String lot;
    private int quantity;
}
