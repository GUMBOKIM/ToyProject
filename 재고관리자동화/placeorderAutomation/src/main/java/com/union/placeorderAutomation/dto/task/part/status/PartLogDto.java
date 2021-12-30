package com.union.placeorderAutomation.dto.task.part.status;

import com.union.placeorderAutomation.entity.Part;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartLogDto {

    private String partBwCode;
    private String spCode;
    private int stock;
    private int loadAmount;
    private List<PartLogDetail> incomeLog = new ArrayList<>();
    private List<PartLogDetail> modifyLog = new ArrayList<>();
    private int defectiveLog;
    private List<PartLogDetail> outcomeLog1 = new ArrayList<>();
    private List<PartLogDetail> outcomeLog2 = new ArrayList<>();

    public PartLogDto(Part part) {
        this.partBwCode = part.getBwCode();
        this.spCode = part.getSpCode();
        this.stock = part.getStock();
        this.loadAmount = part.getLoadAmount();
    }
}
