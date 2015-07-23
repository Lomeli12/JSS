package net.lomeli.jss.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Created by lomeli12 on 11/9/14.
 */
public class ConnectedTexturesRenderer {
    private Block block;
    private int meta;
    private RenderBlocks renderer;

    public ConnectedTexturesRenderer(Block block, int meta, RenderBlocks renderer) {
        this.block = block;
        this.meta = meta;
        this.renderer = renderer;
    }

    public ConnectedTexturesRenderer(Block block, RenderBlocks renderer) {
        this(block, 0, renderer);
    }

    public boolean renderStandardBlock(IBlockAccess world, int x, int y, int z) {
        Tessellator tess = Tessellator.instance;
        renderer.enableAO = false;
        tess.setColorOpaque_F(1f, 1f, 1f);
        boolean flag = false;
        if (block != null) {
            for (int side = 0; side < 6; side++) {
                int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
                IIcon icon = block.getIcon(side, meta);
                if (icon != null) {
                    if (icon instanceof IconConnected) {
                        IIcon[] iconsToRender = getIconsForSide(world, x, y, z, side, (IconConnected) icon);
                        renderSideIcons(tess, world, x, y, z, side, brightness, iconsToRender);
                    } else
                        renderStandardSide(tess, world, x, y, z, icon, side, brightness);
                }
            }
        }
        return flag;
    }

