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

    public UnitDTO addUnit(UnitDTO unitToAdd, UUID detachmentId, UUID armyId) throws DataAccessException, NotFoundException {
        try {
            if (detachmentRepo.findById(detachmentId).isPresent()
                    && detachmentService.getDetachmentsByArmyId(armyId).stream()
                    .map(DetachmentDTO::getDetachmentId)
                    .collect(Collectors.toList()).contains(detachmentId)) {
                if (unitToAdd.getId() == null) {
                    unitToAdd.setId(UUID.randomUUID());
                }
                unitToAdd.setDetachmentId(detachmentId);
                Unit toAdd = mapper.dtoToEntity(unitToAdd);
                toAdd = unitRepo.save(toAdd);
                return mapper.entityToDTO(toAdd);
            }
            else {
                if(!detachmentService.getDetachmentsByArmyId(armyId).stream()
                        .map(DetachmentDTO::getDetachmentId)
                        .collect(Collectors.toList()).contains(detachmentId)) {
                    throw new NotFoundException(String.format("Detachment %s is not in army %s", detachmentId, armyId));
                }
                else {
                    throw new NotFoundException(String.format("Detachment %s not found", detachmentId));
                }
            }
        }
        catch(Exception e) {
            log.error("Error adding unit {}: {}", unitToAdd.getName(), e.getMessage());
            throw e;
        }
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
                            if(typeInDB.isEmpty()) {
                                throw new NotFoundException(String.format("Unit type %s not found.", newType));
                            }
                            if(!typeInDB.get().getName().equals(newType)) {
                                currUnit.setUnitType(typeInDB.get());
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
                .anyMatch(u -> u.getId().equals(detachmentId));
        if(!isInDetachment) {
            log.error("Unit {} not found in detachment {}", unitId, detachmentId);
            throw new NotFoundException(String.format("Unit %s not found in detachment %s", unitId, detachmentId));
        }
        unitRepo.deleteById(unitId);
    }

}
