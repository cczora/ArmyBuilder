package com.cczora.armybuilder.data.fields;

import com.cczora.armybuilder.models.entity.DetachmentField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetachmentFieldsRepository extends CrudRepository<DetachmentField, String> {
}
