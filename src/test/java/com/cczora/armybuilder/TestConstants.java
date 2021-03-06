package com.cczora.armybuilder;

import com.cczora.armybuilder.models.entity.Army;
import com.cczora.armybuilder.models.entity.Detachment;
import com.cczora.armybuilder.models.entity.DetachmentType;
import com.cczora.armybuilder.models.entity.FactionType;

import java.util.UUID;

public class TestConstants {

    public static final String username = "Test User";

    public static final UUID armyId = UUID.fromString("fadd8a45-a7ef-4188-987d-04d97926e8db");
    public final static UUID factionTypeId = UUID.fromString("205fe7d0-ed75-4d18-a2f5-7d5324e27f0e");
    public final static String factionTypeName = "Necrons";
    public final static String sizeClass = "small";

    public final static String updatedName = "UpdatedName";
    public final static String updatedFaction = "Tau Empire";
    public final static String updatedSize = "medium";
    public final static String updatedNotes = "updated notes";
    public final static String updatedUnitTypeName = "Fast Attack";

    public final static UUID detachmentId = UUID.fromString("25da53b3-476d-4d36-9b6f-66c69181a250");
    public final static UUID detachmentTypeId = UUID.fromString("65d6447a-9057-4187-b26b-a38cb018901d");
    public final static String detachmentTypeName = "Air Wing";
    public final static String updatedDetachmentTypeName = "Supreme Command";

    public final static UUID unitId = UUID.fromString("fa9029f8-414f-49bb-a279-212b3f4c536a");
    public final static UUID unitTypeId = UUID.fromString("853dff0e-0a48-4081-a11f-5d1e7dd0cbc3");
    public final static String unitTypeName = "Flyer";


    public final static Army testArmy = Army.builder()
            .id(armyId)
            .faction(FactionType.builder()
                    .name(factionTypeName)
                    .factionTypeId(factionTypeId)
                    .build())
            .factionTypeId(factionTypeId)
            .name("Test Army")
            .sizeClass("Medium")
            .notes("Test Notes")
            .username(username)
            .build();

    public final static Detachment testDetachment = Detachment.builder()
            .id(detachmentId)
            .armyId(armyId)
            .detachmentType(DetachmentType.builder()
                    .detachmentTypeId(detachmentTypeId)
                    .name(detachmentTypeName)
                    .commandPoints(3)
                    .build())
            .faction(FactionType.builder()
                    .factionTypeId(factionTypeId)
                    .name(factionTypeName)
                    .build())
            .name("Test Detachment")
            .notes("Test Notes")
            .build();

}
