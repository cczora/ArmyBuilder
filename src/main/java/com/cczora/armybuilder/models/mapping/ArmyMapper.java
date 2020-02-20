package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.data.FactionTypeRepository;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.FactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArmyMapper {

    private FactionTypeRepository typeRepo;

    @Autowired
    public ArmyMapper(FactionTypeRepository typeRepo) {
        this.typeRepo = typeRepo;
    }

    public Army armyDTOToArmy(ArmyDTO dto, String username) {
        FactionType faction = typeRepo.findFactionTypeByName(dto.getFactionName());
        return Army.builder()
                .username(username)
                .commandPoints(dto.getCommandPoints())
                .notes(dto.getNotes())
                .faction(faction)
                .factionTypeId(faction.getFactionTypeId())
                .sizeClass(dto.getSizeClass())
                .name(dto.getName())
                .id(dto.getArmyId())
                .build();
    }

    public ArmyDTO armyToArmyDTO(Army army) {
        return ArmyDTO.builder()
                .armyId(army.getId())
                .notes(army.getNotes())
                .factionName(typeRepo.findById(army.getFactionTypeId()).get().getName())
                .sizeClass(army.getSizeClass())
                .name(army.getName())
                .commandPoints(army.getCommandPoints())
                .build();
    }
}
