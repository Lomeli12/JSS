package net.lomeli.jss.blocks.connected;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.jss.client.render.blocks.IconConnected;

public class BlockObsiSteel extends BlockCT {

    public BlockObsiSteel() {
        super(Material.rock);
        this.setHardness(50.0F);
        this.setResistance(2000.0F);
        this.setStepSound(soundTypePiston);
        this.setBlockName("obsidianSteelLined");
        this.setBlockTextureName("obsidiSteelLined/obsidiSteel");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        ((IconConnected) this.blockIcon).setIcon(1, Blocks.obsidian.getIcon(0, 0));
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return !(entity instanceof EntityWither || entity instanceof EntityDragon);
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }
}