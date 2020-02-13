package com.cczora.armybuilder.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit implements Serializable {

    private static final long serialVersionUID = 7604476564781331578L;

    @Id
    private UUID unit_Id;

    @ManyToOne
    @JoinColumn(name = "unit_type_id")
    @NotNull
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
        return unit_Id == unit.unit_Id &&
                Objects.equals(unitType, unit.unitType) &&
                Objects.equals(name, unit.name) &&
                Objects.equals(notes, unit.notes) &&
                Objects.equals(detachmentId, unit.detachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit_Id, unitType, name, notes, detachmentId);
    }
}
