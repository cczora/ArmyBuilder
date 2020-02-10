package com.cczora.armybuilder.service;

import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.DetachmentTypeRepository;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@NoArgsConstructor
public class DetachmentService {

    private DetachmentRepository detachmentRepository;
    private DetachmentTypeRepository detachmentTypeRepository;

    @Autowired
    public DetachmentService(DetachmentRepository detachmentRepository, DetachmentTypeRepository detachmentTypeRepository) {
        this.detachmentRepository = detachmentRepository;
        this.detachmentTypeRepository = detachmentTypeRepository;
    }

    public List<DetachmentType> getAllDetachmentTypes() {
        return detachmentTypeRepository.findAll();
    }

    public List<Detachment> getDetachmentsByArmyId(UUID armyId) {
        return detachmentRepository.findAllByArmyId(armyId);
    }

    public Detachment addDetachment(Detachment detachment, UUID armyId) {
        detachmentRepository.save(detachment);
        return detachmentRepository.findById(armyId).isPresent() ? detachmentRepository.findById(armyId).get() : new Detachment();
    }

    public List<Detachment> editDetachment(UUID detachmentId, UUID armyId, Detachment detachment) {
        detachmentRepository.save(detachment);
        return detachmentRepository.findById(armyId).isPresent() ? detachmentRepository.findAllByArmyId(armyId) : new ArrayList<>();
    }

    public List<Detachment> deleteDetachment(UUID detachmentId, UUID armyId) {
        detachmentRepository.deleteById(detachmentId);
        return detachmentRepository.findById(armyId).isPresent() ? detachmentRepository.findAllByArmyId(armyId) : new ArrayList<>();
    }

}
