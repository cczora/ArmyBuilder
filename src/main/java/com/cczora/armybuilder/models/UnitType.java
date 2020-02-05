package com.cczora.armybuilder.models;

import java.util.Objects;
import java.util.UUID;

public class UnitType {

    private UUID unitTypeId;

    private String name;

    private int powerPoints;

    public UnitType() {

    }

    public UnitType(UUID unitTypeId, String name, int powerPoints) {
        this.unitTypeId = unitTypeId;
        this.name = name;
        this.powerPoints = powerPoints;

    }

    public UUID getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(UUID unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPowerPoints() {
        return powerPoints;
    }

    public void setPowerPoints(int powerPoints) {
        this.powerPoints = powerPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitType unitType = (UnitType) o;
        return powerPoints == unitType.powerPoints &&
                Objects.equals(unitTypeId, unitType.unitTypeId) &&
                Objects.equals(name, unitType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitTypeId, name, powerPoints);
    }
}
