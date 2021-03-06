package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.DetachmentFieldsRepository;
import com.cczora.armybuilder.models.KeyValuePair;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.DetachmentPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.entity.UnitType;
import com.cczora.armybuilder.models.mapping.DetachmentMapper;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DetachmentServiceIT {

    private ArmyRepository armyRepo;
    private DetachmentRepository detachmentRepo;
    private UnitRepository unitRepo;
    private UnitTypeRepository unitTypeRepo;
    private DetachmentService service;
    private Faker faker = new Faker();

    @Autowired
    public DetachmentServiceIT(ArmyRepository armyRepo, DetachmentRepository detachmentRepo, DetachmentMapper mapper, DetachmentTypeRepository detachmentTypeRepo, DetachmentFieldsRepository detachmentFieldsRepo, FactionTypeRepository factionTypeRepo, UnitRepository unitRepo, UnitTypeRepository unitTypeRepo) {
        this.armyRepo = armyRepo;
        this.detachmentRepo = detachmentRepo;
        this.unitRepo = unitRepo;
        this.unitTypeRepo = unitTypeRepo;
        this.service = new DetachmentService(armyRepo, detachmentRepo, mapper, detachmentTypeRepo, detachmentFieldsRepo, factionTypeRepo, unitRepo, this.unitTypeRepo);
    }

    @BeforeEach
    public void cleanupAddTestArmy() {
        if(armyRepo.findAll().size() > 0) {
            armyRepo.deleteAll();
        }
        addTestArmy();
    }



    @Test
    public void DetachmentCRUD() throws Exception {
        DetachmentDTO testDetachment = makeTestDetachmentDTO();
        service.addDetachment(testDetachment);
        List<DetachmentDTO> fromRepo = service.getDetachmentsByArmyId(TestConstants.armyId);
        assertEquals(1, fromRepo.size());
        assertEquals(testDetachment, fromRepo.get(0));

        service.editDetachment(DetachmentPatchRequestDTO.builder()
                .detachmentId(testDetachment.getDetachmentId())
                .updates(Lists.newArrayList(
                        KeyValuePair.builder()
                                .key("detachment_type_id")
                                .value(TestConstants.updatedDetachmentTypeName)
                                .build(),
                        KeyValuePair.builder()
                                .key("faction_type_id")
                                .value(TestConstants.updatedFaction)
                                .build(),
                        KeyValuePair.builder()
                                .key("name")
                                .value(TestConstants.updatedName)
                                .build(),
                        KeyValuePair.builder()
                                .key("notes")
                                .value(TestConstants.updatedNotes)
                                .build()))
                .build());

        fromRepo = service.getDetachmentsByArmyId(TestConstants.armyId);
        assertEquals(1, fromRepo.size());
        assertEquals(TestConstants.updatedDetachmentTypeName, fromRepo.get(0).getDetachmentType());
        assertEquals(TestConstants.updatedFaction, fromRepo.get(0).getFactionName());
        assertEquals(TestConstants.updatedName, fromRepo.get(0).getName());
        assertEquals(TestConstants.updatedNotes, fromRepo.get(0).getNotes());

        service.deleteDetachment(testDetachment.getDetachmentId());
        fromRepo = service.getDetachmentsByArmyId(TestConstants.armyId);
        assertEquals(0, fromRepo.size());
    }

    @Test
    public void deleteDetachment_deleteFlagSetToFalse_emptyUnits() {
        service.addDetachment(makeTestDetachmentDTO());
        Unit unit = makeTestUnit();
        unitRepo.save(unit);
        Optional<Detachment> detachment = Optional.ofNullable(service.getFullDetachment(TestConstants.detachmentId));
        assertTrue(detachment.isPresent());
        List<Unit> unitsForDetach = detachment.get().getUnits();
        assertEquals(unitsForDetach.size(), 1);
        assertEquals(unitsForDetach.get(0), unit);

        service.deleteUnitsForDetachment(TestConstants.detachmentId);

        detachment = Optional.ofNullable(service.getFullDetachment(TestConstants.detachmentId));
        assertTrue(detachment.isPresent());
        unitsForDetach = detachment.get().getUnits();
        assertEquals(0, unitsForDetach.size());
    }

    @Test
    public void addDetachment_invalidFaction() {
        DetachmentDTO detachmentDTO = makeTestDetachmentDTO();
        detachmentDTO.setFactionName("Invalid Faction");
        assertThrows(NotFoundException.class, () -> service.addDetachment(detachmentDTO));
    }

    @Test
    public void editDetachment_invalidPatchField() {
        DetachmentPatchRequestDTO dto = DetachmentPatchRequestDTO.builder()
                .detachmentId(TestConstants.detachmentId)
                .updates(Collections.singletonList(KeyValuePair.builder()
                        .key("Invalid Field")
                        .value("Invalid Value")
                        .build()))
                .build();
        assertThrows(NoSuchFieldException.class, () -> service.editDetachment(dto));
    }

    @Test
    public void editDetachment_validPatchField_invalidValue() {
        DetachmentDTO testDetachment = makeTestDetachmentDTO();
        service.addDetachment(testDetachment);
        DetachmentPatchRequestDTO patchRequestDTO = DetachmentPatchRequestDTO.builder()
                .detachmentId(TestConstants.detachmentId)
                .updates(Collections.singletonList(KeyValuePair.builder()
                        .key("faction_type_id")
                        .value("Invalid Value")
                        .build()))
                .build();
        assertThrows(NotFoundException.class, () -> service.editDetachment(patchRequestDTO));
    }

    //region private methods

    private DetachmentDTO makeTestDetachmentDTO() {
        return DetachmentDTO.builder()
                .detachmentId(TestConstants.detachmentId)
                .armyId(TestConstants.armyId)
                .detachmentType(TestConstants.detachmentTypeName)
                .factionName(TestConstants.factionTypeName)
                .name(faker.funnyName().name())
                .notes(faker.princessBride().quote())
                .build();
    }

    private void addTestArmy() {
        armyRepo.save(Army.builder()
                .name(faker.lorem().word())
                .notes(faker.ancient().primordial())
                .id(TestConstants.armyId)
                .username(TestConstants.username)
                .factionTypeId(TestConstants.factionTypeId)
                .sizeClass("small")
                .build());
    }

    private Unit makeTestUnit() {
        return Unit.builder()
                .id(TestConstants.unitId)
                .unitType(UnitType.builder()
                        .unit_type_id(TestConstants.unitTypeId)
                        .name(TestConstants.unitTypeName)
                        .powerPoints(11)
                        .build())
                .unitTypeId(TestConstants.unitTypeId)
                .name(faker.lorem().word())
                .detachmentId(TestConstants.detachmentId)
                .build();
    }

    //endregion

}
