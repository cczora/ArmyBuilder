package com.cczora.armybuilder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PWEnc {
    public static void main(String[] args) {
        String clearTxtPw = "password2";
        // BCrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPw = encoder.encode(clearTxtPw);
        System.out.println(hashedPw);
    }
}
