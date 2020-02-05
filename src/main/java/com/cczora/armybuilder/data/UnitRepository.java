package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public abstract class UnitRepository implements JpaRepository<Unit, UUID> {

    public abstract List<Unit> findAllByDetachmentId(UUID detachmentId);

}
