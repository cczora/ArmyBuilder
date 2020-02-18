package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.data.fields.ArmyFieldRepository;
import com.cczora.armybuilder.models.KeyValuePair;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.entity.Account;
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

import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class ArmyServiceIT {

    private UserRepository userRepo;
    private ArmyRepository armyRepo;
    private ArmyService service;
    private Faker faker = new Faker();

    @Autowired
    public ArmyServiceIT(UserRepository userRepo1, UserRepository userRepo,
                         ArmyRepository armyRepo,
                         ArmyMapper mapper,
                         ArmyFieldRepository armyFieldsRepo,
                         FactionTypeRepository factionTypeRepo,
                         DetachmentRepository detachmentRepo,
                         UnitRepository unitRepo) {
        this.userRepo = userRepo1;
        this.armyRepo = armyRepo;
        this.service = new ArmyService(userRepo, armyRepo, mapper, armyFieldsRepo, factionTypeRepo, detachmentRepo, unitRepo);
    }

    @BeforeEach
    public void cleanup() {
        armyRepo.deleteAll();
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

    //region private methods

    private ArmyDTO makeTestArmyDTO() {
        return ArmyDTO.builder()
                .name(faker.lorem().words(1).get(0))
                .sizeClass("small")
                .factionName("Necrons")
                .notes(faker.zelda().character())
                .build();
    }

    //endregion

}
