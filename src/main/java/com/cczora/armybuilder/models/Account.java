 package com.cczora.armybuilder.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 public class Account {

    @Id
     private String username;

    @NotNull
    @Column(nullable = false)
     private String password;

    @Column(name = "profile_pic_url")
     private String profilePicUrl;

    @OneToMany
    @JoinTable(name = "army")
     List<Army> armies;

 }
