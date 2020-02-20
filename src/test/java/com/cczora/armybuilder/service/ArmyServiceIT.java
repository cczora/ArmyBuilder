package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.ArmyFieldRepository;
import com.cczora.armybuilder.models.KeyValuePair;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Unit;
import com.cczora.armybuilder.models.entity.UnitType;
import com.cczora.armybuilder.models.mapping.ArmyMapper;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class ArmyServiceIT {

    private UserRepository userRepo;
    private ArmyRepository armyRepo;
    private DetachmentRepository detachRepo;
    private UnitRepository unitRepo;
    private ArmyService service;
    private Faker faker = new Faker();

    @Autowired
    public ArmyServiceIT(UserRepository userRepo1, UserRepository userRepo,
                         ArmyRepository armyRepo,
                         DetachmentRepository detachRepo, UnitRepository unitRepo1, ArmyMapper mapper,
                         ArmyFieldRepository armyFieldsRepo,
                         FactionTypeRepository factionTypeRepo,
                         DetachmentRepository detachmentRepo,
                         UnitRepository unitRepo) {
        this.userRepo = userRepo1;
        this.armyRepo = armyRepo;
        this.detachRepo = detachRepo;
        this.unitRepo = unitRepo1;
        this.service = new ArmyService(userRepo, armyRepo, mapper, armyFieldsRepo, factionTypeRepo, detachmentRepo, unitRepo);
    }

    @BeforeEach
    public void cleanup() {
        if(armyRepo.findAll().size() > 0) {
            armyRepo.deleteAll();
        }
        userRepo.save(Account.builder()
                .username("Test User")
                .password("password")
                .profilePicUrl("http://www.google.com")
                .build());
    }

    @Test
    public void armyCRUD() throws Exception {
        ArmyDTO testArmy = makeTestArmyDTO();
        testArmy = service.addArmy(testArmy, TestConstants.username);
        List<ArmyDTO> fromRepo = service.getArmiesByUsername(TestConstants.username);
        assertEquals(1, fromRepo.size());
        assertEquals(testArmy, fromRepo.get(0));

        service.editArmy(ArmyPatchRequestDTO.builder()
                .armyId(testArmy.getArmyId())
                .updates(Lists.newArrayList(KeyValuePair.builder()
                                .key("name")
                                .value(TestConstants.updatedName)
                                .build(),
                        KeyValuePair.builder()
                                .key("size")
                                .value(TestConstants.updatedSize)
                                .build(),
                        KeyValuePair.builder()
                                .key("faction_type_id")
                                .value(TestConstants.updatedFaction)
                                .build()))
                .build());

        fromRepo = service.getArmiesByUsername(TestConstants.username);
        assertEquals(1, fromRepo.size());
        assertEquals(TestConstants.updatedName, fromRepo.get(0).getName());
        assertEquals(TestConstants.updatedSize, fromRepo.get(0).getSizeClass());
        assertEquals(TestConstants.updatedFaction, fromRepo.get(0).getFactionName());

        service.deleteArmyById(testArmy.getArmyId());
        fromRepo = service.getArmiesByUsername(TestConstants.username);
        assertEquals(0, fromRepo.size());
    }

    @Test
    public void addArmy_invalidFaction() {
        ArmyDTO army = makeTestArmyDTO();
        army.setFactionName("Invalid Faction");
        assertThrows(NotFoundException.class, () -> service.addArmy(army, TestConstants.username));
    }

    @Test
    public void editArmy_invalidPatchField() {
        ArmyPatchRequestDTO dto = ArmyPatchRequestDTO.builder()
                .armyId(TestConstants.armyId)
                .updates(Collections.singletonList(KeyValuePair.builder()
                        .key("Invalid Field")
                        .value("Invalid Value")
                        .build()))
                .build();
        assertThrows(NoSuchFieldException.class, () -> service.editArmy(dto));
    }

    @Test
    public void editArmy_validPatchField_invalidValue() {
        ArmyDTO testArmy = makeTestArmyDTO();
        testArmy.setArmyId(TestConstants.armyId);
        service.addArmy(testArmy, TestConstants.username);
        ArmyPatchRequestDTO patchRequestDTO = ArmyPatchRequestDTO.builder()
                .armyId(TestConstants.armyId)
                .updates(Collections.singletonList(KeyValuePair.builder()
                        .key("faction_type_id")
                        .value("Invalid Value")
                        .build()))
                .build();
        assertThrows(NotFoundException.class, () -> service.editArmy(patchRequestDTO));
    }

    @Test
    public void deleteArmy_withDetachmentsAndUnits() {
        addTestWetArmy();
        service.deleteArmyById(TestConstants.armyId);
        assertEquals(service.getArmiesByUsername(TestConstants.username).size(), 0);
        assertEquals(detachRepo.findAllByArmyId(TestConstants.armyId).size(), 0);
        assertEquals(unitRepo.findAllByDetachmentId(TestConstants.detachmentId).size(), 0);
    }

    //region private methods

    private void addTestWetArmy() {
        armyRepo.save(TestConstants.testArmy);
        assertTrue(armyRepo.findById(TestConstants.armyId).isPresent());
        detachRepo.save(TestConstants.testDetachment);
        assertEquals(1, detachRepo.findAllByArmyId(TestConstants.armyId).size());
        unitRepo.save(makeTestUnit());
        assertEquals(1, unitRepo.findAllByDetachmentId(TestConstants.detachmentId).size());
    }

    private ArmyDTO makeTestArmyDTO() {
        return ArmyDTO.builder()
                .armyId(TestConstants.armyId)
                .name(faker.lorem().word())
                .sizeClass(TestConstants.sizeClass)
                .factionName(TestConstants.factionTypeName)
                .notes(faker.zelda().character())
                .build();
    }

    private Unit makeTestUnit() {
        return Unit.builder()
                .detachmentId(TestConstants.detachmentId)
                .id(TestConstants.unitId)
                .unitType(UnitType.builder()
                        .unit_type_id(TestConstants.unitTypeId)
                        .name(TestConstants.unitTypeName)
                        .build())
                .unitTypeId(TestConstants.unitTypeId)
                .name(faker.lorem().word())
                .build();
    }

    //endregion

}
