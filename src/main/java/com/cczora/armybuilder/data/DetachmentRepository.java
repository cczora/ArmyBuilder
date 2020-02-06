package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Detachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DetachmentRepository extends JpaRepository<Detachment, UUID> {

    @Query(value = "select * from detachment d where d.detachment_id in (" +
            "select a.army_id from army a where a.)", nativeQuery = true)
    List<Detachment> findAllByArmyId(UUID armyId);

}
