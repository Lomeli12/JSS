package net.lomeli.jss.blocks.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import net.minecraftforge.fluids.BlockFluidFinite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.jss.JSS;
import net.lomeli.jss.fluid.ModFluids;

public class BlockFluidObsiSteel extends BlockFluidFinite {
    @SideOnly(Side.CLIENT)
    public IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    public IIcon flowIcon;

    public BlockFluidObsiSteel() {
        super(ModFluids.obsidiSteel, Material.lava);
        this.setBlockName("moltenObsidianSteel");
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        stillIcon = register.registerIcon(JSS.MOD_ID + ":fluid/fluid_obsidiSteel");
        flowIcon = register.registerIcon(JSS.MOD_ID + ":fluid/fluid_obsidiSteel_flow");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowIcon;
    }

    @Override
    public Block setBlockName(String name) {
        return super.setBlockName(JSS.MOD_ID + "." + name);
    }
}
