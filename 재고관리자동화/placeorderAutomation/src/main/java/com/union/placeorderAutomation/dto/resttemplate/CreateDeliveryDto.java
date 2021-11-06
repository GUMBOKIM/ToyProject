package com.union.placeorderAutomation.dto.resttemplate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateDeliveryDto {
    private String bwCode;
    private String partName;
    private String poCode;
    private String lot;
    private int boxQuantity;
    private int loadAmount;
    private String location;
    private String standardYn;
}
