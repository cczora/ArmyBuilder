package com.cczora.armybuilder.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyValuePair {
    String key;
    String value;
}
