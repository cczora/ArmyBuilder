package com.cczora.armybuilder.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ArmyPatchRequestDTO {
    private UUID armyId;
    private List<KeyValuePair> updates;
}
