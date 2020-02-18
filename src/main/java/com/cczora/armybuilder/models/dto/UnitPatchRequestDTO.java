package com.cczora.armybuilder.models.dto;

import com.cczora.armybuilder.models.KeyValuePair;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnitPatchRequestDTO extends PatchRequestDTO {
    private UUID unitId;

    @Builder
    public UnitPatchRequestDTO(List<KeyValuePair> updates, UUID unitId) {
        super(updates);
        this.unitId = unitId;
    }
}
