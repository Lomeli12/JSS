package net.lomeli.jss.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.jss.items.weapons.ItemBaseSword;
import net.lomeli.jss.items.weapons.ItemSoulSword;

public class ModItems {
    public static Item.ToolMaterial obsidiSteelMat;
    public static Item resources, obsidiSteelSword, soulSword, emptyHeart;

    public static void initItems() {
        obsidiSteelMat = EnumHelper.addToolMaterial("obsidiSteel", 3, 630, 7f, 2.5f, 12);

        resources = new ItemResources();
        obsidiSteelSword = new ItemBaseSword(obsidiSteelMat).setTextureName("obsidiSteelSword").setUnlocalizedName("obsidiSteelSword");
        soulSword = new ItemSoulSword();
        emptyHeart = new ItemEmptyHeart();

        GameRegistry.registerItem(resources, "resources");
        GameRegistry.registerItem(obsidiSteelSword, "obsidiSteelSword");
        GameRegistry.registerItem(soulSword, "soulSword");
        GameRegistry.registerItem(emptyHeart, "emptyHeart");

        OreDictionary.registerOre("ingotObsidianSteel", new ItemStack(resources, 1, 2));
        OreDictionary.registerOre("nuggetObsidianSteel", new ItemStack(resources, 1, 3));
        OreDictionary.registerOre("stickIron", new ItemStack(resources, 1, 4));
        OreDictionary.registerOre("dyeClearInk", new ItemStack(resources, 1, 0));
    }
}
