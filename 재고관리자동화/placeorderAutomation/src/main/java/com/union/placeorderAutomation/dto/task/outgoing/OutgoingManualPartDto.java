package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.Data;

@Data
public class OutgoingManualPartDto {
    private String bwCode;
    private String partName;
    private String poCode;
    private String lot;
    private String boxQuantity;
    private int quantity;
    private int loadAmount;
    private String location;
    private String desc;
}
