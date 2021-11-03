package com.union.placeorderAutomation.dto.task.part;

import lombok.Data;

import java.util.List;

@Data
public class SubmitDto {

    String companyCode;
    String date;
    String time;
    List<SubmitDto> submitPartList;
}
