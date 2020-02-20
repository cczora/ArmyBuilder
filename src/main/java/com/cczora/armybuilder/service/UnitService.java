package com.cczora.armybuilder.service;

import com.cczora.armybuilder.config.AppConstants;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UnitTypeRepository;
import com.cczora.armybuilder.data.fields.UnitFieldsRepo;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.UnitDTO;
import com.cczora.armybuilder.models.dto.UnitPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.entity.UnitType;
import com.cczora.armybuilder.models.mapping.UnitMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UnitService {

    private DetachmentRepository detachmentRepo;
    private DetachmentService detachmentService;
    private UnitRepository unitRepo;
    private UnitMapper mapper;
    private UnitTypeRepository unitTypeRepo;
    private UnitFieldsRepo unitFieldsRepo;

    @Autowired
    public UnitService(DetachmentRepository detachmentRepo, DetachmentService detachmentService, UnitRepository unitRepo, UnitMapper mapper, UnitTypeRepository unitTypeRepo, UnitFieldsRepo unitFieldsRepo) {
        this.detachmentRepo = detachmentRepo;
        this.detachmentService = detachmentService;
        this.unitRepo = unitRepo;
        this.mapper = mapper;
        this.unitTypeRepo = unitTypeRepo;
        this.unitFieldsRepo = unitFieldsRepo;
    }

    public List<UnitDTO> getUnitsForDetachment(UUID detachmentId) throws PersistenceException {
        List<Unit> unitsForDetachment = unitRepo.findAllByDetachmentId(detachmentId);
        return unitsForDetachment.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public List<UnitType> getAllUnitTypes() {
        return unitTypeRepo.findAll();
    }

    public UnitDTO addUnit(UnitDTO unitToAdd) throws NotFoundException {
        try {
            Optional<UUID> armyId = Optional.ofNullable(detachmentService.findArmyIdForDetachment(unitToAdd.getDetachmentId()));
            if (armyId.isPresent()) {
                Optional<List<DetachmentDTO>> detachments = Optional.ofNullable(detachmentService.getDetachmentsByArmyId(armyId.get()));
                if (detachments.isPresent()) {
                    boolean isInDetachmentsForArmy = detachments.get().stream()
                            .map(DetachmentDTO::getDetachmentId)
                            .collect(Collectors.toList()).contains(unitToAdd.getDetachmentId());
                    if (isInDetachmentsForArmy) {
                        //validate unit type name, add random uuid and standard name if not given
                        if (unitToAdd.getId() == null) unitToAdd.setId(UUID.randomUUID());
                        Optional<UnitType> typeToAdd = unitTypeRepo.findByName(unitToAdd.getUnitType());
                        if (typeToAdd.isEmpty()) {
                            String message = String.format("Unit type %s not found.", unitToAdd.getUnitType());
                            log.error(message);
                            throw new NotFoundException(message);
                        }
                        //default name
                        if (unitToAdd.getName() == null)
                            unitToAdd.setName(String.format("New %s Unit", typeToAdd.get().getName()));
                        Unit unitEntity = unitRepo.save(mapper.dtoToEntity(unitToAdd));
                        return mapper.entityToDTO(unitEntity);
                    }
                } else { //no detachments
                    String message = String.format("Detachment %s not found in army %s", unitToAdd.getDetachmentId(), armyId);
                    log.error(message);
                    throw new NotFoundException(message);
                }
            } else { //no army!
                String message = String.format("Unit %s is not part of an army!", unitToAdd.getId());
                log.error(message);
                throw new NotFoundException(message);
            }
        }
        catch(Exception e) {
            log.error("Error adding unit {}: {}", unitToAdd.getName(), e.getMessage());
            throw e;
        }
        return null;
    }

    public void editUnit(UnitPatchRequestDTO dto) throws PersistenceException, NoSuchFieldException, NotFoundException {
        try {
            Map<String, Object> updates = AppConstants.checkRequiredFieldsForPatch(unitFieldsRepo, dto.getUpdates());
            Optional<Unit> currentUnit = unitRepo.findById(dto.getUnitId());
            if(currentUnit.isPresent()) {
                Unit currUnit = currentUnit.get();
                for (String field : updates.keySet()) {
                    switch (field) {
                        case "unit_type_id":
                            String newType = updates.get(field).toString();
                            Optional<UnitType> typeInDB = unitTypeRepo.findByName(newType);
                            if(typeInDB.isEmpty() || unitTypeRepo.findById(currUnit.getUnitTypeId()).isEmpty()) {
                                throw new NotFoundException(String.format("Unit type %s not found.", newType));
                            }
                            if(!unitTypeRepo.findById(currUnit.getUnitTypeId()).get().getName().equals(newType)) {
                                currUnit.setUnitType(typeInDB.get());
                                currUnit.setUnitTypeId(typeInDB.get().getUnit_type_id());
                            }
                            break;
                        case "name":
                            String newName = updates.get(field).toString();
                            if (!currUnit.getName().equals(newName)) {
                                currUnit.setName(newName);
                            }
                            break;
                        case "notes":
                            String newNotes = updates.get(field).toString();
                            if (!currUnit.getNotes().equals(newNotes)) {
                                currUnit.setNotes(newNotes);
                            }
                            break;
                        default:
                            break;
                    }
                }
                unitRepo.save(currUnit);
            }
        }
        catch(Exception e) {
            log.error("Error editing unit {}: {}", dto.getUnitId(), e.getMessage());
            throw e;
        }
    }

    public void deleteUnitById(UUID unitId, UUID detachmentId) {
        boolean isInDetachment = unitRepo.findAllByDetachmentId(detachmentId).stream()
                .anyMatch(u -> u.getId().equals(unitId));
        if (!isInDetachment) {
            log.error("Unit {} not found in detachment {}", unitId, detachmentId);
            throw new NotFoundException(String.format("Unit %s not found in detachment %s", unitId, detachmentId));
        }
        unitRepo.deleteById(unitId);
    }

    //region private methods

    private Optional<UUID> findArmyIdForUnit(UUID id) {
        return Optional.ofNullable(unitRepo.findArmyIdForUnit(id));
    }

    //endregion

}
