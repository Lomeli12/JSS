package net.lomeli.jss.compat;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmeltery;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.fluids.FluidStack;

import net.lomeli.jss.blocks.ModBlocks;
import net.lomeli.jss.fluid.ModFluids;
import net.lomeli.jss.items.ModItems;
import net.lomeli.jss.lib.ModVars;

public class TinkersCompat {

    public static void loadTinkersCompat() {
        ItemStack ingotcast = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
        ItemStack nuggetCast = new ItemStack(TinkerSmeltery.metalPattern, 1, 27);

        FluidType.registerFluidType("ObsidianSteel", ModBlocks.blockObsidiSteel, 0, 700, ModFluids.obsidiSteel, true);

        Smeltery.addMelting(ModBlocks.blockObsidiSteel, 0, 700, new FluidStack(ModFluids.obsidiSteel, 1296));
        Smeltery.addMelting(FluidType.getFluidType("ObsidianSteel"), new ItemStack(ModItems.resources, 1, 2), 600, 144);
        Smeltery.addMelting(FluidType.getFluidType("ObsidianSteel"), new ItemStack(ModItems.resources, 1, 3), 67, 16);

        Smeltery.addAlloyMixing(new FluidStack(ModFluids.obsidiSteel, 576), new FluidStack(TinkerSmeltery.moltenIronFluid, 144), new FluidStack(TinkerSmeltery.moltenObsidianFluid, 1152));

        ToolMaterial obsidiSteelMaterial = new ToolMaterial("obsidianSteel", "material.jss.obsidianSteel", 2, 425, 650, 3, 1.65f, 2, 0, EnumChatFormatting.WHITE.toString(), 0xB09CB8);
        TConstructRegistry.addtoolMaterial(ModVars.TiCMaterialID, obsidiSteelMaterial);
        TConstructRegistry.addDefaultShardMaterial(ModVars.TiCMaterialID);
        TConstructRegistry.addDefaultToolPartMaterial(ModVars.TiCMaterialID);

        TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ModBlocks.blockObsidiSteel), new FluidStack(ModFluids.obsidiSteel, 1296), null, true, 100);
        TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(ModItems.resources, 1, 2), new FluidStack(ModFluids.obsidiSteel, 144), ingotcast, 50);
        TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(ModItems.resources, 1, 3), new FluidStack(ModFluids.obsidiSteel, 16), nuggetCast, 30);
    }
}
