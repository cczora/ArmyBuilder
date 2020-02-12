package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.ArmyFieldRepository;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.entity.*;
import com.cczora.armybuilder.models.mapping.ArmyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArmyService {

    private final UserRepository userRepo;
    private final ArmyRepository armyRepo;
    private static final ArmyMapper mapper = ArmyMapper.MAPPER;
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

    public ArmyDTO addArmy(ArmyDTO army, String username) throws PersistenceException {
        try {
            log.debug("Adding army {} for user {}", army.getName(), username);
            FactionType faction = factionRepo.findFactionTypeByName(army.getFactionName());
            Army armyEntity = mapper.armyDTOToArmy(army);
            armyEntity.setArmy_id(UUID.randomUUID());
            armyEntity.setCommandPoints(3);
            armyEntity.setUsername(username);
            armyEntity = armyRepo.save(armyEntity);
            log.debug("Successfully added army {}", armyEntity.getArmy_id());
            return mapper.armyToArmyDTO(armyEntity);
        }
        catch(DataAccessException e) {
            log.error("Error adding army {}: {}", army.getName(), e);
            throw new PersistenceException(e.getMessage(), e);
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
        try {
            Map<String, Object> updates = AppConstants.checkRequiredFieldsForPatch(armyFieldsRepo, armyUpdates.getUpdates());
            Optional<Army> currentArmy = armyRepo.findById(armyUpdates.getArmyId());
            if(currentArmy.isPresent()) {
                Army currArmy = currentArmy.get();
                for(String field : updates.keySet()) {
                    switch(field) {
                        case "name":
                            String newName = updates.get(field).toString();
                            if(!currArmy.getName().equals(newName)) {
                                currArmy.setName(newName);
                            }
                            break;
                        case "faction_type_id":
                            String newFaction = updates.get(field).toString();
                            if(!currArmy.getFaction().getName().equals(newFaction)) {
                                currArmy.setFaction(factionRepo.findFactionTypeByName(newFaction));
                            }
                            break;
                        case "commandPoints":
                            int newCP = Integer.parseInt(updates.get(field).toString());
                            if(currArmy.getCommandPoints() != newCP) {
                                currArmy.setCommandPoints(newCP);
                            }
                            break;
                        case "size":
                            String newSize = updates.get(field).toString();
                            if(!currArmy.getSizeClass().equals(newSize)) {
                                currArmy.setSizeClass(newSize);
                            }
                            break;
                        case "notes":
                            String newNotes = updates.get(field).toString();
                            if(!currArmy.getNotes().equals(newNotes)) {
                                currArmy.setNotes(newNotes);
                            }
                            break;
                        default:
                            break;
                    }
                }
                armyRepo.save(currArmy);
            }
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
