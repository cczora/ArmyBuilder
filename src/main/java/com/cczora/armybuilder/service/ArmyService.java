package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.dto.KeyValuePair;
import com.cczora.armybuilder.models.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ArmyService {

    private final UserRepository userRepo;
    private final ArmyRepository armyRepo;
    private final ArmyFieldRepository armyFieldsRepo;
    private final FactionTypeRepository factionRepo;
    private final DetachmentRepository detachmentRepo;
    private final UnitRepository unitRepo;

    @Autowired
    public ArmyService(UserRepository userRepo, ArmyRepository armyRepo, ArmyFieldRepository armyFieldsRepo, FactionTypeRepository factionRepo,
                       DetachmentRepository detachmentRepo, UnitRepository unitRepo) {
        this.userRepo = userRepo;
        this.armyRepo = armyRepo;
        this.armyFieldsRepo = armyFieldsRepo;
        this.factionRepo = factionRepo;
        this.detachmentRepo = detachmentRepo;
        this.unitRepo = unitRepo;
    }

    public List<FactionType> getAllFactions() {
        return factionRepo.findAll();
    }

    public Army getArmyById(UUID armyId) {
        return armyRepo.findById(armyId).isPresent() ? armyRepo.findById(armyId).get() : new Army();
    }

    public List<ArmyDTO> getArmiesByUsername(String username) {
        List<Army> armies = armyRepo.findAllByUsername(username);
        return armies.stream().map(this::mapArmyToArmyDTO).collect(Collectors.toList());
    }

    public ArmyDTO addArmy(ArmyDTO army, String username) throws Exception {
        try {
            log.debug("Adding army {} for user {}", army.getName(), username);
            FactionType faction = factionRepo.findFactionTypeByName(army.getFactionName());
            Army armyEntity = Army.builder()
                    .army_id(UUID.randomUUID())
                    .name(army.getName())
                    .faction(faction)
                    .username(username)
                    .sizeClass(army.getSizeClass())
                    .notes(army.getNotes())
                    .build();
            armyEntity = armyRepo.save(armyEntity);
            return mapArmyToArmyDTO(armyEntity);
        }
        catch(Exception e) {
            log.error("Error adding army {}: {}", army.getName(), e);
            throw new Exception(e.getMessage(), e); //TODO: make custom exceptions?
        }
    }

    @Transactional
    public void deleteArmyById(UUID id) throws Exception {
        log.debug("Deleting army {}", id);
        try {
            //TODO: add SQl triggers to delete detachments and units for armies instead of relying on repos
            List<Detachment> detachments = detachmentRepo.findAllByArmyId(id);
            detachmentRepo.deleteAll(detachments);

            for(Detachment d : detachments) {
                List<Unit> units = unitRepo.findAllByDetachmentId(d.getDetachmentId());
                unitRepo.deleteAll(units);
            }
            armyRepo.deleteById(id);
            log.debug("Successfully deleted army {}", id);
        }
        catch(Exception e) {
            log.error("Error deleting army: {}", e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

    public void editArmy(ArmyPatchRequestDTO armyUpdates) throws Exception {
        List<String> updateFields = StreamSupport.stream(armyFieldsRepo.findAll().spliterator(), false)
                .filter(ArmyField::isPatchEnabled)
                .map(ArmyField::getName)
                .collect(Collectors.toList());
        Map<String, Object> updates = armyUpdates.getUpdates().stream()
                .collect(Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue));
        for(String updateField : updates.keySet()) {
            if(!updateFields.contains(updateField)) {
                log.error("Invalid field {}", updateField);
                throw new NoSuchFieldException(String.format("Field %s is not allowed to be updated. Supported fields are %s.", updateField, updateFields.toString()));
            }
        }
        try {
            Army currentArmy = armyRepo.findById(armyUpdates.getArmyId()).get();
            for(String field : updates.keySet()) {
                switch(field) {
                    case "name":
                        String newName = updates.get(field).toString();
                        if(!currentArmy.getName().equals(newName)) {
                            currentArmy.setName(newName);
                        }
                        break;
                    case "faction_type_id":
                        String newFaction = updates.get(field).toString();
                        if(!currentArmy.getFaction().getName().equals(newFaction)) {
                            currentArmy.setFaction(factionRepo.findFactionTypeByName(newFaction));
                        }
                        break;
                    case "commandPoints":
                        int newCP = Integer.parseInt(updates.get(field).toString());
                        if(currentArmy.getCommandPoints() != newCP) {
                            currentArmy.setCommandPoints(newCP);
                        }
                        break;
                    case "size":
                        String newSize = updates.get(field).toString();
                        if(!currentArmy.getSizeClass().equals(newSize)) {
                            currentArmy.setSizeClass(newSize);
                        }
                        break;
                    case "notes":
                        String newNotes = updates.get(field).toString();
                        if(!currentArmy.getNotes().equals(newNotes)) {
                            currentArmy.setNotes(newNotes);
                        }
                        break;
                    default:
                        break;
                }
            }
            armyRepo.save(currentArmy);
        }
        catch(Exception e) {
            log.error("Error editing armyUpdates {}: {}", armyUpdates.getArmyId(), e.getMessage());
            throw new Exception(e);
        }
    }

    //region private methods

    public ArmyDTO mapArmyToArmyDTO(Army a) { //TODO: look into using Mapstruct for simple mapping like this one
        return ArmyDTO.builder()
                .armyId(a.getArmy_id())
                .commandPoints(a.getCommandPoints())
                .factionName(a.getFaction().getName())
                .name(a.getName())
                .notes(a.getNotes())
                .sizeClass(a.getSizeClass())
                .build();
    }

    //endregion

}
