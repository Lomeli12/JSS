package net.lomeli.jss.client.render.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/**
 * Created by lomeli12 on 11/9/14.
 */
public class IconConnected implements IIcon {
    private IIcon[] icons;
    private int iconIndex;

    public IconConnected(IIconRegister register, String iconBaseName) {
        icons = new IIcon[6];
        icons[0] = register.registerIcon(iconBaseName + "_full");
        icons[1] = register.registerIcon(iconBaseName + "_base");
        icons[2] = register.registerIcon(iconBaseName + "_edge_type_0");
        icons[3] = register.registerIcon(iconBaseName + "_edge_type_1");
        icons[4] = register.registerIcon(iconBaseName + "_corner");
        icons[5] = register.registerIcon(iconBaseName + "_anti_corner");
        iconIndex = 0;
    }

    public int getIconIndex() {
        return iconIndex;
    }

    public void setIconIndex(int index) {
        if (index >= 0 && index < icons.length)
            iconIndex = index;
    }

    public void setIcon(int index, IIcon icon) {
        if (index >= 0 && index < icons.length && icon != null)
            icons[index] = icon;
    }

    @Override
    public int getIconWidth() {
        return this.icons[this.iconIndex].getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.icons[this.iconIndex].getIconHeight();
    }

    @Override
    public float getMinU() {
        return this.icons[this.iconIndex].getMinU();
    }

    @Override
    public float getMaxU() {
        return this.icons[this.iconIndex].getMaxU();
    }

    @Override
    public float getInterpolatedU(double d0) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float) d0 / 16F);
    }

    @Override
    public float getMinV() {
        return this.icons[this.iconIndex].getMinV();
    }

    @Override
    public float getMaxV() {
        return this.icons[this.iconIndex].getMaxV();
    }

    @Override
    public float getInterpolatedV(double d0) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float) d0 / 16F);
    }

    @Override
    public String getIconName() {
        return this.icons[this.iconIndex].getIconName();
    }

    public IIcon getFull() {
        return this.icons[0];
    }

    public IIcon getBase() {
        return this.icons[1];
    }

    public IIcon getTopEdge() {
        return this.icons[2];
    }

    public IIcon getBottomEdge() {
        return new IconReverse(this.icons[2]);
    }

    public IIcon getLeftEdge() {
        return this.icons[3];
    }

    public IIcon getRightEdge() {
        return new IconReverse(this.icons[3]);
    }

    public IIcon getBLCorner() {
        return this.icons[4];
    }

    public IIcon getTLCorner() {
        return new IconReverse(this.icons[4], 2);
    }

    public IIcon getBRCorner() {
        return new IconReverse(this.icons[4], 1);
    }

    public IIcon getTRCorner() {
        return new IconReverse(this.icons[4]);
    }

    public IIcon getABLCorner() {
        return this.icons[5];
    }

    public IIcon getATLCorner() {
        return new IconReverse(this.icons[5], 2);
    }

    public IIcon getABRCorner() {
        return new IconReverse(this.icons[5], 1);
    }

    public IIcon getATRCorner() {
        return new IconReverse(this.icons[5]);
    }
}
