package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public abstract class UnitTypeRepository implements JpaRepository<UnitType, UUID> {
}
