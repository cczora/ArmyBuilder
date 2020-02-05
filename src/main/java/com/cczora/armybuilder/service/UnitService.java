package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UnitTypeRepository;
import com.cczora.armybuilder.models.Unit;
import com.cczora.armybuilder.models.UnitType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UnitService {
    
    private UnitRepository unitRepo;
    private UnitTypeRepository unitTypeRepo;

    public List<Unit> getUnitsForDetachment(UUID detachmentId) {
        return unitRepo.findAllByDetachmentId(detachmentId);
    }

    public List<UnitType> getAllUnitTypes() {
        return unitTypeRepo.findAll();
    }

    public Unit addUnit(Unit unitToAdd, UUID detachmentId, UUID armyId) {
        if(unitToAdd.getUnitId() == null) {
            unitToAdd.setUnitId(UUID.randomUUID());
        }
        unitToAdd.setDetachmentId(detachmentId);
        unitRepo.save(unitToAdd);
        return unitToAdd;
    }

    public Unit editUnit(UUID unitId, Unit unitToEdit) {
        unitRepo.save(unitToEdit);
        return unitRepo.findById(unitId).get();
    }

    public boolean deleteUnitById(UUID unitId) {
        unitRepo.delete(unitRepo.getOne(unitId));
        return unitRepo.findById(unitId).isEmpty();
    }
    
}
