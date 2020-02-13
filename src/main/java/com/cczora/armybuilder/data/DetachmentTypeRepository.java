package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.entity.DetachmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetachmentTypeRepository extends JpaRepository<DetachmentType, String> {
    DetachmentType findDetachmentTypeByName(String name);
}
