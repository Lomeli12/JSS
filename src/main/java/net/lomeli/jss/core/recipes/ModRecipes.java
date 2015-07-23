package net.lomeli.jss.core.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.lomlib.core.recipes.ShapedFluidRecipe;
import net.lomeli.lomlib.core.recipes.ShapelessFluidRecipe;

import net.lomeli.jss.blocks.ModBlocks;
import net.lomeli.jss.items.ModItems;

public class ModRecipes {

    public static void initRecipes() {
        addShapeless(new ItemStack(ModItems.resources, 1, 0), "paneGlass", "dyeBlack");
        addShapeless(new ItemStack(ModItems.resources, 2, 1), "dyeClearInk", Items.glass_bottle, Items.glass_bottle);
        addShapeless(new ItemStack(ModItems.resources, 9, 3), "ingotObsidianSteel");
        addShapeless(new ItemStack(ModItems.resources, 9, 2), "blockObsidianSteel");

        addShaped(new ItemStack(ModItems.resources, 1, 2), "III", "III", "III", 'I', "nuggetObsidianSteel");
        addShaped(new ItemStack(ModBlocks.blockObsidiSteel), "III", "III", "III", 'I', "ingotObsidianSteel");
        addShaped(new ItemStack(ModItems.resources, 2, 4), " I", "I ", 'I', "ingotIron");
        addShaped(new ItemStack(ModItems.resources, 1, 5), "  I", "WS ", "SW ", 'I', "ingotIron", 'S', "stickIron", 'W', Blocks.wool);
        addShaped(new ItemStack(ModItems.resources, 4, 2), "OOO", "OIO", "OOO", 'O', Blocks.obsidian, 'I', "ingotIron");
        addShaped(new ItemStack(ModBlocks.linedObsidiSteel, 8), "OIO", "IOI", "OIO", 'I', "blockObsidianSteel", 'O', Blocks.obsidian);
        addShaped(new ItemStack(ModItems.obsidiSteelSword), "O", "O", "S", 'S', "stickWood", 'O', "ingotObsidianSteel");
        addShaped(new ItemStack(ModItems.soulSword), "SNS", "SNS", "DED", 'S', new ItemStack(ModItems.resources, 1, 8), 'N', Items.nether_star, 'D', "blockDiamond", 'E', "gemEmerald");

        GameRegistry.addRecipe(new InkRecipe());
    }

    private static void addShaped(ItemStack output, Object... inputs) {
        GameRegistry.addRecipe(new ShapedFluidRecipe(output, inputs));
    }

    private static void addShapeless(ItemStack output, Object... inputs) {
        GameRegistry.addRecipe(new ShapelessFluidRecipe(output, inputs));
    }
}
