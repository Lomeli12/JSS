package net.lomeli.jss.client.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.jss.lib.ModNBT;

public class ItemTooltipHandler {
    @SubscribeEvent
    public void handleItemTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        if (stack != null && stack.getItem() != null) {
            if (NBTUtil.getBoolean(stack, ModNBT.INK_NBT))
                event.toolTip.add(StatCollector.translateToLocal("tooltip.jss.invisibleink"));
        }
    }
}
