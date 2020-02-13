package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.data.DetachmentTypeRepository;
import com.cczora.armybuilder.data.FactionTypeRepository;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.entity.Detachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetachmentMapper {

    private final DetachmentTypeRepository typeRepo;
    private final FactionTypeRepository factionRepo;

    @Autowired
    public DetachmentMapper(DetachmentTypeRepository typeRepo, FactionTypeRepository factionRepo) {
        this.typeRepo = typeRepo;
        this.factionRepo = factionRepo;
    }

    public Detachment dtoToEntity(DetachmentDTO dto) {
        return Detachment.builder()
                .armyId(dto.getArmyId())
                .detachmentId(dto.getDetachmentId())
                .detachmentType(typeRepo.findDetachmentTypeByName(dto.getDetachmentType()))
                .faction(factionRepo.findFactionTypeByName(dto.getFactionType()))
                .name(dto.getName())
                .notes(dto.getNotes())
                .build();
    }

    public DetachmentDTO entityToDto(Detachment detachment) {
        return DetachmentDTO.builder()
                .detachmentId(detachment.getDetachmentId())
                .armyId(detachment.getArmyId())
                .notes(detachment.getNotes())
                .detachmentType(detachment.getDetachmentType().getName())
                .factionType(detachment.getFaction().getName())
                .name(detachment.getName())
                .build();
    }
}
