package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.DetachmentTypeRepository;
import com.cczora.armybuilder.data.FactionTypeRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.fields.DetachmentFieldsRepository;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.DetachmentPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.mapping.DetachmentMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DetachmentService {

    private final DetachmentRepository detachmentRepository;
    private final static DetachmentMapper mapper = DetachmentMapper.MAPPER;
    private final DetachmentTypeRepository detachmentTypeRepo;
    private final DetachmentFieldsRepository detachmentFieldsRepo;
    private final FactionTypeRepository factionRepo;
    private final UnitRepository unitRepo;

    @Autowired
    public DetachmentService(DetachmentRepository detachmentRepository, DetachmentTypeRepository detachmentTypeRepository, DetachmentFieldsRepository detachmentFieldsRepo, FactionTypeRepository factionRepo, UnitRepository unitRepo) {
        this.detachmentRepository = detachmentRepository;
        this.detachmentTypeRepo = detachmentTypeRepository;
        this.detachmentFieldsRepo = detachmentFieldsRepo;
        this.factionRepo = factionRepo;
        this.unitRepo = unitRepo;
    }

    public List<DetachmentType> getAllDetachmentTypes() {
        return detachmentTypeRepo.findAll();
    }

    public List<?> getDetachmentsByArmyId(UUID armyId, boolean getFullDetachments) {
        List<Detachment> detachmentsForArmy =  detachmentRepository.findAllByArmyId(armyId);
        if(getFullDetachments) {
            for(Detachment d : detachmentsForArmy) {
                d.setUnits(unitRepo.findAllByDetachmentId(d.getDetachmentId()));
            }
            return detachmentsForArmy;
        }
        else {
            return detachmentsForArmy.stream().map(mapper::detachmentToDetachmentDTO).collect(Collectors.toList());
        }
    }

    public Detachment getFullDetachment(UUID detachmentId) throws NotFoundException {
        Optional<Detachment> detachment = detachmentRepository.findById(detachmentId);
        if(detachment.isEmpty()) {
            log.error("Detachment {} not found.", detachmentId);
            throw new NotFoundException(String.format("Detachment %s not found", detachmentId));
        }
        List<Unit> units = unitRepo.findAllByDetachmentId(detachmentId);
        detachment.get().setUnits(units);
        return detachment.get();
    }

    public void addDetachment(DetachmentDTO detachment) throws PersistenceException {
        try {
            Detachment toSave = mapper.detachmentDTOtoDetachment(detachment);
            toSave.setDetachmentId(UUID.randomUUID());
            toSave.setUnits(Lists.newArrayList());
            detachmentRepository.save(toSave);
            mapper.detachmentToDetachmentDTO(toSave);
        }
        catch(DataAccessException e) {
            log.error("Error adding detachment {}: {}", detachment.getName(), e.getMessage());
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public void editDetachment(DetachmentPatchRequestDTO dto) throws NoSuchFieldException, PersistenceException {
        try {
            Map<String, Object> updates = AppConstants.checkRequiredFieldsForPatch(detachmentFieldsRepo, dto.getUpdates());
            Optional<Detachment> currentDetachment = detachmentRepository.findById(dto.getDetachmentId());
            if(currentDetachment.isPresent()) {
                Detachment currDetach = currentDetachment.get();
                for (String field : updates.keySet()) {
                    switch (field) {
                        case "detachment_type_id":
                            String newType = updates.get(field).toString();
                            if (!currDetach.getDetachmentType().getName().equals(newType)) {
                                currDetach.setDetachmentType(detachmentTypeRepo.getOne(newType));
                            }
                            break;
                        case "faction_type_id":
                            String newFaction = updates.get(field).toString();
                            if (!currDetach.getFaction().getName().equals(newFaction)) {
                                currDetach.setFaction(factionRepo.findFactionTypeByName(newFaction));
                            }
                            break;
                        case "name":
                            String newName = updates.get(field).toString();
                            if (!currDetach.getName().equals(newName)) {
                                currDetach.setName(newName);
                            }
                            break;
                        case "notes":
                            String newNotes = updates.get(field).toString();
                            if (!currDetach.getNotes().equals(newNotes)) {
                                currDetach.setNotes(newNotes);
                            }
                            break;
                        default:
                            break;
                    }
                }
                detachmentRepository.save(currDetach);
            }
        }
        catch(NoSuchFieldException e) {
            log.error("Error editing detachment {}: {}", dto.getDetachmentId(), e.getMessage());
            throw new NoSuchFieldException(e.getMessage());
        }
    }

    public void deleteDetachment(UUID detachmentId, UUID armyId) throws NotFoundException, PersistenceException {
        boolean isInArmy = detachmentRepository.findAllByArmyId(armyId).stream()
                .anyMatch(d -> d.getDetachmentId().equals(detachmentId));
        if(!isInArmy) {
            log.error("Detachment {} not found in army {}", detachmentId, armyId);
            throw new NotFoundException(String.format("Detachment %s not found in army %s", detachmentId, armyId));
        }
        detachmentRepository.deleteById(detachmentId);
    }
}
