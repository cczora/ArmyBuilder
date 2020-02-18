package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Unit implements Serializable {

    private static final long serialVersionUID = 7604476564781331578L;

    @Id
    private UUID id;

    @Transient
    private UnitType unitType;

    @Column(name = "unit_type_id")
    private UUID unitTypeId;

    @Column(name = "detachment_id")
    private UUID detachmentId;

    @Column(nullable = false)
    private String name;

    @Column
    private String notes;
}
