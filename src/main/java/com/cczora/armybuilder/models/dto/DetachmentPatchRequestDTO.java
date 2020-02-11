package com.cczora.armybuilder.models.dto;

import com.cczora.armybuilder.models.KeyValuePair;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetachmentPatchRequestDTO extends PatchRequestDTO {
    private UUID detachmentId;

    @Builder
    public DetachmentPatchRequestDTO(List<KeyValuePair> updates, UUID detachmentId) {
        super(updates);
        this.detachmentId = detachmentId;
    }
}
