package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.FactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FactionTypeRepository extends JpaRepository<FactionType, UUID> {

    @Query(value = "select f.faction_type_id, f.name from faction_type f where f.name = :name", nativeQuery = true)
    FactionType findFactionTypeByName(@Param(value = "name") String name);

}
