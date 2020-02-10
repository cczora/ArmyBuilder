package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {

    @Query(value = "select * from unit u where u.detachment_id = :detachmentId", nativeQuery = true)
    List<Unit> findAllByDetachmentId(@Param(value = "detachmentId") UUID detachmentId);

}
