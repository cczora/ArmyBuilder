package com.cczora.armybuilder.models;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
