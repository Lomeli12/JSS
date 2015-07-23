package net.lomeli.jss.core.helper;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.jss.lib.ModNBT;

public class SoulBindHelper {

    public static void bindToPlayer(ItemStack stack, EntityPlayer player) {
        if (stack != null && player != null && !EntityUtil.isFakePlayer(player)) {
            if (bounded(stack))
                return;
            NBTTagCompound tag = stack.hasTagCompound() ? stack.stackTagCompound : new NBTTagCompound();
            tag.setLong(ModNBT.ITEM_OWNER_MOST, player.getUniqueID().getMostSignificantBits());
            tag.setLong(ModNBT.ITEM_OWNER_LEAST, player.getUniqueID().getLeastSignificantBits());
            tag.setString(ModNBT.ITEM_OWNER_NAME, player.getCommandSenderName());
            stack.stackTagCompound = tag;
        }
    }

    public static boolean bounded(ItemStack stack) {
        return stack != null && stack.hasTagCompound() ? stack.getTagCompound().hasKey(ModNBT.ITEM_OWNER_MOST) && stack.getTagCompound().hasKey(ModNBT.ITEM_OWNER_LEAST) && stack.getTagCompound().hasKey(ModNBT.ITEM_OWNER_NAME) : false;
    }

    public static boolean isRightPlayer(ItemStack stack, EntityPlayer player) {
        if (player == null || stack == null)
            return false;
        UUID id = getBoundedUUID(stack);
        if (id == null)
            return true;
        return player.getUniqueID().equals(id);
    }

    public static UUID getBoundedUUID(ItemStack stack) {
        if (stack == null || !bounded(stack))
            return null;
        return new UUID(stack.getTagCompound().getLong(ModNBT.ITEM_OWNER_MOST), stack.getTagCompound().getLong(ModNBT.ITEM_OWNER_LEAST));
    }

    public static String getBoundedName(ItemStack stack) {
        if (stack == null || !bounded(stack))
            return null;
        return stack.getTagCompound().getString(ModNBT.ITEM_OWNER_NAME);
    }
}
