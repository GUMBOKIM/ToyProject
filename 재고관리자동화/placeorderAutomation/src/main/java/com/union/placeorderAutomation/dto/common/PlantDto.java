package com.union.placeorderAutomation.dto.common;

import com.union.placeorderAutomation.entity.Plant;
import lombok.Data;

@Data
public class PlantDto {

    private String plantCode;
    private String plantName;

    public PlantDto(Plant plant) {
        this.plantCode = plant.getPlantCode();
        this.plantName = plant.getPlantName();
    }
}
