package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.entity.Army;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArmyMapper {
    ArmyMapper MAPPER = Mappers.getMapper(ArmyMapper.class);

    Army armyDTOToArmy(ArmyDTO army);
    ArmyDTO armyToArmyDTO(Army army);
}
