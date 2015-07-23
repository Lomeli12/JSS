package net.lomeli.jss.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
    public static Fluid obsidiSteel;

    public static void initFluids() {
        obsidiSteel = new Fluid("molten.jss.obsidianSteel").setDensity(3000).setViscosity(6000).setTemperature(1300).setLuminosity(12);

        FluidRegistry.registerFluid(obsidiSteel);
    }
}
