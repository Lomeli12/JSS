package net.lomeli.jss.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import net.lomeli.lomlib.util.RenderUtils;

import net.lomeli.jss.client.handler.EventHandlerClient;
import net.lomeli.jss.entities.EntityUnborn;

/**
 * ModelUnborn - Lomeli12
 * Created using Tabula 5.1.0
 */
public class ModelUnborn extends ModelBase {
    public ModelRenderer headOverlay;
    public ModelRenderer rightArm;
    public ModelRenderer rightLeg;
    public ModelRenderer headMain;
    public ModelRenderer chest;
    public ModelRenderer leftArm;
    public ModelRenderer leftLeg;
    public ModelRenderer circle;

    public ModelUnborn() {
        this(0f);
    }

    public ModelUnborn(float f0) {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.rightArm = new ModelRenderer(this, 40, 16);
        this.rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, f0);
        this.setRotateAngle(rightArm, 0.0F, 0.0F, 0.10000736613927509F);
        this.chest = new ModelRenderer(this, 16, 16);
        this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, f0);
        this.leftArm = new ModelRenderer(this, 32, 48);
        this.leftArm.setRotationPoint(5.0F, 2.0F, -0.0F);
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, f0);
        this.setRotateAngle(leftArm, 0.0F, 0.0F, -0.10000736613927509F);
        this.circle = new ModelRenderer(this, 0, 64);
        this.circle.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.circle.addBox(-15.0F, -15.0F, 0.0F, 30, 30, 0, f0);
        this.headMain = new ModelRenderer(this, 0, 0);
        this.headMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headMain.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f0);
        this.headOverlay = new ModelRenderer(this, 32, 0);
        this.headOverlay.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headOverlay.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f0);
        this.rightLeg = new ModelRenderer(this, 0, 16);
        this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f0);
        this.leftLeg = new ModelRenderer(this, 16, 48);
        this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f0);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity != null && entity instanceof EntityUnborn)
            renderUnborn(f5, ((EntityUnborn) entity).getAttackMode());
    }

    public void renderUnborn(float f5, int mode) {
        GL11.glPushMatrix();
        RenderUtils.applyColor(getColorFromMode(mode));
        float angle = EventHandlerClient.clientTicks % 360f;
        GL11.glRotatef(angle, 0f, 0f, 1f);
        this.circle.render(f5);
        RenderUtils.resetColor();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        RenderUtils.applyColor(1f, 1f, 1f, 0.6f);
        this.rightArm.render(f5);
        this.chest.render(f5);
        this.leftArm.render(f5);
        this.headMain.render(f5);
        this.headOverlay.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        RenderUtils.resetColor();
        GL11.glPopMatrix();
    }

    private int getColorFromMode(int mode) {
        switch (mode) {
            case 1:
                return 0xD10A0A;
            case 2:
                return 0xC7C7C7;
            case 3:
                return 0x0AD10A;
            case 4:
                return 0xFF00E1;
            case 5:
                return 0xFFFF40;
            default:
                return 0xFFFFFF;
        }
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
