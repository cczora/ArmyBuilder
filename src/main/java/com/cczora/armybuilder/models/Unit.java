package com.cczora.armybuilder.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class Unit {

    @Id
    private UUID unitId;

    @Column(name = "unit_type_id", nullable = false)
    private UUID unitTypeId;

    @ManyToOne
    @JoinColumn(name = "unit_type_id")
    private UnitType unitType;

    @Column(nullable = false)
    private String name;

    @Column
    private String notes;

    @Column(name = "detachment_id", nullable = false)
    private UUID detachmentId;

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
