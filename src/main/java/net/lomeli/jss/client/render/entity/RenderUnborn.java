package net.lomeli.jss.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.jss.JSS;
import net.lomeli.jss.client.model.ModelUnborn;
import net.lomeli.jss.entities.EntityUnborn;

public class RenderUnborn extends RenderLiving {
    private IIcon circle;

    public RenderUnborn(IIcon circle) {
        super(new ModelUnborn(0.5F), 0F);
        this.circle = circle;
    }

    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float f0, float f1) {
        if (entity != null && entity instanceof EntityUnborn)
            doRender((EntityUnborn) entity, x, y, z, f0, f1);
    }

    private void doRender(EntityUnborn unborn, double x, double y, double z, float f0, float f1) {
        BossStatus.setBossStatus(unborn, false);
        super.doRender(unborn, x, y, z, f0, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ResourceUtil.getEntityTexture(JSS.MOD_ID, "unborn.png");
        //return getUnbornTexture((EntityUnborn) entity);
    }

    private ResourceLocation getUnbornTexture(EntityUnborn unborn) {
        return ResourceUtil.getEntityTexture(JSS.MOD_ID, "unborn_" + unborn.getAttackMode() + ".png");
    }
}
