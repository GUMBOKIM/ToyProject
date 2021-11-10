package com.union.placeorderAutomation.dto.task.outgoing;

import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import lombok.Data;

@Data
public class OutgoingManualDto {
    private String companyCode;
    private String plantCode;
    private String date;
    private String time;
    private CreateDeliveryDto[] deliveryList;
}
