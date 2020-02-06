package com.cczora.armybuilder.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//class for army details without detailed detachment/unit info
@Data
public class ArmyDTO {

    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name too long.")
    private String name;

    @NotNull(message = "Faction required.")
    private String factionName;

    private int commandPoints;

    @NotNull(message = "Size class required.")
    private String sizeClass;

    @Size(max = 255, message = "You have exceeded the character limit for notes.")
    private String notes;

}
