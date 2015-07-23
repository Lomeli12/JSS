package net.lomeli.jss.entities;

import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import net.lomeli.lomlib.lib.Vector3;
import net.lomeli.lomlib.util.MathHelper;
import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.jss.compat.AvaritaCompact;
import net.lomeli.jss.core.helper.HealthModifierHelper;
import net.lomeli.jss.core.helper.SoulBindHelper;
import net.lomeli.jss.items.ModItems;
import net.lomeli.jss.lib.ModNBT;

public class EntityUnborn extends EntityCreature implements IBossDisplayData {
    private static final float MAX_HEALTH = 450f;
    private static final int MOB_SPAWN_TICKS = 140;
    private static final int TELEPORT_DELAY = 90;

    public EntityUnborn(World world) {
        super(world);
        setSize(0.6F, 1.8F);
        getNavigator().setCanSwim(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, Float.MAX_VALUE));
        isImmuneToFire = true;
        experienceValue = 900;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!worldObj.isRemote && worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
            setDead();
        if (getTargetUUID() == null || isTargetDead()) {
            this.setDead();
            return;
        }
        ChunkCoordinates source = getSource();
        EntityPlayer target = getTargetAsPlayer();
        if (getHealth() > 0f) {
            //if (!worldObj.isRemote) {
            boolean lowFlag = getHealth() <= (getMaxHealth() / 4);
            // Teleporting
            int tpTick = getTPDelay() - 1;
            if (tpTick <= 0 || recentlyHit >= 99) {
                tpTick = TELEPORT_DELAY / (lowFlag ? 2 : 1);
                int attempts = 0;
                while (!teleportRandomly() && attempts < 50)
                    attempts++;
                if (attempts >= 50)
                    teleportTo(source.posX + 0.5f, source.posY + 2, source.posZ + 0.5f);
            }
            setTPDelay(tpTick);

            int attackMode = getAttackMode();
            switch (attackMode) {
                case 1:
                    if (worldObj.getTotalWorldTime() % 10L == 0) {
                        for (int i = -7; i < 8; i++)
                            for (int j = -7; j < 8; j++) {
                                double x = MathHelper.floor(this.posX + i) + worldObj.rand.nextFloat();
                                double y = MathHelper.floor(this.posY) + 0.5;
                                double z = MathHelper.floor(this.posZ + j) + worldObj.rand.nextFloat();
                                if (pointDistanceSpace(this.posX, this.posY, this.posZ, x, y, z) < 6)
                                    worldObj.spawnParticle("flame", x, y, z, 0, 0, 0);
                            }
                        if (pointDistanceSpace(this.posX, this.posY, this.posZ, target.posX, target.posY, target.posZ) < 6)
                            target.setFire(12);
                    }
                    break;
                default: // Mob spawning
                    int mobSpawn = getMobSpawnTicks() - 1;
                    if (mobSpawn <= 0) {
                        mobSpawn = (lowFlag ? MOB_SPAWN_TICKS / 2 : MOB_SPAWN_TICKS);
                        if (worldObj.rand.nextFloat() < 0.8f)
                            spawnUndeadMobs();
                    }
                    setMobSpawnTicks(mobSpawn);
                    break;
            }

            for (int i = -13; i < 14; i++)
                for (int j = -13; j < 14; j++) {
                    double x = MathHelper.floor(source.posX + i) + worldObj.rand.nextFloat();
                    double y = MathHelper.floor(source.posY) - 0.5;
                    double z = MathHelper.floor(source.posZ + j) + worldObj.rand.nextFloat();
                    if (MathHelper.floor(pointDistanceSpace(source.posX, source.posY, source.posZ, x, y, z)) == 12) {
                        worldObj.spawnParticle("smoke", x, y, z, 0, 0, 0);
                        worldObj.spawnParticle("smoke", x, y + 1, z, 0, 0, 0);
                    }
                }

            // Pulling running player
            if (pointDistanceSpace(target.posX, target.posY, target.posZ, source.posX, source.posY, source.posZ) > 12)
                pullTarget(getTargetAsPlayer(), new Vector3(source.posX, source.posY, source.posZ), 0.5f);
            //}
        }
    }

    @Override
    protected void dropFewItems(boolean hitByPlayer, int looting) {
        super.dropFewItems(hitByPlayer, looting);
        if (hitByPlayer) {
            ItemStack emptyHeart = new ItemStack(ModItems.emptyHeart);
            SoulBindHelper.bindToPlayer(emptyHeart, getTargetAsPlayer());
            entityDropItem(emptyHeart, 1f);
            int size = worldObj.rand.nextInt(4);
            if (size > 0)
                entityDropItem(new ItemStack(ModItems.resources, size, 8), 1f);
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MAX_HEALTH);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(20, 0); //Attack mode
        dataWatcher.addObject(21, ""); // Target UUID
        dataWatcher.addObject(22, 0); // TP Delay
        dataWatcher.addObject(23, 0); // Source X
        dataWatcher.addObject(24, 0); // Source Y
        dataWatcher.addObject(25, 0); // Source Z
        dataWatcher.addObject(26, 0); // Ticks spawning mobs
    }

    private void spawnUndeadMobs() {
        if (worldObj.isRemote) return;

        for (int i = 0; i < worldObj.rand.nextInt(4); i++) {
            EntityCreature creature;
            switch (worldObj.rand.nextInt(4)) {
                case 1:
                    creature = new EntitySkeleton(worldObj);
                    ((EntitySkeleton) creature).setSkeletonType(1);
                    creature.setCurrentItemOrArmor(0, new ItemStack(Items.diamond_sword));
                    break;
                case 2:
                    creature = new EntitySkeleton(worldObj);
                    creature.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
                    break;
                case 3:
                    creature = new EntityPigZombie(worldObj);
                    ((EntityPigZombie) creature).becomeAngryAt(getTargetAsPlayer());
                    creature.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
                    break;
                default:
                    creature = new EntityZombie(worldObj);
                    creature.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
                    break;
            }
            if (worldObj.rand.nextBoolean())
                creature.setCurrentItemOrArmor(1, new ItemStack(Items.iron_helmet));
            creature.setPosition(this.posX, this.posY, this.posZ);
            creature.setAttackTarget(getTargetAsPlayer());
            if (creature != null)
                worldObj.spawnEntityInWorld(creature);
        }
    }

    public EntityPlayer getTargetAsPlayer() {
        UUID uuid = getTargetUUID();
        if (uuid == null)
            return null;
        EntityPlayer player = worldObj.func_152378_a(uuid);
        return player;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity attacker = source.getSourceOfDamage();
        if (attacker == null || attacker.getUniqueID() == null || !attacker.getUniqueID().equals(this.getTargetUUID()))
            return false;
        if (source.isUnblockable())
            amount /= 3f;
        if (attacker instanceof EntityPlayer) {
            if (((EntityPlayer) attacker).getCurrentEquippedItem() != null && ((EntityPlayer) attacker).getCurrentEquippedItem().getItem() == AvaritaCompact.infinitySword) {
                HealthModifierHelper.removeHeartLoss((EntityPlayer) attacker);
                attacker.attackEntityFrom(DamageSource.generic, ((EntityPlayer) attacker).getMaxHealth());
            }
        }
        return super.attackEntityFrom(source, amount);
    }

    private void infectNearbyPlayers(int x, int y, int z) {
        List<EntityPlayer> playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(8f, 8f, 8f));
        if (playerList != null && playerList.size() > 0) {
            for (EntityPlayer player : playerList) {
                if (player != null && getTargetUUID().equals(player.getUniqueID()) && !HealthModifierHelper.markedForLoss(player)) {
                    HealthModifierHelper.markForLoss(player);
                    break;
                }
            }
        }
    }

    @Override
    protected void onDeathUpdate() {
        super.onDeathUpdate();
        if (!worldObj.isRemote) {
            EntityPlayer target = getTargetAsPlayer();
            if (HealthModifierHelper.markedForLoss(target))
                HealthModifierHelper.removeHeartLoss(target);
        }
    }

    private void pullTarget(EntityPlayer target, Vector3 origin, float mod) {
        Vector3 targetVec = Vector3.fromEntityCenter(target);
        Vector3 finalVector = origin.clone().sub(targetVec);

        if (finalVector.magnitudeSquared() > 1)
            finalVector.normalize();

        target.motionX = finalVector.getX() * mod;
        target.motionY = (finalVector.getY() + 1) * mod;
        target.motionZ = finalVector.getZ() * mod;
        target.attackEntityFrom(DamageSource.generic, 0.5f);
    }

    private boolean isTargetDead() {
        EntityPlayer player = getTargetAsPlayer();
        return player == null || player.isDead;
    }

    public int getTPDelay() {
        return dataWatcher.getWatchableObjectInt(22);
    }

    public void setTPDelay(int delay) {
        dataWatcher.updateObject(22, delay);
    }

    public ChunkCoordinates getSource() {
        int x = dataWatcher.getWatchableObjectInt(23);
        int y = dataWatcher.getWatchableObjectInt(24);
        int z = dataWatcher.getWatchableObjectInt(25);
        return new ChunkCoordinates(x, y, z);
    }

    public int getMobSpawnTicks() {
        return dataWatcher.getWatchableObjectInt(26);
    }

    public void setMobSpawnTicks(int ticks) {
        dataWatcher.updateObject(26, ticks);
    }

    public int getAttackMode() {
        return dataWatcher.getWatchableObjectInt(20);
    }

    public void setAttackMode(int mode) {
        dataWatcher.updateObject(20, mode);
    }

    public void setSource(int x, int y, int z) {
        dataWatcher.updateObject(23, x);
        dataWatcher.updateObject(24, y);
        dataWatcher.updateObject(25, z);
    }

    public UUID getTargetUUID() {
        return UUID.fromString(dataWatcher.getWatchableObjectString(21));
    }

    public void setTargetUUID(UUID uuid) {
        dataWatcher.updateObject(21, uuid.toString());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(ModNBT.UNBORN_SPAWN_MOD, getMobSpawnTicks());

        ChunkCoordinates source = getSource();
        tag.setInteger(ModNBT.UNBORN_BASE_X, source.posX);
        tag.setInteger(ModNBT.UNBORN_BASE_Y, source.posY);
        tag.setInteger(ModNBT.UNBORN_BASE_Z, source.posZ);

        tag.setInteger(ModNBT.UNBORN_ATTACK, getAttackMode());
        tag.setInteger(ModNBT.UNBORN_TP_DELAY, getTPDelay());

        tag.setString(ModNBT.UNBORN_TARGET_UUID, getTargetUUID().toString());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.setMobSpawnTicks(tag.getInteger(ModNBT.UNBORN_SPAWN_MOD));

        int x = tag.getInteger(ModNBT.UNBORN_BASE_X);
        int y = tag.getInteger(ModNBT.UNBORN_BASE_Y);
        int z = tag.getInteger(ModNBT.UNBORN_BASE_Z);
        this.setSource(x, y, z);

        this.setTPDelay(tag.getInteger(ModNBT.UNBORN_TP_DELAY));
        this.setAttackMode(tag.getInteger(ModNBT.UNBORN_ATTACK));

        UUID uuid = UUID.fromString(tag.getString(ModNBT.UNBORN_TARGET_UUID));
        this.setTargetUUID(uuid);
    }

    // Copied from Botania
    protected boolean teleportRandomly() {
        double d0 = posX + (rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = posY + (rand.nextInt(64) - 32);
        double d2 = posZ + (rand.nextDouble() - 0.5D) * 64.0D;
        return teleportTo(d0, d1, d2);
    }

    protected boolean teleportTo(double par1, double par3, double par5) {
        setAttackMode(worldObj.rand.nextInt(5));
        double d3 = posX;
        double d4 = posY;
        double d5 = posZ;
        posX = par1;
        posY = par3;
        posZ = par5;
        boolean flag = false;
        int i = MathHelper.floor(posX);
        int j = MathHelper.floor(posY);
        int k = MathHelper.floor(posZ);

        if (worldObj.blockExists(i, j, k)) {
            boolean flag1 = false;

            while (!flag1 && j > 0) {
                Block block = worldObj.getBlock(i, j - 1, k);

                if (block.getMaterial().blocksMovement())
                    flag1 = true;
                else {
                    --posY;
                    --j;
                }
            }

            if (flag1) {
                setPosition(posX, posY, posZ);

                if (worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox))
                    flag = true;

                // Prevent out of bounds teleporting
                ChunkCoordinates source = getSource();
                if (pointDistanceSpace(posX, posY, posZ, source.posX, source.posY, source.posZ) > 12)
                    flag = false;
            }
        }

        if (!flag) {
            setPosition(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for (int l = 0; l < short1; ++l) {
                double d6 = l / (short1 - 1.0D);
                float f = (rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (posX - d3) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
                double d8 = d4 + (posY - d4) * d6 + rand.nextDouble() * height;
                double d9 = d5 + (posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * width * 2.0D;
                worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
            }

            worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    public static boolean spawnBoss(EntityPlayer player, ItemStack stack, World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.glowstone && !world.isRemote && !EntityUtil.isFakePlayer(player)) {
            if (world.difficultySetting == EnumDifficulty.PEACEFUL)
                return false;
            if (structureBuilt(world, x, y, z) && world.getEntitiesWithinAABB(EntityUnborn.class, block.getCollisionBoundingBoxFromPool(world, x, y, z).expand(8f, 8f, 8f)).isEmpty()) {
                if (!player.capabilities.isCreativeMode)
                    stack.stackSize--;
                EntityUnborn unborn = new EntityUnborn(world);
                unborn.setPosition(x + 1, y + 3, z + 1);
                unborn.setSource(x, y, z);
                unborn.setMobSpawnTicks(MOB_SPAWN_TICKS);
                unborn.setTPDelay(TELEPORT_DELAY / 6);
                unborn.setTargetUUID(player.getUniqueID());
                HealthModifierHelper.markForLoss(player);
                world.spawnEntityInWorld(unborn);
                world.playSoundAtEntity(unborn, "mob.endermen.scream", 10F, 0.5f);
                return true;
            }
        }
        return false;
    }

    private static boolean structureBuilt(World world, int x, int y, int z) {
        int count = 0;
        boolean flag = false;
        for (int difX = -1; difX < 2; difX++)
            for (int difZ = -1; difZ < 2; difZ++) {
                Block block = world.getBlock(x + difX, y - 1, z + difZ);
                count += block == (flag ? Blocks.obsidian : Blocks.end_stone) ? 1 : 0;
                flag = !flag;
            }
        if (isObelisk(world, x + 4, y - 1, z + 4))
            count++;
        if (isObelisk(world, x + 4, y - 1, z - 4))
            count++;
        if (isObelisk(world, x - 4, y - 1, z - 4))
            count++;
        if (isObelisk(world, x - 4, y - 1, z + 4))
            count++;
        return count == 13;
    }

    private static boolean isObelisk(World world, int x, int y, int z) {
        for (int i = 0; i < 3; i++) {
            Block block = world.getBlock(x, y + i, z);
            if (i == 2) {
                if (block != Blocks.glowstone)
                    return false;
            } else if (block != Blocks.obsidian)
                return false;
        }
        return true;
    }

    public static float pointDistanceSpace(double x1, double y1, double z1, double x2, double y2, double z2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }
}
