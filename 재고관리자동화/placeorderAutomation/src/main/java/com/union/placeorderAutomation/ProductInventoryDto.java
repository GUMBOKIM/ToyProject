package com.union.placeorderAutomation;

import lombok.Data;

@Data
public class ProductInventoryDto {

    private int no;
    private String plant;
    private String whNo;
    private String storLoc;
    private String partNo;
    private int stockQTY;
    private String uom;
    private String rop;
    private int lotMin;
    private int lotMax;
}
