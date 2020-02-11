package com.cczora.armybuilder.models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class Field {
    @Id
    private String name;
    @Column
    private boolean patchEnabled;

    public static boolean isPatchEnabed(Field f) {
        return f.patchEnabled;
    }

    public static String getNam(Field f) {
        return f.name;
    }
}
