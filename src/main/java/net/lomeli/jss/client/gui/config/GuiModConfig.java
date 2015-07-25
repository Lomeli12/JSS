package net.lomeli.jss.client.gui.config;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.config.GuiConfig;

import net.lomeli.lomlib.lib.ModLibs;

import net.lomeli.jss.JSS;

public class GuiModConfig extends GuiConfig {
    public GuiModConfig(GuiScreen parent) {
        super(parent, new ConfigElement(JSS.modConfig.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), ModLibs.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(JSS.modConfig.getConfig().toString()));
    }
}
