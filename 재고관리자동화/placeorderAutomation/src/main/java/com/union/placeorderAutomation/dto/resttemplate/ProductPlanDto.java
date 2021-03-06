package com.union.placeorderAutomation.dto.resttemplate;

import lombok.Data;
import java.util.List;

@Data
public class ProductPlanDto {

    private String plant;
    private String workCenter;
    private String bomBwCode;
    private int totalQTY;
    private int deliveredQTY;
    private int remainQTY;
    private String seq;
    private int rTime;
    private int tTime;

    private List<PartInventoryDto> partInventory;

}
