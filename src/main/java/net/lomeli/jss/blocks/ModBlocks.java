package net.lomeli.jss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.jss.blocks.connected.BlockObsiSteel;
import net.lomeli.jss.blocks.fluids.BlockFluidObsiSteel;
import net.lomeli.jss.fluid.ModFluids;

public class ModBlocks {
    public static Block linedObsidiSteel, blockObsidiSteel, moltenObsidiSteel;

    public static void initBlocks() {
        linedObsidiSteel = new BlockObsiSteel();
        blockObsidiSteel = new BlockJSS(Material.iron).setBlockName("obsidianSteelBlock").setBlockTextureName("blockObsidiSteel");
        moltenObsidiSteel = new BlockFluidObsiSteel();

        GameRegistry.registerBlock(linedObsidiSteel, "lined_obsidisteel");
        GameRegistry.registerBlock(blockObsidiSteel, "block_obsidiSteel");
        GameRegistry.registerBlock(moltenObsidiSteel, "molten_obsidiSteel");

        OreDictionary.registerOre("blockObsidianSteel", blockObsidiSteel);

        ModFluids.obsidiSteel.setBlock(moltenObsidiSteel);
    }
}
