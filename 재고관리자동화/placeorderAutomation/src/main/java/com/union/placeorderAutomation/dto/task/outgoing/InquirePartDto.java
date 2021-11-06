package com.union.placeorderAutomation.dto.task.outgoing;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InquirePartDto {
    private String partBwCode;
    private int usage;
    private int loadAmount;
    private int stockQTY;
}
