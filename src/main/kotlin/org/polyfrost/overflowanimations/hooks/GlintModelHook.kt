package org.polyfrost.overflowanimations.hooks

import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldRenderer
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.client.resources.model.SimpleBakedModel
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import org.polyfrost.overflowanimations.mixin.interfaces.RenderItemInvoker

object GlintModelHook {
    private val glintMap = hashMapOf<HashedModel, IBakedModel>()

    fun getGlint(model: IBakedModel): IBakedModel =
        glintMap.computeIfAbsent(HashedModel(model)) {
            SimpleBakedModel.Builder(model, JustUV).makeBakedModel()
        }

    data class HashedModel(val data: List<Int>) {
        constructor(model: IBakedModel) : this(
            (EnumFacing.entries.flatMap { face -> model.getFaceQuads(face) } + model.generalQuads).flatMap {
                it.vertexData.slice(
                    0..2
                )
            }
        )
    }

    object JustUV : TextureAtlasSprite("uv") {
        override fun getInterpolatedU(u: Double) = -u.toFloat() / 16f
        override fun getInterpolatedV(v: Double) = v.toFloat() / 16f
    }

    fun renderGlintGui(x: Int, y: Int, glintTexture: ResourceLocation) {
        val red = 128 / 255f
        val green = 64 / 255f
        val blue = 204 / 255f
        val alpha = 255 / 255f

        val tesselator = Tessellator.getInstance()
        val vertexConsumer = tesselator.worldRenderer

        val currentTime = Minecraft.getSystemTime()
        val twentyPixels = 20.0 / 256.0
        val a = (currentTime % 3000L) / 3000.0
        val b = (currentTime % 4873L) / 4873.0

        GlStateManager.enableRescaleNormal()
        GlStateManager.depthFunc(GL11.GL_GEQUAL)
        GlStateManager.disableLighting()
        GlStateManager.depthMask(false)
        mc.textureManager.bindTexture(glintTexture)
        GlStateManager.enableAlpha()
        GlStateManager.alphaFunc(516, 0.1f)
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(772, 1, 0, 0)
        GlStateManager.color(red, green, blue, alpha)

        GlStateManager.pushMatrix()

        (mc.renderItem as RenderItemInvoker).invokeSetupGuiTransform(x, y, false)

        GlStateManager.scale(0.5f, 0.5f, 0.5f)
        GlStateManager.translate(-0.5f, -0.5f, -0.5f)

        vertexConsumer.begin(7, DefaultVertexFormats.POSITION_TEX)
        drawVertices(vertexConsumer, a, twentyPixels)
        drawVertices(vertexConsumer, b - twentyPixels, twentyPixels)
        tesselator.draw()

        GlStateManager.popMatrix()

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.depthMask(true)
        GlStateManager.enableLighting()
        GlStateManager.depthFunc(GL11.GL_LEQUAL)
        GlStateManager.disableAlpha()
        GlStateManager.disableRescaleNormal()
        GlStateManager.disableLighting()
        mc.textureManager.bindTexture(TextureMap.locationBlocksTexture)
    }

    private fun drawVertices(vertexConsumer: WorldRenderer, uOffset: Double, twentyPixels: Double) {
        vertexConsumer.run {
            pos(0.0, 0.0, 0.0).tex(uOffset + twentyPixels * 4.0, twentyPixels).endVertex()
            pos(1.0, 0.0, 0.0).tex(uOffset + twentyPixels * 5.0, twentyPixels).endVertex()
            pos(1.0, 1.0, 0.0).tex(uOffset + twentyPixels, 0.0).endVertex()
            pos(0.0, 1.0, 0.0).tex(uOffset, 0.0).endVertex()
        }
    }
}