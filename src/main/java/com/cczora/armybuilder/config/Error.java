package com.cczora.armybuilder.config;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Error {

    private LocalDateTime timestamp;
    private String message;

}
