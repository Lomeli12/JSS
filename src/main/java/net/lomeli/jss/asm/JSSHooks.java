package net.lomeli.jss.asm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.relauncher.ReflectionHelper;

import net.lomeli.jss.asm.event.PlayerCraftItemEvent;

public class JSSHooks {
    public static boolean onItemCraftEvent(EntityPlayer player, ItemStack output) {
        boolean flag = MinecraftForge.EVENT_BUS.post(new PlayerCraftItemEvent(player, output));
        if (flag && player.inventory.getItemStack() != null)
            ReflectionHelper.setPrivateValue(InventoryPlayer.class, player.inventory, null, "g", "field_70457_g", "itemStack");
        return flag;
    }
}
