package com.union.placeorderAutomation.dto.task.part.status;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartLogDto {

    private String partBwCode;
    private List<PartLogDetail> incomeLog = new ArrayList<>();
    private List<PartLogDetail> modifyLog = new ArrayList<>();
    private int defectiveLog;
    private List<PartLogDetail> outcomeLog1 = new ArrayList<>();
    private List<PartLogDetail> outcomeLog2 = new ArrayList<>();

    public PartLogDto(String bwCode) {
        this.partBwCode = bwCode;
    }
}
