package com.cczora.armybuilder.data.fields;

import com.cczora.armybuilder.models.entity.UnitField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitFieldsRepo extends CrudRepository<UnitField, String> {
}
