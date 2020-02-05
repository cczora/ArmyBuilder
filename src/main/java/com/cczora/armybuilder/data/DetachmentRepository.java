package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.Detachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public abstract class DetachmentRepository implements JpaRepository<Detachment, UUID> {

    public abstract List<Detachment> findAllByArmyId(UUID armyId);

}
