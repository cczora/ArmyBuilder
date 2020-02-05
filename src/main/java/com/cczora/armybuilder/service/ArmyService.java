package com.cczora.armybuilder.service;

import com.cczora.armybuilder.models.Army;
import com.cczora.armybuilder.models.DetachmentType;
import com.cczora.armybuilder.models.FactionType;
import com.cczora.armybuilder.models.UnitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArmyService {

    private final UnitOfWork unit;

    @Autowired
    public ArmyService(UnitOfWork unit) {
        this.unit = unit;
    }

    public Army getArmyById(UUID armyId) {
        return unit.getArmyById(armyId);
    }

    public List<Army> getArmiesByUsername(String username) {
        return unit.getArmiesByUsername(username);
    }

    public List<FactionType> getAllFactions() {
        return unit.getAllFactions();
    }

    public List<UnitType> getAllUnitTypes() {
        return unit.getAllUnitTypes();
    }

    public List<DetachmentType> getAllDetachmentTypes() {
        return unit.getAllDetachmentTypes();
    }

    public List<Army> addArmy(Army army, String username) {
        return unit.addArmy(army, username);
    }

    public List<Army> deleteArmyById(String username, UUID armyId) {
        return unit.deleteArmyById(username, armyId);
    }

    public Army editArmy(String username, UUID armyId, Army army) {
        return unit.editArmy(username, armyId, army);
    }

}
