package com.cczora.armybuilder.models.dto;

import io.swagger.v3.oas.annotations.Parameter;
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

    @Parameter(description = "Unique guid id for the army. Will be randomly generated if adding a new army.")
    private UUID armyId;

    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name too long.")
    @Parameter(required = true, description = "Name for the army")
    private String name;

    @NotNull(message = "Faction required.")
    @Parameter(description = "Faction name for the army", required = true)
    private String factionName;

    @Builder.Default
    @Parameter(description = "CP for the army (default 3 if left blank)")
    private int commandPoints = 3;

    @NotNull(message = "Size class required.")
    @Parameter(required = true, description = "Size class of the army; accepted values are small (2 detachments), medium (3 detachments), and large (4 detachments)")
    private String sizeClass;

    @Size(max = 255, message = "You have exceeded the character limit for notes.")
    @Parameter(description = "User-added notes")
    private String notes;

}
