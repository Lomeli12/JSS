package net.lomeli.jss.core.handler;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import net.lomeli.jss.core.helper.HealthModifierHelper;
import net.lomeli.jss.entities.EntityUnborn;
import net.lomeli.jss.items.ModItems;
import net.lomeli.jss.lib.ModNBT;

public class PlayerEventHandler {

    @SubscribeEvent
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (!event.world.isRemote) {
            EntityPlayer player = event.entityPlayer;
            ItemStack hand = player.getCurrentEquippedItem();
            if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                Block block = event.world.getBlock(event.x, event.y, event.z);
                int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
                if (hand != null && hand.getItem() != null) {
                    if (hand.getItem() == ModItems.resources) {
                        switch (hand.getItemDamage()) {
                            case 5:
                                if (block != null && block.getMaterial() == Material.glass && !block.hasTileEntity(meta)) {
                                    block.breakBlock(event.world, event.x, event.y, event.z, block, meta);
                                    ItemStack itemBlock = new ItemStack(block, 1, meta);
                                    event.world.playSoundEffect(event.x, event.y, event.z, block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                                    EntityItem entityItem = new EntityItem(event.world, event.x, event.y, event.z);
                                    entityItem.setEntityItemStack(itemBlock);
                                    event.world.spawnEntityInWorld(entityItem);
                                    event.world.setBlockToAir(event.x, event.y, event.z);
                                }
                                break;
                            case 7:
                                EntityUnborn.spawnBoss(player, hand, event.world, event.x, event.y, event.z);
                                break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerLoggedin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.worldObj.isRemote) {
            if (HealthModifierHelper.hasModifier(event.player))
                HealthModifierHelper.applyModifier(event.player, HealthModifierHelper.heartCount(event.player));
            if (event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey(ModNBT.UNBORN_LOGGED_OUT, 10)) {
                NBTTagCompound tag = event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(ModNBT.UNBORN_LOGGED_OUT);
                EntityUnborn unborn = new EntityUnborn(event.player.worldObj);
                unborn.readFromNBT(tag);
                event.player.worldObj.spawnEntityInWorld(unborn);
            }
        }
    }

    @SubscribeEvent
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!event.player.worldObj.isRemote) {
            List<EntityUnborn> unbornList = event.player.worldObj.getEntitiesWithinAABB(EntityUnborn.class, event.player.boundingBox.expand(20, 20, 20));
            if (unbornList != null && unbornList.isEmpty()) {
                for (EntityUnborn unborn : unbornList) {
                    if (unborn != null && event.player.getUniqueID().equals(unborn.getTargetUUID())) {
                        NBTTagCompound tag = new NBTTagCompound();
                        unborn.writeToNBT(tag);
                        event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag(ModNBT.UNBORN_LOGGED_OUT, tag);
                        unborn.setDead();
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote) {
            int amount = HealthModifierHelper.heartCount(event.player);
            if (HealthModifierHelper.markedForLoss(event.player)) {
                HealthModifierHelper.removeHeartLoss(event.player);
                amount--;
            }
            HealthModifierHelper.applyModifier(event.player, amount);
        }
    }

    @SubscribeEvent
    public void playerAttackEntity(AttackEntityEvent event) {
        EntityPlayer player = event.entityPlayer;
        Entity target = event.target;
        ItemStack hand = player.getCurrentEquippedItem();
        if (!player.worldObj.isRemote && target != null) {
            if (hand != null) {
                if (hand.getItem() == ModItems.soulSword) {
                    //event.setCanceled(true);
                }
            }
        }
    }
}
