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

    @Query(value = "select u.id, detachment_id, unit_type_id, name, notes from unit u where u.detachment_id = :detachmentId", nativeQuery = true)
    List<Unit> findAllByDetachmentId(@Param(value = "detachmentId") UUID detachmentId);

    @Query(value = "select distinct d.detachment_id from Detachment d inner join unit u on u.detachment_id = d.detachment_id where u.unit_id = :unitId", nativeQuery = true)
    UUID findDetachmentIdForUnit(@Param("unitId") UUID unitId);

}
