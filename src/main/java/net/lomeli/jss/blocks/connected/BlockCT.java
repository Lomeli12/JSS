package net.lomeli.jss.blocks.connected;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.jss.blocks.BlockJSS;
import net.lomeli.jss.client.render.blocks.IconConnected;
import net.lomeli.jss.lib.ModRender;

public class BlockCT extends BlockJSS {

    public BlockCT(Material material) {
        super(material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = new IconConnected(register, this.getTextureName());
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return ((IconConnected) this.blockIcon);
    }

    @Override
    public int getRenderType() {
        return ModRender.ctRenderID;
    }

    @Override
    public boolean isBlockNormalCube() {
        return true;
    }

    @Override
    public boolean isNormalCube() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        Block block = world.getBlock(x, y, z);

        if (block != null && !world.isAirBlock(x, y, z))
            return !block.isOpaqueCube() && block != this && (block.renderAsNormalBlock() ? block.isBlockSolid(world, x, y, z, Facing.oppositeSide[side]) : !block.isSideSolid(world, x, y, z, ForgeDirection.getOrientation(Facing.oppositeSide[side]))) && super.shouldSideBeRendered(world, x, y, z, side);

        return super.shouldSideBeRendered(world, x, y, z, side);
    }
}
