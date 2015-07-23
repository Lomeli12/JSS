package net.lomeli.jss.client.handler;

import net.lomeli.jss.blocks.ModBlocks;
import net.lomeli.jss.fluid.ModFluids;

public class FluidIconHandler {
    public static void assignFluidTextures() {
        ModFluids.obsidiSteel.setIcons(ModBlocks.moltenObsidiSteel.getIcon(1, 0), ModBlocks.moltenObsidiSteel.getIcon(2, 0));
    }
}
