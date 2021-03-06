package com.cczora.armybuilder.models.dto;

import com.cczora.armybuilder.models.KeyValuePair;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArmyPatchRequestDTO extends PatchRequestDTO {
    private UUID armyId;

    @Builder
    public ArmyPatchRequestDTO(List<KeyValuePair> updates, UUID armyId) {
        super(updates);
        this.armyId = armyId;
    }
}
