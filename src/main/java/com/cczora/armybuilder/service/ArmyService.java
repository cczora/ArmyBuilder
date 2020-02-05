package com.cczora.armybuilder.service;

import com.cczora.armybuilder.models.Army;
import com.cczora.armybuilder.models.FactionType;
import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ArmyService {

    private ArmyRepository armyRepo;

    public Army getArmyById(UUID armyId) {
        return armyRepo.getArmyById(armyId);
    }

    public List<Army> getArmiesByUsername(String username) {
        return unit.getArmiesByUsername(username);
    }

    public List<FactionType> getAllFactions() {
        return unit.getAllFactions();
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
