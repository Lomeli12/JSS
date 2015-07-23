package net.lomeli.jss.core;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import net.lomeli.lomlib.util.SimpleConfig;

import net.lomeli.jss.JSS;
import net.lomeli.jss.lib.ModVars;

public class ModConfig extends SimpleConfig {

    public ModConfig(File file) {
        super(JSS.MOD_ID, new Configuration(file));
    }

    @Override
    public void loadConfig() {
        String cat = Configuration.CATEGORY_GENERAL;
        ModVars.TiCMaterialID = config.getInt("tinkerCompatID", cat, 801, 0, Integer.MAX_VALUE, "");

        if (this.config.hasChanged())
            this.config.save();
    }
}
