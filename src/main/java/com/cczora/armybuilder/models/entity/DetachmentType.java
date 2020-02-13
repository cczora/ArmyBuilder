package com.cczora.armybuilder.models.entity;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detachment_type")
public class DetachmentType implements Serializable {

    private static final long serialVersionUID = -6806945322796260838L;
    @Id
    @Column(name = "detachment_type_id")
    private UUID detachmentTypeId;

    @Column(nullable = false)
    private String name;

    @Column(name = "command_pts", nullable = false)
    private int commandPoints;
}
