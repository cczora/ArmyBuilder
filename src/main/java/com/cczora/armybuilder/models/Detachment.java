package com.cczora.armybuilder.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Detachment {

    private UUID detachmentId;

    private UUID armyId;

    private UUID detachmentTypeId;

    private DetachmentType detachmentType;

    private UUID factionTypeId;

    private FactionType faction;

    private String name;

    private String notes;

    private List<Unit> units = new ArrayList<>();

    public UUID getDetachmentId() {
        return detachmentId;
    }

    public void setDetachmentId(UUID detachmentId) {
        this.detachmentId = detachmentId;
    }

    public UUID getArmyId() {
        return armyId;
    }

    public void setArmyId(UUID armyId) {
        this.armyId = armyId;
    }

    public UUID getDetachmentTypeId() {
        return detachmentTypeId;
    }

    public void setDetachmentTypeId(UUID detachmentTypeId) {
        this.detachmentTypeId = detachmentTypeId;
    }

    public DetachmentType getDetachmentType() {
        return detachmentType;
    }

    public void setDetachmentType(DetachmentType detachmentType) {
        this.detachmentType = detachmentType;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public void sortUnits(Detachment detachment) {
        detachment.setUnits(
                detachment.getUnits().stream().
                        sorted()
                        .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detachment that = (Detachment) o;
        return armyId == that.armyId &&
                detachmentTypeId == that.detachmentTypeId &&
                factionTypeId == that.factionTypeId &&
                Objects.equals(detachmentId, that.detachmentId) &&
                Objects.equals(detachmentType, that.detachmentType) &&
                Objects.equals(faction, that.faction) &&
                Objects.equals(name, that.name) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detachmentId, armyId, detachmentTypeId, detachmentType, factionTypeId, faction, name, notes, units);
    }
}
