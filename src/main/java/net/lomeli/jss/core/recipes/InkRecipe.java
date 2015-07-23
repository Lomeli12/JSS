package net.lomeli.jss.core.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import cpw.mods.fml.common.Loader;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.jss.compat.BotaniaCompat;
import net.lomeli.jss.items.ModItems;
import net.lomeli.jss.lib.ModIDS;
import net.lomeli.jss.lib.ModNBT;

public class InkRecipe implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting invCraft, World world) {
        boolean foundInk = false;
        boolean foundItem = false;

        for (int i = 0; i < invCraft.getSizeInventory(); i++) {
            ItemStack stack = invCraft.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() == ModItems.resources && stack.getItemDamage() == 1 && !foundInk)
                    foundInk = true;
                else if (!foundItem) {
                    if (!NBTUtil.getBoolean(stack, ModNBT.INK_NBT) && isValidArmor(stack)) {
                        if (Loader.isModLoaded(ModIDS.BOTANIA_ID)) {
                            if (!isPhantomInkable(stack))
                                foundItem = true;
                            else
                                return false;
                        } else
                            foundItem = true;
                    } else return false;
                } else return false;
            }
        }

        return foundInk && foundItem;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting invCraft) {
        ItemStack item = null;

        for (int i = 0; i < invCraft.getSizeInventory(); i++) {
            ItemStack stack = invCraft.getStackInSlot(i);
            if (stack != null && item == null && isValidArmor(stack))
                item = stack;
        }

        if (NBTUtil.getBoolean(item, ModNBT.INK_NBT) && isPhantomInkable(item))
            return null;

        ItemStack copy = item.copy();
        NBTUtil.setBoolean(copy, ModNBT.INK_NBT, true);
        return copy;
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    private boolean isValidArmor(ItemStack stack) {
        return (stack == null || stack.getItem() == null) ? false : (stack.getItem() instanceof ItemArmor || stack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) || stack.getItem() == Items.skull);
    }

    private boolean isPhantomInkable(ItemStack stack) {
        if (Loader.isModLoaded(ModIDS.BOTANIA_ID))
            BotaniaCompat.isPhantomInkable(stack);
        return false;
    }
}
