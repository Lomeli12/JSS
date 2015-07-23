package net.lomeli.jss.client.handler;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.RenderPlayerEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.jss.lib.ModNBT;

public class EventHandlerClient {
    private static final int[] armorSlots = {3, 2, 1, 0};

    @SubscribeEvent
    public void setArmorModel(RenderPlayerEvent.SetArmorModel event) {
        if (event.slot < 4) {
            ItemStack stack = event.entityPlayer.getCurrentArmor(event.slot);
            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) stack.getItem();
                if (armorSlots[event.slot] == armor.armorType && NBTUtil.getBoolean(stack, ModNBT.INK_NBT))
                    event.result = 0;
            }
        }
    }
}
