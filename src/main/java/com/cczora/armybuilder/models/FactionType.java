package com.cczora.armybuilder.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name = "faction_type")
public class FactionType implements Serializable {

    @Id
    private UUID factionTypeId;

    @Column(nullable = false)
    private String name;
}
