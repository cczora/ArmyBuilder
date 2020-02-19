package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.DetachmentFieldsRepository;
import com.cczora.armybuilder.data.fields.UnitFieldsRepo;
import com.cczora.armybuilder.models.KeyValuePair;
import com.cczora.armybuilder.models.dto.DetachmentDTO;
import com.cczora.armybuilder.models.dto.UnitDTO;
import com.cczora.armybuilder.models.dto.UnitPatchRequestDTO;
import com.cczora.armybuilder.models.entity.*;
import com.cczora.armybuilder.models.mapping.DetachmentMapper;
import com.cczora.armybuilder.models.mapping.UnitMapper;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UnitServiceIT {

    @Mock
    private ArmyRepository armyRepoMock;
    @Mock
    private DetachmentService detachmentServiceMock;
    @Mock
    private DetachmentRepository detachmentRepoMock;
    @Mock
    private DetachmentMapper detachmentMapperMock;
    @Mock
    private DetachmentTypeRepository detachmentTypeRepoMock;
    @Mock
    private DetachmentFieldsRepository detachmentFieldsRepoMock;

    private UnitService service;
    private UnitRepository unitRepo;
    private Faker faker = new Faker();

    @Autowired
    public UnitServiceIT(UnitRepository unitRepo, UnitTypeRepository unitTypeRepo, UnitFieldsRepo unitFieldsRepo, UnitMapper unitMapper, DetachmentService detachmentService, FactionTypeRepository factionRepo) {
        this.unitRepo = unitRepo;
        MockitoAnnotations.initMocks(this);
        this.detachmentServiceMock = new DetachmentService(armyRepoMock, detachmentRepoMock, detachmentMapperMock,
                detachmentTypeRepoMock, detachmentFieldsRepoMock,
                factionRepo, unitRepo, unitTypeRepo);
        this.service = new UnitService(detachmentRepoMock, detachmentService, unitRepo, unitMapper, unitTypeRepo, unitFieldsRepo);
        when(armyRepoMock.save(any(Army.class))).thenReturn(Army.builder()
                .name(faker.lorem().word())
                .notes(faker.ancient().primordial())
                .id(TestConstants.armyId)
                .username(TestConstants.username)
                .factionTypeId(TestConstants.factionTypeId)
                .sizeClass("small")
                .build());
        when(detachmentRepoMock.findArmyIdForDetachment(any(UUID.class))).thenReturn(TestConstants.armyId);
        when(detachmentRepoMock.findById(any(UUID.class))).thenReturn(Optional.of(makeTestDetachment()));
        when(detachmentServiceMock.getDetachmentsByArmyId(any(UUID.class))).thenReturn(Collections.singletonList(makeTestDetachmentDTO()));
    }

    @Autowired



    @BeforeEach
    public void cleanup() {
        unitRepo.deleteAll();
    }



    @Test
    public void UnitCRUD() throws Exception {
        UnitDTO testUnit = makeTestUnitDTO();
        service.addUnit(testUnit, TestConstants.armyId);
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
        assertThrows(NotFoundException.class, () -> service.addUnit(unitDTO, TestConstants.armyId));
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
        UnitDTO testUnit = makeTestUnitDTO();
        service.addUnit(testUnit, TestConstants.armyId);
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
                .notes(faker.cat().name())
                .build();
    }

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

    private Detachment makeTestDetachment() {
        return Detachment.builder()
                .id(TestConstants.detachmentId)
                .armyId(TestConstants.armyId)
                .detachmentType(DetachmentType.builder()
                        .detachmentTypeId(TestConstants.detachmentTypeId)
                        .name(TestConstants.detachmentTypeName)
                        .commandPoints(3)
                        .build())
                .faction(FactionType.builder()
                        .factionTypeId(TestConstants.factionTypeId)
                        .name(TestConstants.factionTypeName)
                        .build())
                .name(faker.funnyName().name())
                .notes(faker.princessBride().quote())
                .build();
    }

    //endregion

}

