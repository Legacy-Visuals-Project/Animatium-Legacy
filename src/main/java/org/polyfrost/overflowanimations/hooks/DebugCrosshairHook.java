package org.polyfrost.overflowanimations.hooks;

import dev.deftu.omnicore.api.client.render.OmniResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.util.MathUtils;

public class DebugCrosshairHook {

    public static void renderDirections(float partialTicks, Minecraft mc) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(OmniResolution.getScaledWidth() / 2), (float)(OmniResolution.getScaledHeight() / 2), 100);
        Entity entity = mc.getRenderViewEntity();
        GlStateManager.rotate(MathUtils.interp(entity.prevRotationPitch, entity.rotationPitch, partialTicks), -1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(MathUtils.interp(entity.prevRotationYaw , entity.rotationYaw, partialTicks), 0.0F, 1.0F, 0.0F);
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
