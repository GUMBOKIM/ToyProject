package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class InquireResultDto {

    private String bomBwCode;
    private String workCenter;
    private int totalQTY;
    private int deliveredQTY;
    private int remainQTY;
    private String seq;
    private List<InquirePartDto> parts = new ArrayList<>();
}
