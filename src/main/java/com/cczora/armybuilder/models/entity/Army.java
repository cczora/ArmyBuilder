package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
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
    private UUID id;

    @Column
    private String name;

    @Column
    private String username;

    @Transient
    private FactionType faction;

    @Column(name = "faction_type_id")
    private UUID factionTypeId;

    @Column(name = "command_points")
    @Builder.Default
    private int commandPoints = 3;

    @Column(name = "size")
    private String sizeClass;

    @Column
    private String notes;

    @Transient
    private List<Detachment> detachmentList;

}
