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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@NoArgsConstructor
public class DetachmentService {

    private DetachmentRepository detachmentRepository;
    private DetachmentTypeRepository detachmentTypeRepo;
    private DetachmentFieldsRepository detachmentFieldsRepo;
    private FactionTypeRepository factionRepo;
    private UnitRepository unitRepo;

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

    public List<Detachment> getDetachmentsByArmyId(UUID armyId) {
        List<Detachment> detachmentsForArmy =  detachmentRepository.findAllByArmyId(armyId);
        for(Detachment d : detachmentsForArmy) {
            d.setUnits(unitRepo.findAllByDetachmentId(d.getDetachmentId()));
        }
        return detachmentsForArmy;
    }

    public Detachment addDetachment(Detachment detachment, UUID armyId) {
        detachmentRepository.save(detachment);
        return detachmentRepository.findById(armyId).isPresent() ? detachmentRepository.findById(armyId).get() : new Detachment();
    }

    public void editDetachment(DetachmentPatchRequestDTO dto) throws Exception {
        Map<String, Object> updates = AppConstants.checkRequiredFieldsForPatch(detachmentFieldsRepo, dto.getUpdates());
        Detachment currentDetachment = detachmentRepository.findById(dto.getDetachmentId()).get();
        for (String field : updates.keySet()) {
            switch (field) {
                case "detachment_type_id":
                    String newType = updates.get(field).toString();
                    if (!currentDetachment.getDetachmentType().getName().equals(newType)) {
                        currentDetachment.setDetachmentType(detachmentTypeRepo.getOne(newType));
                    }
                    break;
                case "faction_type_id":
                    String newFaction = updates.get(field).toString();
                    if (!currentDetachment.getFaction().getName().equals(newFaction)) {
                        currentDetachment.setFaction(factionRepo.findFactionTypeByName(newFaction));
                    }
                    break;
                case "name":
                    String newName = updates.get(field).toString();
                    if (!currentDetachment.getName().equals(newName)) {
                        currentDetachment.setName(newName);
                    }
                    break;
                case "notes":
                    String newNotes = updates.get(field).toString();
                    if (!currentDetachment.getNotes().equals(newNotes)) {
                        currentDetachment.setNotes(newNotes);
                    }
                    break;
                default:
                    break;
            }
        }
        detachmentRepository.save(currentDetachment);
    }

    public void deleteDetachment(UUID detachmentId, UUID armyId) throws NotFoundException {
        boolean isInArmy = detachmentRepository.findAllByArmyId(armyId).stream()
                .anyMatch(d -> d.getDetachmentId().equals(detachmentId));
        if(!isInArmy) {
            log.error("Detachment {} not found in army {}", detachmentId, armyId);
            throw new NotFoundException(String.format("Detachment %s not found in army %s", detachmentId, armyId));
        }
        detachmentRepository.deleteById(detachmentId);
        unitRepo.deleteAll(unitRepo.findAllByDetachmentId(detachmentId));
    }

}
