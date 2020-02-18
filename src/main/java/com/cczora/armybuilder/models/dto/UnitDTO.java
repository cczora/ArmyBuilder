package com.cczora.armybuilder.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UnitDTO {
    private UUID id;
    private String unitType;
    private UUID detachmentId;
    private String name;
    private String notes;
}
