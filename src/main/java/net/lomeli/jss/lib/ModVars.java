package net.lomeli.jss.lib;

import java.util.UUID;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.common.util.EnumHelper;

public class ModVars {
    public static final String HEALTH_ATTRIBUTE_ID = "JSS Health Modifier";
    public static final UUID HEALTH_ATTRIB_UUID = UUID.fromString("449b774c-30a4-11e5-a151-fab1acadab1a"); //I really wanted the end bit to be "abracadabra"
    public static int TiCMaterialID = 80148077;
    public static EnumRarity Otherworldly = EnumHelper.addRarity("OTHER_WORDLY", EnumChatFormatting.GOLD, "Other-worldly");
}
