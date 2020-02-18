package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
public class Detachment implements Serializable {

    private static final long serialVersionUID = -3798228120704087633L;

    @Id
    private UUID id;

    @Column(name = "army_id")
    private UUID armyId;

    @ManyToOne
    @JoinColumn(name = "detachment_type_id", nullable = false)
    private DetachmentType detachmentType;

    @ManyToOne
    @JoinColumn(name = "faction_type_id", nullable = false)
    private FactionType faction;

    @Column(nullable = false)
    private String name;

    @Column
    private String notes;

    @Transient
    private List<Unit> units;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detachment that = (Detachment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(detachmentType, that.detachmentType) &&
                Objects.equals(faction, that.faction) &&
                Objects.equals(name, that.name) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, detachmentType, faction, name, notes, units);
    }

}
