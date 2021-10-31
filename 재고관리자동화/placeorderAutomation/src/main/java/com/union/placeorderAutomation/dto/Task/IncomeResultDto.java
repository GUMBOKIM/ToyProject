package com.union.placeorderAutomation.dto.Task;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IncomeResultDto {

    private List<PartStockDetailDto> success = new ArrayList<>();
    private List<String> fail = new ArrayList<>();
}
