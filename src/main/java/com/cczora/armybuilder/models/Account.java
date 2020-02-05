 package com.cczora.armybuilder.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
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
     List<Army> armies = new ArrayList<>();

 }
