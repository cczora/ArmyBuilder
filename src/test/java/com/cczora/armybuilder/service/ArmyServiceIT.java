package com.cczora.armybuilder.service;

import com.cczora.armybuilder.TestConstants;
import com.cczora.armybuilder.data.*;
import com.cczora.armybuilder.models.dto.ArmyDTO;
import com.cczora.armybuilder.models.dto.ArmyPatchRequestDTO;
import com.cczora.armybuilder.models.dto.KeyValuePair;
import com.cczora.armybuilder.models.entity.Account;
import com.cczora.armybuilder.models.entity.Army;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ArmyServiceIT {

    private UserRepository userRepo;
    private ArmyRepository armyRepo;
    private ArmyService service;
    private Faker faker = new Faker();

    @Autowired
    public ArmyServiceIT(UserRepository userRepo,
                         ArmyRepository armyRepo,
                         ArmyFieldRepository armyFieldsRepo,
                         FactionTypeRepository factionTypeRepo,
                         DetachmentRepository detachmentRepo,
                         UnitRepository unitRepo) {
        this.userRepo = userRepo;
        this.armyRepo = armyRepo;
        this.service = new ArmyService(userRepo, armyRepo, armyFieldsRepo, factionTypeRepo, detachmentRepo, unitRepo);
    }

    @BeforeEach
    public void cleanup() {
        armyRepo.deleteAll();
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

    //region private methods

    private ArmyDTO makeTestArmyDTO() {
        return ArmyDTO.builder()
                .name(faker.lorem().words(2).toString())
                .sizeClass("small")
                .factionName("Necrons")
                .notes(faker.zelda().character())
                .build();
    }

    //endregion

}
