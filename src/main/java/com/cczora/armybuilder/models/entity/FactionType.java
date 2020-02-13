package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faction_type")
@Builder
public class FactionType implements Serializable {

    private static final long serialVersionUID = 8643387175043144289L;

    @Id
    @Column(name = "faction_type_id")
    private UUID factionTypeId;

    @Column(nullable = false)
    private String name;
}
