package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.Army;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public abstract class ArmyRepository implements JpaRepository<Army, UUID> {

    public abstract List<Army> findArmiesByUsername(String username);

}
