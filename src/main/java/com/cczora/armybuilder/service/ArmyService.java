package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.FactionTypeRepository;
import com.cczora.armybuilder.models.Army;
import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.models.FactionType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ArmyService {

    private ArmyRepository armyRepo;
    private FactionTypeRepository factionRepo;

    public List<FactionType> getAllFactions() {
        return factionRepo.findAll();
    }

    public Army getArmyById(UUID armyId) {
        return armyRepo.findById(armyId).isPresent() ? armyRepo.findById(armyId).get() : new Army();
    }

    public List<Army> getArmiesByUsername(String username) {
        return armyRepo.findArmiesByUsername(username);
    }

    public List<Army> addArmy(Army army, String username) {
        armyRepo.save(army);
        return armyRepo.findArmiesByUsername(username);
    }

    public List<Army> deleteArmyById(String username, UUID armyId) {
        armyRepo.deleteById(armyId);
        return armyRepo.findArmiesByUsername(username);
    }

    public Army editArmy(String username, UUID armyId, Army army) {
        armyRepo.save(army);
        return armyRepo.findById(armyId).isPresent() ? armyRepo.findById(armyId).get(): new Army();
    }

}
