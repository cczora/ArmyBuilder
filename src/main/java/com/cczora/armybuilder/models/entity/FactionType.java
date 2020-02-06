package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "faction_type")
public class FactionType {

    @Id
    @Column(name = "faction_type_id")
    private UUID factionTypeId;

    @Column(nullable = false)
    private String name;
}
