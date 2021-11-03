package com.union.placeorderAutomation.dto.manage;

import lombok.Data;

@Data
public class BomCreateDto {

    private String bomBwCode;
    private String partBwCode;
    private int amount;
}
