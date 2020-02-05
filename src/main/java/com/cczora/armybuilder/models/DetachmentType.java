package com.cczora.armybuilder.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detachment_type")
public class DetachmentType {
    @Id
    @Column(name = "detachment_type_id")
    private UUID detachmentTypeId;

    @Column(nullable = false)
    private String name;

    @Column(name = "command_pts", nullable = false)
    private int commandPoints;
}
