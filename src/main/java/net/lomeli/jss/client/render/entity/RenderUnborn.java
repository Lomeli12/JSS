package net.lomeli.jss.client.render.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.util.RenderUtils;
import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.jss.JSS;
import net.lomeli.jss.entities.EntityUnborn;

public class RenderUnborn extends RenderLiving {
    private IIcon circle;

    public RenderUnborn(IIcon circle) {
        super(new ModelBiped(0.5F), 0F);
        this.circle = circle;
    }

    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float f0, float f1) {
        doRender((EntityUnborn) entity, x, y, z, f0, f1);
    }

    private void doRender(EntityUnborn unborn, double x, double y, double z, float f0, float f1) {
        BossStatus.setBossStatus(unborn, false);
        //TODO: FANCY SHIT!!
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1f, 1f, 1f, 0.5f);
        super.doRender(unborn, x, y, z, f0, f1);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        /*
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(x, y, z);

        GL11.glRotatef(-90f, 0f, 0f, 1f);
        GL11.glRotatef(180f, 0f, 1f, 0f);

        renderCircle(unborn, x, y + 1.5f, z);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();*/

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderCircle(EntityUnborn unborn, double x, double y, double z) {
        if (circle == null)
            return;
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        if (textureManager == null)
            return;

        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.5F, -0.5F, 1 / 32.0F);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        textureManager.bindTexture(TextureMap.locationBlocksTexture);
        float minU = circle.getMinU();
        float maxU = circle.getMaxU();
        float minV = circle.getMinV();
        float maxV = circle.getMaxV();
        RenderUtils.applyColor(getColorFromMode(unborn.getAttackMode()), 0.6f);
        ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, circle.getIconWidth(), circle.getIconHeight(), 0.0625F);
        RenderUtils.resetColor();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    private int getColorFromMode(int mode) {
        switch (mode) {
            case 1:
                return 0xC40000;
            case 2:
                return 0x968357;
            case 3:
                return 0x9CFDFF;
            case 4:
                return 0x000000;
            default:
                return 0xFFFFFF;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ResourceUtil.getEntityTexture(JSS.MOD_ID, "unborn.png");
    }
}
