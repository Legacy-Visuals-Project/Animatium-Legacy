package org.polyfrost.animatium_legacy.hooks

import cc.polyfrost.oneconfig.libs.universal.UResolution
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11.glLineWidth
import org.polyfrost.animatium_legacy.util.MathUtils.lerp

object DebugCrosshairHook {
    @JvmStatic
    fun renderDirections(tickDelta: Float, mc: Minecraft) {
        GlStateManager.pushMatrix()
        GlStateManager.translate(UResolution.scaledWidth / 2.0F, UResolution.scaledHeight / 2.0F, 100.0F)

        val entity = mc.renderViewEntity
        GlStateManager.rotate(lerp(tickDelta, entity.prevRotationPitch, entity.rotationPitch), -1.0F, 0.0F, 0.0F)
        GlStateManager.rotate(lerp(tickDelta, entity.prevRotationYaw, entity.rotationYaw), 0.0F, 1.0F, 0.0F)
        GlStateManager.scale(-1.0F, -1.0F, -1.0F)
        GlStateManager.disableTexture2D()
        GlStateManager.depthMask(false)

        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.worldRenderer

        glLineWidth(4.0F)
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR)
        bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex()
        bufferBuilder.pos(10.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 10.0, 0.0).color(0, 0, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 0.0, 10.0).color(0, 0, 0, 255).endVertex()
        tessellator.draw()

        glLineWidth(2.0F)
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR)
        bufferBuilder.pos(0.0, 0.0, 0.0).color(255, 0, 0, 255).endVertex()
        bufferBuilder.pos(10.0, 0.0, 0.0).color(255, 0, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 255, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 10.0, 0.0).color(0, 255, 0, 255).endVertex()
        bufferBuilder.pos(0.0, 0.0, 0.0).color(127, 127, 255, 255).endVertex()
        bufferBuilder.pos(0.0, 0.0, 10.0).color(127, 127, 255, 255).endVertex()
        tessellator.draw()

        glLineWidth(1.0F)
        GlStateManager.depthMask(true)
        GlStateManager.enableTexture2D()
        GlStateManager.popMatrix()
    }
}