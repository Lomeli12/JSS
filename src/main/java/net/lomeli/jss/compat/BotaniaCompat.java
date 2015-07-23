package net.lomeli.jss.compat;

import net.minecraft.item.ItemStack;

import vazkii.botania.api.item.IPhantomInkable;

public class BotaniaCompat {
    public static void loadBotaniaCompat() {

    }

    public static boolean isPhantomInkable(ItemStack stack) {
        return stack != null && stack.getItem() instanceof IPhantomInkable;
    }
}
