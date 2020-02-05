package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.models.Detachment;
import com.cczora.armybuilder.models.DetachmentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DetachmentService {

    private final DetachmentRepository detachmentRepository;

    public List<DetachmentType> getAllDetachmentTypes() {
        return unit.getAllDetachmentTypes();
    }

    public List<Detachment> getDetachmentsByArmyId(UUID armyId) {
        return unit.getDetachmentsByArmyId(armyId);
    }

    public Detachment addDetachment(Detachment detachment, UUID armyId) throws Exception {
        return unit.addDetachment(detachment, armyId);
    }

    public List<Detachment> editDetachment(UUID detachmentId, UUID armyId, Detachment detachment) {
        return unit.editDetachment(detachmentId, armyId, detachment);
    }

    public List<Detachment> deleteDetachment(UUID detachmentId, UUID armyId) {
        return unit.deleteDetachmentById(armyId, detachmentId);
    }

}
