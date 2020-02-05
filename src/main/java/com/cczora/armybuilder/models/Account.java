 package com.cczora.armybuilder.models;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
 public class Account {

    @Id
     private String username;

    @NotNull
     private String password;

     private String profilePicUrl;

     private List<Army> armies = new ArrayList<>();

     public List<Army> getArmies() {
         return armies;
     }

     public void setArmies(List<Army> armies) {
         this.armies = armies;
     }

     public String getUsername() {
         return username;
     }

     public void setUsername(String username) {
         this.username = username;
     }

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     public String getProfilePicUrl() {
         return profilePicUrl;
     }

     public void setProfilePicUrl(String profilePicUrl) {
         this.profilePicUrl = profilePicUrl;
     }

     @Override
     public int hashCode() {
         int hash = 7;
         hash = 37 * hash + Objects.hashCode(this.username);
         hash = 37 * hash + Objects.hashCode(this.password);
         hash = 37 * hash + Objects.hashCode(this.profilePicUrl);
         return hash;
     }

     @Override
     public boolean equals(Object obj) {
         if (this == obj) {
             return true;
         }
         if (obj == null) {
             return false;
         }
         if (getClass() != obj.getClass()) {
             return false;
         }
         final Account other = (Account) obj;
         if (!Objects.equals(this.username, other.username)) {
             return false;
         }
         if (!Objects.equals(this.password, other.password)) {
             return false;
         }
         if (!Objects.equals(this.profilePicUrl, other.profilePicUrl)) {
             return false;
         }
         return true;
     }


 }
