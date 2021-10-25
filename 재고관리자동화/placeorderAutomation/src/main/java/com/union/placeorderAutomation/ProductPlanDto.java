package com.union.placeorderAutomation;

import lombok.Data;

@Data
public class ProductPlanDto {

    private int no;
    private String plant;
    private String workCenter;
    private String partNo;
    private int totalQTY;
    private int deliveredQTY;
    private int remainQTY;
    private String seq;
    private int rTime;
    private int tTime;

}
