package com.cczora.armybuilder.data;

import com.cczora.armybuilder.models.DetachmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class DetachmentTypeRepository implements JpaRepository<DetachmentType, String> {

}
