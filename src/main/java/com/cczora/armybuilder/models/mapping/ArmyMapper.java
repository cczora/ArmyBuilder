package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.data.FactionTypeRepository;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.entity.Army;
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
        return Army.builder()
                .commandPoints(dto.getCommandPoints())
                .notes(dto.getNotes())
                .faction(typeRepo.findFactionTypeByName(dto.getFactionName()))
                .sizeClass(dto.getSizeClass())
                .username(username)
                .name(dto.getName())
                .army_id(dto.getArmyId())
                .build();
    }

    public ArmyDTO armyToArmyDTO(Army army) {
        return ArmyDTO.builder()
                .armyId(army.getArmy_id())
                .notes(army.getNotes())
                .factionName(army.getFaction().getName())
                .sizeClass(army.getSizeClass())
                .name(army.getName())
                .commandPoints(army.getCommandPoints())
                .build();
    }
}
