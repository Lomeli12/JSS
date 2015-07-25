package net.lomeli.jss.asm.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.player.PlayerEvent;

import cpw.mods.fml.common.eventhandler.Cancelable;

@Cancelable
public class PlayerCraftItemEvent extends PlayerEvent {

    public final ItemStack craftingOutput;

    public PlayerCraftItemEvent(EntityPlayer player, ItemStack output) {
        super(player);
        this.craftingOutput = output;
    }
}
