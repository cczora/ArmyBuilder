package com.cczora.armybuilder.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "unit_type")
@NoArgsConstructor
public class UnitType implements Serializable {

    private static final long serialVersionUID = -8952298989988435673L;

    @Id
    private UUID unit_type_id;

    @Column(nullable = false)
    private String name;

    @Column(name = "power_points", nullable = false)
    private int powerPoints;
}
