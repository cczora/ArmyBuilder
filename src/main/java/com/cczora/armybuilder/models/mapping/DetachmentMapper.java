package com.cczora.armybuilder.models.mapping;

import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.entity.Detachment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DetachmentMapper {
    DetachmentMapper MAPPER = Mappers.getMapper(DetachmentMapper.class);

    Detachment detachmentDTOtoDetachment(DetachmentDTO dto);
    DetachmentDTO detachmentToDetachmentDTO(Detachment detachment);
}
