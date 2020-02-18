package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UnitTypeRepository;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.entity.UnitType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@NoArgsConstructor
public class UnitService {

    private DetachmentRepository detachmentRepo;
    private UnitRepository unitRepo;
    private UnitTypeRepository unitTypeRepo;

    @Autowired
    public UnitService(DetachmentRepository detachmentRepo, UnitRepository unitRepo, UnitTypeRepository unitTypeRepo) {
        this.detachmentRepo = detachmentRepo;
        this.unitRepo = unitRepo;
        this.unitTypeRepo = unitTypeRepo;
    }

    public List<Unit> getUnitsForDetachment(UUID detachmentId) {
        return unitRepo.findAllByDetachmentId(detachmentId);
    }

    public List<UnitType> getAllUnitTypes() {
        return unitTypeRepo.findAll();
    }

    public Unit addUnit(Unit unitToAdd, UUID detachmentId, UUID armyId) {
        if(unitToAdd.getDetachmentId() == null) {
            unitToAdd.setId(UUID.randomUUID());
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
