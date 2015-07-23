package net.lomeli.jss.items.weapons;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.jss.core.helper.LocalizationHelper;
import net.lomeli.jss.core.helper.SoulBindHelper;
import net.lomeli.jss.items.ItemJSS;
import net.lomeli.jss.lib.ModVars;

public class ItemSoulSword extends ItemJSS {
    public static final DamageSource soulDamage = new DamageSource("jss.souldamage").setDamageBypassesArmor();

    public ItemSoulSword() {
        super();
        this.setUnlocalizedName("soulsword");
        this.setTextureName("soulSword");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!SoulBindHelper.bounded(stack))
                SoulBindHelper.bindToPlayer(stack, player);
            else {
                if (!SoulBindHelper.isRightPlayer(stack, player) && world.getTotalWorldTime() % 20L == 0)
                    player.attackEntityFrom(soulDamage, 2f);
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase && SoulBindHelper.isRightPlayer(stack, player)) {
            float damage = ((EntityLivingBase) entity).getMaxHealth() * 0.2f;
            if (damage < 1f)
                damage = 1f;
            entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
            player.attackEntityFrom(soulDamage, player.getMaxHealth() / 30f);
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advToolTips) {
        if (SoulBindHelper.bounded(stack)) {
            list.add(LocalizationHelper.translate("tooltip.jss.soulsword.bounded.player", SoulBindHelper.getBoundedName(stack)));
            if (!SoulBindHelper.isRightPlayer(stack, player))
                list.add(LocalizationHelper.translate("tooltip.jss.soulsword.bounded.notowner"));
            list.add(LocalizationHelper.translate("tooltip.jss.soulsword.info"));
        } else
            list.add(LocalizationHelper.translate("tooltip.jss.soulsword.bounded.none"));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return ModVars.Otherworldly;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
