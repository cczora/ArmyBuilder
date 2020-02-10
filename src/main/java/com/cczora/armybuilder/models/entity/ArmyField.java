package com.cczora.armybuilder.models.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "army_fields")
@Entity
@Getter
public class ArmyField implements Serializable {
    private static final long serialVersionUID = 7032360875824260694L;

    @Id
    private String name;

    private boolean patchEnabled;

}
