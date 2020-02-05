package com.cczora.armybuilder.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter
@Setter
public class Detachment implements Serializable {

    @Id
    @Column(name = "detachment_id")
    private UUID detachmentId;

    @Column(name = "army_id", nullable = false)
    private UUID armyId;

    @Column(name = "detachment_type_id", nullable = false)
    private UUID detachmentTypeId;

    @ManyToOne
    @JoinColumn(name = "detachment_type_id", nullable = false)
    private DetachmentType detachmentType;

    @Column(name = "faction_type_id", nullable = false)
    private UUID factionTypeId;

    @ManyToOne
    @JoinColumn(name = "faction_type_id", nullable = false)
    private FactionType faction;

    @Column(nullable = false)
    private String name;

    @Column
    private String notes;

    @OneToMany
    @JoinTable(name = "unit")
    private List<Unit> units = new ArrayList<>();

    public void sortUnits(Detachment detachment) {
                detachment.units = detachment.units.stream().
                        sorted()
                        .collect(Collectors.toList());
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
