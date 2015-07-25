package net.lomeli.jss;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.lomlib.util.LogHelper;

import net.lomeli.jss.core.ModConfig;
import net.lomeli.jss.core.ModTab;
import net.lomeli.jss.core.Proxy;
import net.lomeli.jss.lib.ModIDS;

@Mod(modid = JSS.MOD_ID, name = JSS.NAME, version = JSS.VERSION, dependencies = JSS.DEPENDENCIES, acceptedMinecraftVersions = JSS.MC_VERSION, guiFactory = JSS.CONFIG_FACTORY)
public class JSS {
    public static final String MOD_ID = "jss";
    public static final String NAME = "Just Some Stuff";
    public static final int MAJOR = 1, MINOR = 0, REV = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REV;
    public static final String MC_VERSION = "1.7.10";
    public static final String DEPENDENCIES = "required-after:LomLib;after:" + ModIDS.BOTANIA_ID + ";after:" + ModIDS.EE3_ID + ";after:" + ModIDS.TiC_ID + ";";
    public static final String CONFIG_FACTORY = "net.lomeli.jss.client.gui.config.GuiModConfigFactory";

    public static CreativeTabs modTab = new ModTab();

    @SidedProxy(serverSide = "net.lomeli.jss.core.Proxy", clientSide = "net.lomeli.jss.client.ClientProxy")
    public static Proxy proxy;

    @Mod.Instance
    public static JSS modInstance;

    public static ModConfig modConfig;
    public static LogHelper logger = new LogHelper(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modConfig = new ModConfig(event.getSuggestedConfigurationFile());
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
