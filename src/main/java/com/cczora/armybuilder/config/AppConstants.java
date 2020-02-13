package com.cczora.armybuilder.config;

import com.cczora.armybuilder.models.Field;
import com.cczora.armybuilder.models.KeyValuePair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class AppConstants {

    public static final String basePath = "/armybuilder";

    public static Map<String, Object> checkRequiredFieldsForPatch(CrudRepository<?,?> repo, List<KeyValuePair> inputFields) throws NoSuchFieldException {
        List<String> updateFields = StreamSupport.stream(repo.findAll().spliterator(), false)
                .filter(f -> Field.isPatchEnabed((Field) f))
                .map(f -> Field.getNam((Field) f))
                .collect(Collectors.toList());
        Map<String, Object> updates = inputFields.stream()
                .collect(Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue));
        for (String updateField : updates.keySet()) {
            if (!updateFields.contains(updateField)) {
                log.error("Invalid field {}", updateField);
                throw new NoSuchFieldException(String.format("Field %s is not allowed to be updated. Supported fields are %s.", updateField, updateFields.toString()));
            }
        }
        return updates;
    }
}
