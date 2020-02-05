package com.cczora.armybuilder.models;

import java.util.Objects;
import java.util.UUID;

public class Unit {
    private UUID unitId;

    private UUID unitTypeId;

    private UnitType unitType;

    private String name;

    private String notes;

    private UUID detachmentId;

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public UUID getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(UUID unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
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

    public UUID getDetachmentId() {
        return detachmentId;
    }

    public void setDetachmentId(UUID detachmentId) {
        this.detachmentId = detachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return unitId == unit.unitId &&
                Objects.equals(unitTypeId, unit.unitTypeId) &&
                Objects.equals(unitType, unit.unitType) &&
                Objects.equals(name, unit.name) &&
                Objects.equals(notes, unit.notes) &&
                Objects.equals(detachmentId, unit.detachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId, unitTypeId, unitType, name, notes, detachmentId);
    }
}
