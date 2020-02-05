package com.cczora.armybuilder.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Army implements Serializable {

    @Id
    private UUID armyId;

    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name too long.")
    private String name;

    private String username;

    @NotNull(message = "Faction required.")
    private UUID factionTypeId;

    private FactionType faction;

    private int commandPoints;

    @NotNull(message = "Size class required.")
    private String sizeClass;

    List<Detachment> detachments = new ArrayList<>();

    @Size(max = 255, message = "You have exceeded the character limit for notes.")
    private String notes;

    public UUID getFactionTypeId() {
        return factionTypeId;
    }

    public void setFactionTypeId(UUID factionTypeId) {
        this.factionTypeId = factionTypeId;
    }

    public FactionType getFaction() {
        return faction;
    }

    public void setFaction(FactionType faction) {
        this.faction = faction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getArmyId() {
        return armyId;
    }

    public void setArmyId(UUID armyId) {
        this.armyId = armyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommandPoints() {
        return commandPoints;
    }

    public void setCommandPoints(int commandPoints) {
        this.commandPoints = commandPoints;
    }

    public String getSizeClass() {
        return sizeClass;
    }

    public void setSizeClass(String sizeClass) {
        this.sizeClass = sizeClass;
    }

    public List<Detachment> getDetachments() {
        return detachments;
    }

    public void setDetachments(List<Detachment> detachments) {
        this.detachments = detachments;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
