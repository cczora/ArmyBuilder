package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detachment implements Serializable {

    @Id
    @Column(name = "detachment_id")
    private UUID detachmentId;

    @ManyToOne
    @JoinColumn(name = "army_id")
    private Army army;

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

    @OneToMany
    @JoinTable(name = "unit")
    private List<Unit> units;

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
        return  Objects.equals(army, that.army) &&
                Objects.equals(detachmentId, that.detachmentId) &&
                Objects.equals(detachmentType, that.detachmentType) &&
                Objects.equals(faction, that.faction) &&
                Objects.equals(name, that.name) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detachmentId, detachmentType, faction, name, notes, units);
    }
}
