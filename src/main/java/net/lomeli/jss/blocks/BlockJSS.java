package net.lomeli.jss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.lomeli.jss.JSS;

public class BlockJSS extends Block {
    public BlockJSS(Material material) {
        super(material);
        this.setCreativeTab(JSS.modTab);
    }

    @Override
    public Block setBlockName(String name) {
        return super.setBlockName(JSS.MOD_ID + "." + name);
    }

    @Override
    public Block setBlockTextureName(String texture) {
        return super.setBlockTextureName(JSS.MOD_ID + ":" + texture);
    }
}
