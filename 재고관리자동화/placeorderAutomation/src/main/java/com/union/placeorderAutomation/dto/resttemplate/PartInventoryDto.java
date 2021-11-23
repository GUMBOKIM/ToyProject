package com.union.placeorderAutomation.dto.resttemplate;

import lombok.Data;

@Data
public class PartInventoryDto {

    private int no;
    private String plant;
    private String whNo;
    private String storeLocation;
    private String partBwCode;
    private int stockQTY;
    private String uom;
    private int rop;
    private int lotMin;
    private int lotMax;

    private int usage = 0;
}
