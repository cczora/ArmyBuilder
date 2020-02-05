package com.cczora.armybuilder.service;

import com.cczora.armybuilder.models.Unit;
import com.cczora.armybuilder.models.UnitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UnitService {
    
    private final UnitOfWork unit;
    
    @Autowired
    public UnitService(UnitOfWork unit) {
        this.unit = unit;
    }

    public List<Unit> getUnitsForDetachment(UUID detachmentId) {
        return unit.getUnitsForDetachment(detachmentId);
    }

    public List<UnitType> getAllUnitTypes() {
        return unit.getAllUnitTypes();
    }

    public Unit addUnit(Unit unitToAdd, UUID detachmentId, UUID armyId) {
        return unit.addUnit(unitToAdd, detachmentId, armyId);
    }

    public Unit editUnit(UUID unitId, Unit unitToEdit) {
        return unit.editUnit(unitId, unitToEdit);
    }

    public boolean deleteUnitById(UUID unitId) {
        return unit.deleteUnitById(unitId);
    }
    
}
