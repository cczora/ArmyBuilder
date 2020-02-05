package com.cczora.armybuilder.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "faction_type")
public class FactionType implements Serializable {

    @Id
    private UUID factionTypeId;

    @Column(nullable = false)
    private String name;
}
