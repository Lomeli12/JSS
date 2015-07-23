package net.lomeli.jss.client.render.blocks;

import net.minecraft.util.IIcon;

/**
 * Created by lomeli12 on 11/9/14.
 */
public class IconReverse implements IIcon {
    private IIcon icon;
    private int type;

    /**
     * @param icon
     * @param type: 0 - Flip U and V; 1 - Flip U; 2 - Flip V
     */
    public IconReverse(IIcon icon, int type) {
        this.icon = icon;
        this.type = type;
    }

    public IconReverse(IIcon icon) {
        this(icon, 0);
    }

    @Override
    public int getIconWidth() {
        return this.icon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.icon.getIconHeight();
    }

    @Override
    public float getMinU() {
        return (type == 0 || type == 1) ? this.icon.getMaxU() : this.icon.getMinU();
    }

    @Override
    public float getMaxU() {
        return (type == 0 || type == 1) ? this.icon.getMinU() : this.icon.getMaxU();
    }

    @Override
    public float getInterpolatedU(double d) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float) d / 16F);
    }

    @Override
    public float getMinV() {
        return (type == 0 || type == 2) ? this.icon.getMaxV() : this.icon.getMinV();
    }

    @Override
    public float getMaxV() {
        return (type == 0 || type == 2) ? this.icon.getMinV() : this.icon.getMaxV();
    }

    @Override
    public float getInterpolatedV(double d) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float) d / 16F);
    }

    @Override
    public String getIconName() {
        return this.icon.getIconName();
    }
}
