package com.cczora.armybuilder.data.fields;

import com.cczora.armybuilder.models.entity.ArmyField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmyFieldRepository extends CrudRepository<ArmyField, String> {
}
