package net.lomeli.jss.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.jss.core.helper.HealthModifierHelper;
import net.lomeli.jss.core.helper.LocalizationHelper;
import net.lomeli.jss.core.helper.SoulBindHelper;

public class ItemEmptyHeart extends ItemJSS {

    public ItemEmptyHeart() {
        super();
        this.setTextureName("emptyHeart");
        this.setUnlocalizedName("emptyHeart");
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!SoulBindHelper.bounded(stack))
                SoulBindHelper.bindToPlayer(stack, player);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
        if (SoulBindHelper.bounded(stack))
            list.add(LocalizationHelper.translate("item.jss.emptyHeart.tooltip", SoulBindHelper.getBoundedName(stack)));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote && !EntityUtil.isFakePlayer(player)) {
            if (player.capabilities.isCreativeMode)
                player.addChatComponentMessage(new ChatComponentText(LocalizationHelper.translate("message.jss.emptyheart.creative")));
            else if (world.difficultySetting == EnumDifficulty.PEACEFUL)
                player.addChatComponentMessage(new ChatComponentText(LocalizationHelper.translate("message.jss.emptyheart.peaceful")));
            else if (SoulBindHelper.bounded(stack)) {
                if (SoulBindHelper.isRightPlayer(stack, player)) {
                    int amount = HealthModifierHelper.heartCount(player);
                    if (amount >= 10)
                        player.addChatComponentMessage(new ChatComponentText(LocalizationHelper.translate("message.jss.emptyheart.full")));
                    else {
                        amount++;
                        HealthModifierHelper.applyModifier(player, amount);
                        player.addChatComponentMessage(new ChatComponentText(LocalizationHelper.translate("message.jss.emptyheart.accept")));
                    }
                } else
                    player.addChatComponentMessage(new ChatComponentText(LocalizationHelper.translate("message.jss.emptyheart.notowner")));
            }
            stack.stackSize--;
        }
        return stack;
    }
}
