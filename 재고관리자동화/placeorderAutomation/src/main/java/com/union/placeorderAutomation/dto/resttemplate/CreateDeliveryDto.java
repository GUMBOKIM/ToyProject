package com.union.placeorderAutomation.dto.resttemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateDeliveryDto {
    private String bwCode;
    private String inventoryBwCode;
    private String partName;
    private String poCode;
    private String lot;
    private int quantity;
    private int loadAmount;
    private String location;
}
