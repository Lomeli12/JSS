package net.lomeli.jss.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.jss.JSS;

public class ItemResources extends ItemJSS {
    private static final int subItems = 9;
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemResources() {
        super();
        this.setUnlocalizedName("resources");
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[subItems];
        for (int i = 0; i < icons.length; i++)
            icons[i] = register.registerIcon(JSS.MOD_ID + ":resources_" + i);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        return meta < subItems ? icons[meta] : icons[0];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < subItems; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
