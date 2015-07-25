package net.lomeli.jss.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.RenderPlayerEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.jss.lib.ModNBT;

public class EventHandlerClient {
    private static final int[] armorSlots = {3, 2, 1, 0};
    public static float clientTicks;

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

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (Minecraft.getMinecraft().inGameHasFocus)
                clientTicks++;
            else {
                GuiScreen gui = Minecraft.getMinecraft().currentScreen;
                if (gui == null || !gui.doesGuiPauseGame())
                    clientTicks++;
            }
        }
    }
}
