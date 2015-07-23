package net.lomeli.jss.client;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.RenderingRegistry;

import net.lomeli.jss.client.handler.EventHandlerClient;
import net.lomeli.jss.client.handler.FluidIconHandler;
import net.lomeli.jss.client.handler.IconHandler;
import net.lomeli.jss.client.handler.ItemTooltipHandler;
import net.lomeli.jss.client.render.blocks.RenderConnectedBlock;
import net.lomeli.jss.client.render.entity.RenderUnborn;
import net.lomeli.jss.core.Proxy;
import net.lomeli.jss.entities.EntityUnborn;
import net.lomeli.jss.lib.ModRender;

public class ClientProxy extends Proxy {
    @Override
    public void preInit() {
        super.preInit();
        IconHandler.getIconHandler().addIconToStich("circle");

        IconHandler.getIconHandler().initEvent();
    }

    @Override
    public void init() {
        super.init();
        FluidIconHandler.assignFluidTextures();

        ModRender.ctRenderID = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());

        RenderingRegistry.registerBlockHandler(new RenderConnectedBlock(ModRender.ctRenderID));
        RenderingRegistry.registerEntityRenderingHandler(EntityUnborn.class, new RenderUnborn(IconHandler.getIconHandler().getIcon("circle")));
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}
