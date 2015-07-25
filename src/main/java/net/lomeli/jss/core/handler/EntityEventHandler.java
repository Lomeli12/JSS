package net.lomeli.jss.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import net.minecraftforge.event.entity.living.LivingDropsEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.jss.items.ModItems;

public class EntityEventHandler {

    @SubscribeEvent
    public void entityDropEvent(LivingDropsEvent event) {
        DamageSource source = event.source;
        EntityLivingBase entity = event.entityLiving;
        Entity attacker = source.isProjectile() ? source.getEntity() : source.getSourceOfDamage();
        if (!entity.worldObj.isRemote) {
            if (entity instanceof EntityWither && EntityUtil.damageFromPlayer(source)) {
                EntityPlayer player = (EntityPlayer) attacker;
                if (!EntityUtil.isFakePlayer(player)) {
                    float chance = (35 + (event.lootingLevel * 2)) / 100f;
                    EntityUtil.entityDropItem(entity, new ItemStack(ModItems.resources, 1, 6), chance);
                }
            } else if (entity instanceof EntitySkeleton) {
                EntitySkeleton skeleton = (EntitySkeleton) entity;
                if (skeleton.getSkeletonType() == 1 && EntityUtil.damageFromPlayer(source)) {
                    EntityPlayer player = (EntityPlayer) attacker;
                    if (!EntityUtil.isFakePlayer(player)) {
                        float chance = (5 + (event.lootingLevel * 2)) / 100f;
                        EntityUtil.entityDropItem(entity, new ItemStack(ModItems.resources, 1, 6), chance);
                    }
                }
            }
        }
    }
}
