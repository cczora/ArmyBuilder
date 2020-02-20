package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.data.UnitTypeRepository;
import com.cczora.armybuilder.models.dto.UnitDTO;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.entity.UnitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    private UnitTypeRepository typeRepo;

    @Autowired
    public UnitMapper(UnitTypeRepository typeRepo) {
        this.typeRepo = typeRepo;
    }

    public Unit dtoToEntity(UnitDTO dto) {
        UnitType type = typeRepo.findByName(dto.getUnitType()).get();
        return Unit.builder()
                .detachmentId(dto.getDetachmentId())
                .id(dto.getId())
                .name(dto.getName())
                .unitType(type)
                .unitTypeId(type.getUnit_type_id())
                .notes(dto.getNotes())
                .build();
    }

    public UnitDTO entityToDTO(Unit unit) {
        if(unit.getUnitType() == null) { //when it comes fresh from db this will not be set
            unit.setUnitType(typeRepo.findById(unit.getUnitTypeId()).get());
        }
        return UnitDTO.builder()
                .name(unit.getName())
                .unitType(unit.getUnitType().getName())
                .id(unit.getId())
                .detachmentId(unit.getDetachmentId())
                .notes(unit.getNotes())
                .build();
    }
}
