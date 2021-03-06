package com.cczora.armybuilder.models.entity;

import com.cczora.armybuilder.models.Field;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "army_fields")
@Entity
@Getter
public class ArmyField extends Field implements Serializable {
    private static final long serialVersionUID = 7032360875824260694L;
}
