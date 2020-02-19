package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.DetachmentFieldsRepository;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.DetachmentPatchRequestDTO;
import com.cczora.armybuilder.models.entity.*;
import com.cczora.armybuilder.models.mapping.DetachmentMapper;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@NoArgsConstructor
public class DetachmentService {

    private ArmyRepository armyRepo;
    private DetachmentRepository detachmentRepository;
    private DetachmentMapper mapper;
    private DetachmentTypeRepository detachmentTypeRepo;
    private DetachmentFieldsRepository detachmentFieldsRepo;
    private FactionTypeRepository factionRepo;
    private UnitRepository unitRepo;
    private UnitTypeRepository unitTypeRepo;

    @Autowired
    public DetachmentService(ArmyRepository armyRepo, DetachmentRepository detachmentRepository, DetachmentMapper mapper,
                             DetachmentTypeRepository detachmentTypeRepository, DetachmentFieldsRepository detachmentFieldsRepo,
                             FactionTypeRepository factionRepo, UnitRepository unitRepo, UnitTypeRepository unitTypeRepo) {
        this.armyRepo = armyRepo;
        this.detachmentRepository = detachmentRepository;
        this.mapper = mapper;
        this.detachmentTypeRepo = detachmentTypeRepository;
        this.detachmentFieldsRepo = detachmentFieldsRepo;
        this.factionRepo = factionRepo;
        this.unitRepo = unitRepo;
        this.unitTypeRepo = unitTypeRepo;
    }

    public List<DetachmentType> getAllDetachmentTypes() {
        return detachmentTypeRepo.findAll();
    }

    public List<DetachmentDTO> getDetachmentsByArmyId(UUID armyId) throws PersistenceException {
        List<Detachment> detachmentsForArmy =  detachmentRepository.findAllByArmyId(armyId);
        return detachmentsForArmy.stream().map(mapper::entityToDto).collect(Collectors.toList());

    }

    public List<Detachment> getFullDetachmentsByArmyId(UUID armyId) throws PersistenceException {
        List<Detachment> detachmentsForArmy =  detachmentRepository.findAllByArmyId(armyId);
        for(Detachment d : detachmentsForArmy) {
            d.setUnits(unitRepo.findAllByDetachmentId(d.getId()));
        }
        return detachmentsForArmy;
    }

    public Detachment getFullDetachment(UUID detachmentId) throws NotFoundException {
        Optional<Detachment> detachment = detachmentRepository.findById(detachmentId);
        if(detachment.isEmpty()) {
            log.error("Detachment {} not found.", detachmentId);
            throw new NotFoundException(String.format("Detachment %s not found", detachmentId));
        }
        List<Unit> units = unitRepo.findAllByDetachmentId(detachmentId);
        for(Unit u : units) {
            Optional<UnitType> type = unitTypeRepo.findById(u.getUnitTypeId());
            type.ifPresent(u::setUnitType);
        }
        detachment.get().setUnits(units);
        return detachment.get();
    }

    public void addDetachment(DetachmentDTO detachment) throws PersistenceException, NotFoundException {
        try {
            if(Optional.ofNullable(factionRepo.findFactionTypeByName(detachment.getFactionName())).isEmpty()) {
                throw new NotFoundException(String.format("Faction %s not found.", detachment.getFactionName()));
            }
            Detachment toSave = mapper.dtoToEntity(detachment);
            if(toSave.getFaction() == null) { //set the faction to the parent army's faction
                toSave.setFaction(armyRepo.findById(detachment.getArmyId()).get().getFaction());
            }
            if(detachment.getDetachmentId() == null) { //they already passed in a UUID to be used
                toSave.setId(UUID.randomUUID());
            }
            toSave.setUnits(Lists.newArrayList());
            detachmentRepository.save(toSave);
            mapper.entityToDto(toSave);
        }
        catch(DataAccessException e) {
            log.error("Error adding detachment {}: {}", detachment.getName(), e.getMessage());
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public void editDetachment(DetachmentPatchRequestDTO dto) throws PersistenceException, NoSuchFieldException, NotFoundException {
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
                                currDetach.setDetachmentType(detachmentTypeRepo.findDetachmentTypeByName(newType));
                            }
                            break;
                        case "faction_type_id":
                            String newFaction = updates.get(field).toString();
                            if(Optional.ofNullable(factionRepo.findFactionTypeByName(newFaction)).isEmpty()) {
                                throw new NotFoundException(String.format("Faction %s not found.", newFaction));
                            }
                            if(!factionRepo.findById(currDetach.getFaction().getFactionTypeId()).get().getName().equals(newFaction)) {
                                FactionType newFT = factionRepo.findFactionTypeByName(newFaction);
                                currDetach.setFaction(newFT);
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
        catch(Exception e) {
            log.error("Error editing detachment {}: {}", dto.getDetachmentId(), e.getMessage());
            throw e;
        }
    }

    public void deleteDetachment(UUID detachmentId, UUID armyId) throws NotFoundException {
        isInArmy(detachmentId, armyId);
        detachmentRepository.deleteById(detachmentId);
    }

    public void deleteUnitsForDetachment(UUID detachmentId, UUID armyId) throws NotFoundException {
        isInArmy(detachmentId, armyId);
        unitRepo.deleteInBatch(unitRepo.findAllByDetachmentId(detachmentId));
    }

    //region private methods

    private void isInArmy(UUID detachmentId, UUID armyId) throws NotFoundException {
        boolean isInArmy =  detachmentRepository.findAllByArmyId(armyId).stream()
                .anyMatch(d -> d.getId().equals(detachmentId));
        if(!isInArmy) {
            log.error("Detachment {} not found in army {}", detachmentId, armyId);
            throw new NotFoundException(String.format("Detachment %s not found in army %s", detachmentId, armyId));
        }
    }

    //endregion
}
