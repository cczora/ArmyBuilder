package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Army;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArmyRepository extends JpaRepository<Army, UUID> {
    List<Army> findArmiesByUsername(String username);
}
