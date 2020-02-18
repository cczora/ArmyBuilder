package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.ArmyFieldRepository;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.FactionType;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.mapping.ArmyMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.ValidationException;
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
    private final ArmyMapper mapper;
    private final ArmyFieldRepository armyFieldsRepo;
    private final FactionTypeRepository factionRepo;
    private final DetachmentRepository detachmentRepo;
    private final UnitRepository unitRepo;

    @Autowired
    public ArmyService(UserRepository userRepo, ArmyRepository armyRepo, ArmyMapper mapper, ArmyFieldRepository armyFieldsRepo, FactionTypeRepository factionRepo,
                       DetachmentRepository detachmentRepo, UnitRepository unitRepo) {
        this.userRepo = userRepo;
        this.armyRepo = armyRepo;
        this.mapper = mapper;
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

    public List<ArmyDTO> getArmiesByUsername(String username) throws NotFoundException {
        Optional<List<Army>> armies = Optional.ofNullable(armyRepo.findArmiesByUsername(username));
        if(armies.isPresent()) {
            return armies.get().stream().map(mapper::armyToArmyDTO).collect(Collectors.toList());
        }
        else {
            String message = String.format("User %s has no armies", username);
            log.error("Error getting armies for user {}: {}", username, message);
            throw new NotFoundException(message);
        }
    }

    public ArmyDTO addArmy(@Valid ArmyDTO army, String username) throws NotFoundException, ValidationException, DataAccessException {
        if(Optional.ofNullable(factionRepo.findFactionTypeByName(army.getFactionName())).isEmpty()) {
            throw new NotFoundException(String.format("Faction %s not found.", army.getFactionName()));
        }
        Army armyEntity = mapper.armyDTOToArmy(army, username);
        if(army.getArmyId() == null) {
            armyEntity.setId(UUID.randomUUID());
        }
        armyEntity.setCommandPoints(3);
        armyEntity.setUsername(username);
        armyEntity.setFaction(factionRepo.findFactionTypeByName(army.getFactionName()));
        armyEntity = armyRepo.save(armyEntity);
        return mapper.armyToArmyDTO(armyEntity);
    }

    @Transactional
    public void deleteArmyById(UUID id) throws PersistenceException {
        log.debug("Deleting army {}", id);
        try {
            List<Detachment> detachments = detachmentRepo.findAllByArmyId(id);
            detachmentRepo.deleteAll(detachments);

            for(Detachment d : detachments) {
                List<Unit> units = unitRepo.findAllByDetachmentId(d.getId());
                unitRepo.deleteAll(units);
            }
            armyRepo.deleteById(id);
            log.debug("Successfully deleted army {}", id);
        }
        catch(DataAccessException e) {
            log.error("Error deleting army {}: {}", id, e.getMessage());
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public void editArmy(ArmyPatchRequestDTO armyUpdates) throws PersistenceException, NoSuchFieldException, NotFoundException {
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
                            if(Optional.ofNullable(factionRepo.findFactionTypeByName(newFaction)).isEmpty()) {
                                throw new NotFoundException(String.format("Faction %s not found.", newFaction));
                            }
                            if(!factionRepo.findById(currArmy.getFactionTypeId()).get().getName().equals(newFaction)) {
                                FactionType newFT = factionRepo.findFactionTypeByName(newFaction);
                                currArmy.setFaction(newFT);
                                currArmy.setFactionTypeId(newFT.getFactionTypeId());
                            }
                            break;
                        case "commandPoints":
                            int newCP = Integer.parseInt(updates.get(field).toString());
                            if(currArmy.getCommandPoints() != newCP && newCP > 0) {
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
            log.error("Error editing army {}: {}", armyUpdates.getArmyId(), e.getMessage());
            throw e;
        }
    }
}
