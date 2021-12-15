package com.union.placeorderAutomation.dto.resttemplate;

import com.union.placeorderAutomation.entity.Part;
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
    private String poCode1;
    private String poCode2;
    private String lot;
    private int quantity;
    private int loadAmount;
    private String location1;
    private String location2;

    public CreateDeliveryDto(Part part) {
        this.bwCode = part.getBwCode();
        this.inventoryBwCode = part.getInventoryBwCode();
        this.partName = part.getPartName();
        this.poCode1 = part.getPoCode1();
        this.poCode2 = part.getPoCode2();
        this.location1 = part.getLocation1();
        this.location2 = part.getLocation2();
    }
}
