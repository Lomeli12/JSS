package net.lomeli.jss.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.lomeli.jss.JSS;
import net.lomeli.jss.items.ModItems;

public class ModTab extends CreativeTabs {

    public ModTab() {
        super(JSS.MOD_ID);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.resources;
    }
}
