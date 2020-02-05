package com.cczora.armybuilder.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Army implements Serializable {

    @Id
    private UUID armyId;

    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name too long.")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "faction_type_id", nullable = false)
    @NotNull(message = "Faction Type required.")
    private FactionType faction;

    @Column(name = "command_points")
    private int commandPoints;

    @NotNull(message = "Size class required.")
    @Column(name = "size", nullable = false)
    private String sizeClass;

    @OneToMany
    @JoinTable(name = "detachment")
    List<Detachment> detachments;

    @Size(max = 255, message = "You have exceeded the character limit for notes.")
    @Column
    private String notes;
}
