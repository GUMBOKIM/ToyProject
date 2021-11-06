package com.union.placeorderAutomation.dto.task.outgoing;

import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InquireResultDto {

    private String bomBwCode;
    private String workCenter;
    private int totalQTY;
    private int deliveredQTY;
    private int remainQTY;
    private String seq;
    private List<InquirePartDto> parts;

    public InquireResultDto(ProductPlanDto planBom) {
        this.bomBwCode = planBom.getBomBwCode();
        this.workCenter = planBom.getWorkCenter();
        this.totalQTY = planBom.getTotalQTY();
        this.deliveredQTY = planBom.getDeliveredQTY();
        this.remainQTY = planBom.getRemainQTY();
        this.seq = planBom.getSeq();
        this.parts = new ArrayList<>();
    }

    public void addParts(InquirePartDto inquirePartDto){
        this.parts.add(inquirePartDto);
    }
}
