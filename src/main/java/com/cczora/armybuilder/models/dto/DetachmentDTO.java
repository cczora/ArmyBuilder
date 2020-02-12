package com.cczora.armybuilder.models.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
public class DetachmentDTO {

    private UUID detachmentId;
    @NotBlank(message = "armyId is required")
    private UUID armyId;
    @NotBlank(message = "Type is required.")
    private String detachmentType;
    private String factionType;
    @NotBlank(message = "Name required.")
    private String name;
    private String notes;
}
