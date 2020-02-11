package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.FactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FactionTypeRepository extends JpaRepository<FactionType, UUID> {
    FactionType findFactionTypeByName(String name);
}