    private IIcon[] getIconsForSide(IBlockAccess world, int x, int y, int z, int side, IconConnected iconBase) {
        IIcon[] icons = new IIcon[9];
        icons[0] = iconBase.getBase();
        if (side == 3) {
            if (!doBlocksMatch(world, x - 1, y, z))
                icons[1] = iconBase.getLeftEdge();
            if (!doBlocksMatch(world, x + 1, y, z))
                icons[2] = iconBase.getRightEdge();
            if (!doBlocksMatch(world, x, y + 1, z))
                icons[3] = iconBase.getTopEdge();
            if (!doBlocksMatch(world, x, y - 1, z))
                icons[4] = iconBase.getBottomEdge();
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                if (!doBlocksMatch(world, x + 1, y - 1, z))
                    icons[5] = iconBase.getABRCorner();
            }
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                if (!doBlocksMatch(world, x - 1, y - 1, z))
                    icons[6] = iconBase.getABLCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                if (!doBlocksMatch(world, x + 1, y + 1, z))
                    icons[7] = iconBase.getATRCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                if (!doBlocksMatch(world, x - 1, y + 1, z))
                    icons[8] = iconBase.getATLCorner();
            }
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x + 1, y, z))
                icons[5] = iconBase.getBRCorner();
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x - 1, y, z))
                icons[6] = iconBase.getBLCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x + 1, y, z))
                icons[7] = iconBase.getTRCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x - 1, y, z))
                icons[8] = iconBase.getTLCorner();
        } else if (side == 2) {
            if (!doBlocksMatch(world, x + 1, y, z))
                icons[1] = iconBase.getLeftEdge();
            if (!doBlocksMatch(world, x - 1, y, z))
                icons[2] = iconBase.getRightEdge();
            if (!doBlocksMatch(world, x, y + 1, z))
                icons[3] = iconBase.getTopEdge();
            if (!doBlocksMatch(world, x, y - 1, z))
                icons[4] = iconBase.getBottomEdge();
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                if (!doBlocksMatch(world, x - 1, y - 1, z))
                    icons[5] = iconBase.getABRCorner();
            }
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                if (!doBlocksMatch(world, x + 1, y - 1, z))
                    icons[6] = iconBase.getABLCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                if (!doBlocksMatch(world, x - 1, y + 1, z))
                    icons[7] = iconBase.getATRCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                if (!doBlocksMatch(world, x + 1, y + 1, z))
                    icons[8] = iconBase.getATLCorner();
            }
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x - 1, y, z))
                icons[5] = iconBase.getBRCorner();
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x + 1, y, z))
                icons[6] = iconBase.getBLCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x - 1, y, z))
                icons[7] = iconBase.getTRCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x + 1, y, z))
                icons[8] = iconBase.getTLCorner();
        } else if (side == 5) {
            if (!doBlocksMatch(world, x, y, z + 1))
                icons[1] = iconBase.getLeftEdge();
            if (!doBlocksMatch(world, x, y, z - 1))
                icons[2] = iconBase.getRightEdge();
            if (!doBlocksMatch(world, x, y + 1, z))
                icons[3] = iconBase.getTopEdge();
            if (!doBlocksMatch(world, x, y - 1, z))
                icons[4] = iconBase.getBottomEdge();
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                if (!doBlocksMatch(world, x, y - 1, z + 1))
                    icons[5] = iconBase.getABLCorner();
            }
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                if (!doBlocksMatch(world, x, y - 1, z - 1))
                    icons[6] = iconBase.getABRCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                if (!doBlocksMatch(world, x, y + 1, z + 1))
                    icons[7] = iconBase.getATLCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                if (!doBlocksMatch(world, x, y + 1, z - 1))
                    icons[8] = iconBase.getATRCorner();
            }
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x, y, z + 1))
                icons[5] = iconBase.getBLCorner();
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x, y, z - 1))
                icons[6] = iconBase.getBRCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x, y, z + 1))
                icons[7] = iconBase.getTLCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x, y, z - 1))
                icons[8] = iconBase.getTRCorner();
        } else if (side == 4) {
            if (!doBlocksMatch(world, x, y, z - 1))
                icons[1] = iconBase.getLeftEdge();
            if (!doBlocksMatch(world, x, y, z + 1))
                icons[2] = iconBase.getRightEdge();
            if (!doBlocksMatch(world, x, y + 1, z))
                icons[3] = iconBase.getTopEdge();
            if (!doBlocksMatch(world, x, y - 1, z))
                icons[4] = iconBase.getBottomEdge();
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                if (!doBlocksMatch(world, x, y - 1, z - 1))
                    icons[5] = iconBase.getABLCorner();
            }
            if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                if (!doBlocksMatch(world, x, y - 1, z + 1))
                    icons[6] = iconBase.getABRCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                if (!doBlocksMatch(world, x, y + 1, z - 1))
                    icons[7] = iconBase.getATLCorner();
            }
            if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                if (!doBlocksMatch(world, x, y + 1, z + 1))
                    icons[8] = iconBase.getATRCorner();
            }
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x, y, z - 1))
                icons[5] = iconBase.getBLCorner();
            if (!doBlocksMatch(world, x, y - 1, z) && !doBlocksMatch(world, x, y, z + 1))
                icons[6] = iconBase.getBRCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x, y, z - 1))
                icons[7] = iconBase.getTLCorner();
            if (!doBlocksMatch(world, x, y + 1, z) && !doBlocksMatch(world, x, y, z + 1))
                icons[8] = iconBase.getTRCorner();
            //} else if (side == 1) {
        } else {
            if (!doBlocksMatch(world, x - 1, y, z))
                icons[1] = iconBase.getLeftEdge();
            if (!doBlocksMatch(world, x + 1, y, z))
                icons[2] = iconBase.getRightEdge();
            if (!doBlocksMatch(world, x, y, z - 1))
                icons[3] = iconBase.getTopEdge();
            if (!doBlocksMatch(world, x, y, z + 1))
                icons[4] = iconBase.getBottomEdge();
            if (doBlocksMatch(world, x - 1, y, z) && doBlocksMatch(world, x, y, z - 1)) {
                if (!doBlocksMatch(world, x - 1, y, z - 1))
                    icons[5] = iconBase.getATLCorner();
            }
            if (doBlocksMatch(world, x + 1, y, z) && doBlocksMatch(world, x, y, z - 1)) {
                if (!doBlocksMatch(world, x + 1, y, z - 1))
                    icons[6] = iconBase.getATRCorner();
            }
            if (doBlocksMatch(world, x - 1, y, z) && doBlocksMatch(world, x, y, z + 1)) {
                if (!doBlocksMatch(world, x - 1, y, z + 1))
                    icons[7] = iconBase.getABLCorner();
            }
            if (doBlocksMatch(world, x + 1, y, z) && doBlocksMatch(world, x, y, z + 1)) {
                if (!doBlocksMatch(world, x + 1, y, z + 1))
                    icons[8] = iconBase.getABRCorner();
            }
            if (!doBlocksMatch(world, x - 1, y, z) && !doBlocksMatch(world, x, y, z - 1))
                icons[5] = iconBase.getTLCorner();
            if (!doBlocksMatch(world, x + 1, y, z) && !doBlocksMatch(world, x, y, z - 1))
                icons[6] = iconBase.getTRCorner();
            if (!doBlocksMatch(world, x - 1, y, z) && !doBlocksMatch(world, x, y, z + 1))
                icons[7] = iconBase.getBLCorner();
            if (!doBlocksMatch(world, x + 1, y, z) && !doBlocksMatch(world, x, y, z + 1))
                icons[8] = iconBase.getBRCorner();
        }
        return icons;
    }

    private void renderSideIcons(Tessellator tess, IBlockAccess world, int x, int y, int z, int side, int brightness, IIcon... icons) {
        switch (side) {
            case 0:
                tess.setBrightness(renderer.renderMinY > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y - 1, z));
                if (block.shouldSideBeRendered(world, x, y - 1, z, side) || renderer.renderAllFaces) {
                    for (IIcon icon : icons) {
                        if (icon != null)
                            renderer.renderFaceYNeg(block, x, y, z, icon);
                    }
                }
                break;
            case 1:
                tess.setBrightness(renderer.renderMaxY < 1.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y + 1, z));
                if (block.shouldSideBeRendered(world, x, y + 1, z, side) || renderer.renderAllFaces) {
                    for (IIcon icon : icons) {
                        if (icon != null)
                            renderer.renderFaceYPos(block, x, y, z, icon);
                    }
                }
                break;
            case 2:
                tess.setBrightness(renderer.renderMinZ > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y, z - 1));
                if (block.shouldSideBeRendered(world, x, y, z - 1, side) || renderer.renderAllFaces) {
                    for (IIcon icon : icons) {
                        if (icon != null)
                            renderer.renderFaceZNeg(block, x, y, z, icon);
                    }
                }
                break;
            case 3:
                tess.setBrightness(renderer.renderMaxZ < 1.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y, z + 1));
                if (block.shouldSideBeRendered(world, x, y, z + 1, side) || renderer.renderAllFaces) {
                    for (IIcon icon : icons) {
                        if (icon != null)
                            renderer.renderFaceZPos(block, x, y, z, icon);
                    }
                }
                break;
            case 4:
                tess.setBrightness(renderer.renderMinX > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x - 1, y, z));
                if (block.shouldSideBeRendered(world, x - 1, y, z, side) || renderer.renderAllFaces) {
                    for (IIcon icon : icons) {
                        if (icon != null)
                            renderer.renderFaceXNeg(block, x, y, z, icon);
                    }
                }
                break;
            case 5:
                tess.setBrightness(renderer.renderMaxX < 1.0D ? brightness : block.getMixedBrightnessForBlock(world, x + 1, y, z));
                if (block.shouldSideBeRendered(world, x + 1, y, z, side) || renderer.renderAllFaces) {
                    for (IIcon icon : icons) {
                        if (icon != null)
                            renderer.renderFaceXPos(block, x, y, z, icon);
                    }
                }
                break;
        }
    }

    private void renderStandardSide(Tessellator tess, IBlockAccess world, int x, int y, int z, IIcon icon, int side, int brightness) {
        renderSideIcons(tess, world, x, y, z, side, brightness, icon);
    }

    private boolean doBlocksMatch(IBlockAccess world, int x, int y, int z) {
        if (!world.isAirBlock(x, y, z)) {
            Block bl = world.getBlock(x, y, z);
            int blMeta = world.getBlockMetadata(x, y, z);
            return bl != null ? (bl == block && blMeta == meta) : false;
        }
        return false;
    }
}
