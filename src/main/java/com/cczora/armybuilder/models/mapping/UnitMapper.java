package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UnitTypeRepository;
import com.cczora.armybuilder.models.dto.UnitDTO;
import com.cczora.armybuilder.models.entity.Unit;
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
        return Unit.builder()
                .detachmentId(dto.getDetachmentId())
                .id(dto.getId())
                .name(dto.getName())
                .unitType(typeRepo.findByName(dto.getName()).get())
                .notes(dto.getNotes())
                .build();
    }

    public UnitDTO entityToDTO(Unit unit) {
        return UnitDTO.builder()
                .name(unit.getName())
                .unitType(unit.getUnitType().getName())
                .id(unit.getId())
                .detachmentId(unit.getDetachmentId())
                .notes(unit.getNotes())
                .build();
    }
}
