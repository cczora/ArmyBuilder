package com.cczora.armybuilder.models.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

//class for army details without detailed detachment/unit info
@Data
@Builder
public class ArmyDTO {

    private UUID armyId;

    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name too long.")
    private String name;

    @NotNull(message = "Faction required.")
    private String factionName;

    @Builder.Default
    private int commandPoints = 3;

    @NotNull(message = "Size class required.")
    private String sizeClass;

    @Size(max = 255, message = "You have exceeded the character limit for notes.")
    private String notes;

}
