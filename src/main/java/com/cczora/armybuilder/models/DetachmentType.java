package com.cczora.armybuilder.models;

import java.util.Objects;
import java.util.UUID;

public class DetachmentType {
    private UUID detachmentTypeId;
    
    private String name;
    
    private int commandPoints;
    
    public DetachmentType() {
        
    }

    public DetachmentType(UUID detachmentTypeId, String name, int commandPoints) {
        this.detachmentTypeId = detachmentTypeId;
        this.name = name;
        this.commandPoints = commandPoints;
    }

    public UUID getDetachmentTypeId() {
        return detachmentTypeId;
    }

    public void setDetachmentTypeId(UUID detachmentTypeId) {
        this.detachmentTypeId = detachmentTypeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetachmentType that = (DetachmentType) o;
        return commandPoints == that.commandPoints &&
                Objects.equals(detachmentTypeId, that.detachmentTypeId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detachmentTypeId, name, commandPoints);
    }
}
