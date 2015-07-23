package net.lomeli.jss.items.weapons;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.jss.JSS;
import net.lomeli.jss.core.helper.LocalizationHelper;

public class ItemBaseSword extends ItemSword {
    public ItemBaseSword(ToolMaterial material) {
        super(material);
        this.setCreativeTab(JSS.modTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
        String toolTip = getUnlocalizedName(stack) + ".tooltip";
        if (StatCollector.canTranslate(toolTip)) {
            String text = LocalizationHelper.translate(toolTip);
            for (String tip : text.split("/n"))
                list.add(tip);
        }
    }

    @Override
    public Item setUnlocalizedName(String name) {
        return super.setUnlocalizedName(JSS.MOD_ID + "." + name);
    }

    @Override
    public Item setTextureName(String texture) {
        return super.setTextureName(JSS.MOD_ID + ":" + texture);
    }
}
