package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.FactionType;
import com.cczora.armybuilder.models.entity.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArmyService {

    private final UserRepository userRepo;
    private final ArmyRepository armyRepo;
    private final FactionTypeRepository factionRepo;
    private final DetachmentRepository detachmentRepo;
    private final UnitRepository unitRepo;

    @Autowired
    public ArmyService(UserRepository userRepo, ArmyRepository armyRepo, FactionTypeRepository factionRepo,
                       DetachmentRepository detachmentRepo, UnitRepository unitRepo) {
        this.userRepo = userRepo;
        this.armyRepo = armyRepo;
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
        //todo: refactor this once it works
        List<Army> armies = armyRepo.findAllByUsername(username);
        List<ArmyDTO> dtos = armies.stream().map(this::mapArmyToArmyDTO).collect(Collectors.toList());
        return dtos;
    }

    public List<Army> addArmy(ArmyDTO army, String username) throws Exception {
        try {
            log.debug("Adding army {} for user {}", army.getName(), username);
            FactionType faction = factionRepo.findFactionTypeByName(army.getFactionName());
            armyRepo.save(Army.builder()
                    .army_id(UUID.randomUUID())
                    .name(army.getName())
                    .faction(faction)
                    .username(username)
                    .sizeClass(army.getSizeClass())
                    .notes(army.getNotes())
                    .build());
            return armyRepo.findAllByUsername(username);
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
            armyRepo.deleteById(id);
            //TODO: add SQl triggers to delete detachments and units for armies instead of relying on repos
            List<Detachment> detachments = detachmentRepo.findAllByArmyId(id);
            detachmentRepo.deleteAll(detachments);

            for(Detachment d : detachments) {
                List<Unit> units = unitRepo.findAllByDetachmentId(d.getDetachmentId());
                unitRepo.deleteAll(units);
            }
            log.debug("Successfully deleted army {}", id);
        }
        catch(Exception e) {
            log.error("Error deleting army: {}", e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

    public Army editArmy(String username, UUID armyId, Army army) {
        armyRepo.save(army);
        return armyRepo.findById(armyId).isPresent() ? armyRepo.findById(armyId).get(): new Army();
    }

    //region private methods

    private ArmyDTO mapArmyToArmyDTO(Army a) { //TODO: look into using Mapstruct for simple mapping like this one
        return ArmyDTO.builder()
                .commandPoints(a.getCommandPoints())
                .factionName(a.getFaction().getName())
                .name(a.getName())
                .notes(a.getNotes())
                .sizeClass(a.getSizeClass())
                .build();
    }

    //endregion

}
