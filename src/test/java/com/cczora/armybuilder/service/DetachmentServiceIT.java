package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.DetachmentTypeRepository;
import com.cczora.armybuilder.data.FactionTypeRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.fields.DetachmentFieldsRepository;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DetachmentServiceIT {

    private DetachmentRepository detachmentRepo;
    private DetachmentService detachmentService;
    private Faker faker = new Faker();

    @Autowired
    public DetachmentServiceIT(DetachmentRepository detachmentRepo, DetachmentTypeRepository detachmentTypeRepo, DetachmentFieldsRepository detachmentFieldsRepo, FactionTypeRepository factionTypeRepo, UnitRepository unitRepo, DetachmentService detachmentService) {
        this.detachmentRepo = detachmentRepo;
        this.detachmentService = new DetachmentService(detachmentRepo, detachmentTypeRepo, detachmentFieldsRepo, factionTypeRepo, unitRepo);
    }

    @BeforeEach
    public void cleanup() {
        detachmentRepo.deleteAll();
    }

    @Test
    public void DetachmentCRUD() throws Exception {
        DetachmentDTO dto = makeTestDetachmentDTO();

    }

    //region private methods

    private DetachmentDTO makeTestDetachmentDTO() {
        return DetachmentDTO.builder()
                .armyId(TestConstants.armyId)
                .detachmentType("Air Wing")
                .factionType("Necrons")
                .name(faker.funnyName().name())
                .notes(faker.princessBride().quote())
                .build();
    }

    //endregion

}
