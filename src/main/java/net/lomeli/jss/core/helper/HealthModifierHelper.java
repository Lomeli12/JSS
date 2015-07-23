package net.lomeli.jss.core.helper;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.jss.lib.ModNBT;
import net.lomeli.jss.lib.ModVars;

public class HealthModifierHelper {
    public static boolean hasModifier(EntityPlayer player) {
        if (player != null && !EntityUtil.isFakePlayer(player))
            return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey(ModNBT.HEART_COUNT, 3);
        return false;
    }

    public static void markForLoss(EntityPlayer player) {
        if (heartCount(player) > -10)
            player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean(ModNBT.HEART_LOSS, true);
    }

    public static void removeHeartLoss(EntityPlayer player) {
        if (markedForLoss(player))
            player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).removeTag(ModNBT.HEART_LOSS);
    }

    public static boolean markedForLoss(EntityPlayer player) {
        if (player != null && !EntityUtil.isFakePlayer(player))
            return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getBoolean(ModNBT.HEART_LOSS);
        return false;
    }

    public static int heartCount(EntityPlayer player) {
        if (player != null && !EntityUtil.isFakePlayer(player)) {
            NBTTagCompound tag = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            if (tag.hasKey(ModNBT.HEART_COUNT, 3))
                return tag.getInteger(ModNBT.HEART_COUNT);
        }
        return 0;
    }

    public static void setHeartCount(EntityPlayer player, int i) {
        if (player != null && !EntityUtil.isFakePlayer(player))
            player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger(ModNBT.HEART_COUNT, i);
    }

    public static void applyModifier(EntityPlayer player, int amount) {
        IAttributeInstance attributeInstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        AttributeModifier modifier = attributeInstance.getModifier(ModVars.HEALTH_ATTRIB_UUID);
        double health = amount * 2f;
        if (modifier != null)
            attributeInstance.removeModifier(modifier);
        if (amount != 0)
            attributeInstance.applyModifier(new AttributeModifier(ModVars.HEALTH_ATTRIB_UUID, ModVars.HEALTH_ATTRIBUTE_ID, health, 0));
        setHeartCount(player, amount);
    }
}
