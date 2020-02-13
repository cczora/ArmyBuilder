package com.cczora.armybuilder.models.dto;

import com.cczora.armybuilder.models.KeyValuePair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchRequestDTO {
    List<KeyValuePair> updates;
}
