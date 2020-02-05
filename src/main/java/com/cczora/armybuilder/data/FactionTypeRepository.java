package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.FactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public abstract class FactionTypeRepository implements JpaRepository<FactionType, UUID> {
}
