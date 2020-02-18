package com.cczora.armybuilder.models.entity;

import com.cczora.armybuilder.models.Field;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "unit_fields")
@Entity
@Getter
public class UnitField extends Field implements Serializable {
    private static final long serialVersionUID = 847445084590628222L;
}
