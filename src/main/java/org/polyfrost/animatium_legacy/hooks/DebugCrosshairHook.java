package org.polyfrost.animatium_legacy.hooks;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.polyfrost.animatium_legacy.util.MathUtils;

public final class DebugCrosshairHook {
    public static void renderDirections(final float tickDelta, final Minecraft mc) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) (UResolution.getScaledWidth() / 2), (float) (UResolution.getScaledHeight() / 2), 100);
        Entity entity = mc.getRenderViewEntity();
        GlStateManager.rotate(MathUtils.lerp(tickDelta, entity.prevRotationPitch, entity.rotationPitch), -1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(MathUtils.lerp(tickDelta, entity.prevRotationYaw, entity.rotationYaw), 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, -1.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GL11.glLineWidth(4.0F);
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(10, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 10, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 10).color(0, 0, 0, 255).endVertex();
        tessellator.draw();

        GL11.glLineWidth(2.0F);
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(0.0, 0.0, 0.0).color(255, 0, 0, 255).endVertex();
        worldRenderer.pos(10, 0.0, 0.0).color(255, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 255, 0, 255).endVertex();
        worldRenderer.pos(0.0, 10, 0.0).color(0, 255, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(127, 127, 255, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 10).color(127, 127, 255, 255).endVertex();
        tessellator.draw();

        GL11.glLineWidth(1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
