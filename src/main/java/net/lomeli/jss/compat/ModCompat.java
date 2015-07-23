package net.lomeli.jss.compat;

import cpw.mods.fml.common.Loader;

import net.lomeli.jss.lib.ModIDS;

public class ModCompat {
    public static void loadModCompat() {
        if (Loader.isModLoaded(ModIDS.EE3_ID))
            EE3Compat.loadEE3Compat();
        if (Loader.isModLoaded(ModIDS.BOTANIA_ID))
            BotaniaCompat.loadBotaniaCompat();
        if (Loader.isModLoaded(ModIDS.TiC_ID))
            TinkersCompat.loadTinkersCompat();
    }
}
