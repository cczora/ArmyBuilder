package com.cczora.armybuilder.models.entity;

import com.cczora.armybuilder.models.Field;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "detachment_fields")
@Entity
@Getter
public class DetachmentField extends Field implements Serializable {
    private static final long serialVersionUID = 6261441060057232854L;
}