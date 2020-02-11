package com.cczora.armybuilder.models.dto;

import com.cczora.armybuilder.models.KeyValuePair;
import lombok.*;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchRequestDTO {
    List<KeyValuePair> updates;
}
