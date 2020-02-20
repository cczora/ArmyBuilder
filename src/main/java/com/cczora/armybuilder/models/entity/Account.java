package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable {

    private static final long serialVersionUID = -6407324986805263828L;

    @Id
    private String username;

    @NotNull
    @Column
    private String password;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @Transient
    private List<Army> armies = new ArrayList<>();

}
