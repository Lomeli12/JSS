package net.lomeli.jss.client.handler;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.jss.JSS;

public class IconHandler {
    private static IconHandler INSTANCE;
    private List<String> registerList;
    private HashMap<String, IIcon> iconList;

    private IconHandler() {
        this.registerList = Lists.newArrayList();
        this.iconList = Maps.newHashMap();
    }

    public void initEvent() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addIconToStich(String icon) {
        if (!Strings.isNullOrEmpty(icon))
            this.registerList.add(icon);
    }

    private void registerIcon(TextureMap map, String icon) {
        if (!Strings.isNullOrEmpty(icon) && !iconList.containsKey(icon))
            iconList.put(icon, map.registerIcon(JSS.MOD_ID + ":" + icon));
    }

    public IIcon getIcon(String iconName) {
        return iconList.containsKey(iconName) ? iconList.get(iconName) : null;
    }

    @SubscribeEvent
    public void preStitchEvent(TextureStitchEvent.Pre event) {
        if (registerList.isEmpty())
            return;
        if (event.map.getTextureType() == 0) {
            for (String iconName : registerList) {
                registerIcon(event.map, iconName);
            }
        }
    }

    public static IconHandler getIconHandler() {
        if (INSTANCE == null)
            INSTANCE = new IconHandler();
        return INSTANCE;
    }
}
