package com.cczora.armybuilder.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
public class Army implements Serializable {

    @Id
    private UUID armyId;

    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name too long.")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @NotNull(message = "Faction required.")
    @Column(name = "faction_type_id", nullable = false)
    private UUID factionTypeId;

    @ManyToOne
    @JoinColumn(name = "faction_type_id", nullable = false)
    private FactionType faction;

    @Column(name = "command_points")
    private int commandPoints;

    @NotNull(message = "Size class required.")
    @Column(name = "size", nullable = false)
    private String sizeClass;

    @OneToMany
    @JoinTable(name = "detachment")
    List<Detachment> detachments = new ArrayList<>();

    @Size(max = 255, message = "You have exceeded the character limit for notes.")
    @Column
    private String notes;
}
