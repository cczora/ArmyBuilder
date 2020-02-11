package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "army", schema = "public")
public class Army implements Serializable {

    private static final long serialVersionUID = 2242079497393722021L;

    @Id
    private UUID army_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "faction_type_id", nullable = false)
    private FactionType faction;

    @Column(name = "command_points")
    @Builder.Default
    private int commandPoints = 3;

    @Column(name = "size", nullable = false)
    private String sizeClass;

    @Column
    private String notes;

    //TODO: add create/modify date to entities
}
