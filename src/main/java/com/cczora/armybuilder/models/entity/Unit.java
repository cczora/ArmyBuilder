package com.cczora.armybuilder.models.entity;

import lombok.*;

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
    private UUID unitId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Detachment detachment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return unitId == unit.unitId &&
                Objects.equals(unitType, unit.unitType) &&
                Objects.equals(name, unit.name) &&
                Objects.equals(notes, unit.notes) &&
                Objects.equals(detachmentId, unit.detachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId, unitType, name, notes, detachmentId);
    }
}
