package com.cczora.armybuilder.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UnitDTO {
    private UUID id;
    @Parameter(required = true, description = "The type of unit that this unit is.")
    private String unitType;
    @Parameter(required = true, description = "The detachment guid id for the unit.")
    private UUID detachmentId;
    @Parameter(description = "A unique name for this unit. ")
    private String name;
    private String notes;
}
