package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Detachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DetachmentRepository extends JpaRepository<Detachment, UUID> {

    List<Detachment> findAllByArmyId(UUID armyId);

    @Query(value = "select distinct cast(d.army_id as varchar) from Detachment d where d.id = :detachmentId", nativeQuery = true)
    UUID findArmyIdForDetachment(@Param(value = "detachmentId") UUID detachmentId);
}
