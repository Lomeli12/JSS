package net.lomeli.jss.entities;

import net.minecraft.entity.Entity;

import cpw.mods.fml.common.registry.EntityRegistry;

import net.lomeli.lomlib.util.entity.EntityUtil;
import net.lomeli.lomlib.util.entity.SimpleEggInfo;

import net.lomeli.jss.JSS;

public class ModEntities {
    private static int entityIDs;

    public static void initEntities() {
        registerEntity(EntityUnborn.class, "unborn", -1, -1);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        EntityRegistry.registerModEntity(entityClass, entityName, getUniqueID(), JSS.modInstance, 64, 3, true);
        if (bkEggColor == -1 || fgEggColor == -1)
            EntityUtil.addEgg(new SimpleEggInfo(entityClass, bkEggColor, fgEggColor, JSS.MOD_ID + "." + entityName));
    }

    private static int getUniqueID() {
        int i = entityIDs;
        entityIDs++;
        return i;
    }
}
