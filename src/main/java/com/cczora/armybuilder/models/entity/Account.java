 package com.cczora.armybuilder.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

 @Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 @Builder
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
