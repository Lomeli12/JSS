package net.lomeli.jss.core;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;

import net.lomeli.jss.JSS;
import net.lomeli.jss.blocks.ModBlocks;
import net.lomeli.jss.compat.ModCompat;
import net.lomeli.jss.core.handler.EntityEventHandler;
import net.lomeli.jss.core.handler.PlayerEventHandler;
import net.lomeli.jss.core.recipes.ModRecipes;
import net.lomeli.jss.entities.ModEntities;
import net.lomeli.jss.fluid.ModFluids;
import net.lomeli.jss.items.ModItems;

public class Proxy {
    public void preInit() {
        JSS.modConfig.loadConfig();
        ModFluids.initFluids();
        ModItems.initItems();
        ModBlocks.initBlocks();
        ModEntities.initEntities();
    }

    public void init() {
        JSS.modConfig.initEvent();
        PlayerEventHandler playerEventHandler = new PlayerEventHandler();

        MinecraftForge.EVENT_BUS.register(playerEventHandler);
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        FMLCommonHandler.instance().bus().register(playerEventHandler);

        ModRecipes.initRecipes();
    }

    public void postInit() {
        ModCompat.loadModCompat();
    }
}
