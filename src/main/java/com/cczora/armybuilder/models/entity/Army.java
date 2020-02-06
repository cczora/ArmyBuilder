package com.cczora.armybuilder.models.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "army", schema = "public")
public class Army {

    @Id
    private UUID army_id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account user;

    @ManyToOne
    @JoinColumn(name = "faction_type_id", nullable = false)
    private FactionType faction;

    @Column(name = "command_points")
    @Builder.Default
    private int commandPoints = 3;

    @Column(name = "size", nullable = false)
    private String sizeClass;

    @Column
    private String notes;

    //TODO: add create/modify date to entities
}
