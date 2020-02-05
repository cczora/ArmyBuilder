package com.cczora.armybuilder.models;

import java.util.Objects;
import java.util.UUID;

public class FactionType {
    
    private UUID factionTypeId;
    
    private String name;
    
    public FactionType() {
    }

    public FactionType(UUID factionTypeId, String name) {
        this.factionTypeId = factionTypeId;
        this.name = name;
    }

    public UUID getFactionTypeId() {
        return factionTypeId;
    }

    public String getName() {
        return name;
    }

    public void setFactionTypeId(UUID factionTypeId) {
        this.factionTypeId = factionTypeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactionType that = (FactionType) o;
        return Objects.equals(factionTypeId, that.factionTypeId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factionTypeId, name);
    }
}
