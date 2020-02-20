package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.ArmyRepository;
import com.cczora.armybuilder.data.DetachmentRepository;
import com.cczora.armybuilder.data.UnitRepository;
import com.cczora.armybuilder.data.UnitTypeRepository;
import com.cczora.armybuilder.data.fields.UnitFieldsRepo;
import com.cczora.armybuilder.models.KeyValuePair;
import com.cczora.armybuilder.models.dto.UnitDTO;
import com.cczora.armybuilder.models.dto.UnitPatchRequestDTO;
import com.cczora.armybuilder.models.mapping.UnitMapper;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UnitServiceIT {

    private ArmyRepository armyRepo;
    private DetachmentRepository detachmentRepo;
    private UnitService service;
    private Faker faker = new Faker();

    @Autowired
    public UnitServiceIT(ArmyRepository armyRepo, DetachmentService detachmentService, DetachmentRepository detachmentRepo, UnitRepository unitRepo, UnitTypeRepository unitTypeRepo, UnitFieldsRepo unitFieldsRepo, UnitMapper unitMapper) {
        this.armyRepo = armyRepo;
        this.detachmentRepo = detachmentRepo;
        MockitoAnnotations.initMocks(this);
        this.service = new UnitService(detachmentRepo, detachmentService, unitRepo, unitMapper, unitTypeRepo, unitFieldsRepo);
    }

    @BeforeEach
    public void setup() {
        armyRepo.deleteAll();
        detachmentRepo.deleteAll();
        armyRepo.save(TestConstants.testArmy);
        detachmentRepo.save(TestConstants.testDetachment);
    }

    @Test
    public void UnitCRUD() throws Exception {
        UnitDTO testUnit = makeTestUnitDTO();
        testUnit = service.addUnit(testUnit, TestConstants.detachmentId);
        List<UnitDTO> fromRepo = service.getUnitsForDetachment(TestConstants.detachmentId);
        assertEquals(1, fromRepo.size());
        assertEquals(testUnit, fromRepo.get(0));

        service.editUnit(UnitPatchRequestDTO.builder()
                .unitId(testUnit.getId())
                .updates(Lists.newArrayList(
                        KeyValuePair.builder()
                                .key("unit_type_id")
                                .value(TestConstants.updatedUnitTypeName)
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

        fromRepo = service.getUnitsForDetachment(TestConstants.detachmentId);
        assertEquals(1, fromRepo.size());
        assertEquals(TestConstants.updatedUnitTypeName, fromRepo.get(0).getUnitType());
        assertEquals(TestConstants.updatedName, fromRepo.get(0).getName());
        assertEquals(TestConstants.updatedNotes, fromRepo.get(0).getNotes());

        service.deleteUnitById(testUnit.getId(), TestConstants.detachmentId);
        fromRepo = service.getUnitsForDetachment(TestConstants.detachmentId);
        assertEquals(0, fromRepo.size());
    }

    @Test
    public void addUnit_invalidUnitType() {
        UnitDTO unitDTO = makeTestUnitDTO();
        unitDTO.setUnitType("Invalid Unit Type");
        assertThrows(NotFoundException.class, () -> service.addUnit(unitDTO, TestConstants.detachmentId));
    }

    @Test
    public void editUnit_invalidPatchField() {
        UnitPatchRequestDTO dto = UnitPatchRequestDTO.builder()
                .unitId(TestConstants.unitId)
                .updates(Collections.singletonList(KeyValuePair.builder()
                        .key("Invalid Field")
                        .value("Invalid Value")
                        .build()))
                .build();
        assertThrows(NoSuchFieldException.class, () -> service.editUnit(dto));
    }

    @Test
    public void editUnit_validPatchField_invalidValue() {
        service.addUnit(makeTestUnitDTO(), TestConstants.detachmentId);
        UnitPatchRequestDTO patchRequestDTO = UnitPatchRequestDTO.builder()
                .unitId(TestConstants.unitId)
                .updates(Collections.singletonList(KeyValuePair.builder()
                        .key("unit_type_id")
                        .value("Invalid Value")
                        .build()))
                .build();
        assertThrows(NotFoundException.class, () -> service.editUnit(patchRequestDTO));
    }

    //region private methods

    private UnitDTO makeTestUnitDTO() {
        return UnitDTO.builder()
                .id(TestConstants.unitId)
                .unitType(TestConstants.unitTypeName)
                .name(faker.lorem().word())
                .detachmentId(TestConstants.detachmentId)
                .notes(faker.chuckNorris().fact())
                .build();
    }

    //endregion

}

