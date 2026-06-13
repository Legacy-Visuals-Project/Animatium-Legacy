package org.polyfrost.animatium_legacy.hooks

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
import org.lwjgl.opengl.GL11.*
import org.polyfrost.animatium_legacy.mixin.accessor.RenderItemAccessor

object GlintModelHook {
    private val glintMap = hashMapOf<HashedModel, IBakedModel>()

    @JvmStatic
    fun getGlint(model: IBakedModel): IBakedModel =
        glintMap.computeIfAbsent(HashedModel(model)) {
            SimpleBakedModel.Builder(model, JustUV).makeBakedModel()
        }

    @JvmStatic
    fun renderGlintGui(x: Int, y: Int, glintTexture: ResourceLocation) {
        val red = 128 / 255.0F
        val green = 64 / 255.0F
        val blue = 204 / 255.0F
        val alpha = 255 / 255.0F

        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.worldRenderer

        val currentTime = Minecraft.getSystemTime()
        val twentyPixels = 20.0 / 256.0
        val a = (currentTime % 3000L) / 3000.0
        val b = (currentTime % 4873L) / 4873.0

        GlStateManager.enableRescaleNormal()
        GlStateManager.depthFunc(GL_GEQUAL)
        GlStateManager.disableLighting()
        GlStateManager.depthMask(false)
        mc.textureManager.bindTexture(glintTexture)
        GlStateManager.enableAlpha()
        GlStateManager.alphaFunc(GL_GREATER, 0.1F)
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(GL_DST_ALPHA, GL_ONE, GL_ZERO, GL_ZERO)
        GlStateManager.color(red, green, blue, alpha)

        GlStateManager.pushMatrix()
        (mc.renderItem as RenderItemAccessor).`animatium$setupGuiTransform`(x, y, false)
        GlStateManager.scale(0.5F, 0.5F, 0.5F)
        GlStateManager.translate(-0.5F, -0.5F, -0.5F)
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
        this.drawVertices(bufferBuilder, a, twentyPixels)
        this.drawVertices(bufferBuilder, b - twentyPixels, twentyPixels)
        tessellator.draw()
        GlStateManager.popMatrix()

        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO)
        GlStateManager.depthMask(true)
        GlStateManager.enableLighting()
        GlStateManager.depthFunc(GL_LEQUAL)
        GlStateManager.disableAlpha()
        GlStateManager.disableRescaleNormal()
        GlStateManager.disableLighting()
        mc.textureManager.bindTexture(TextureMap.locationBlocksTexture)
    }

    private fun drawVertices(bufferBuilder: WorldRenderer, uOffset: Double, twentyPixels: Double) {
        bufferBuilder.run {
            pos(0.0, 0.0, 0.0).tex(uOffset + twentyPixels * 4.0, twentyPixels).endVertex()
            pos(1.0, 0.0, 0.0).tex(uOffset + twentyPixels * 5.0, twentyPixels).endVertex()
            pos(1.0, 1.0, 0.0).tex(uOffset + twentyPixels, 0.0).endVertex()
            pos(0.0, 1.0, 0.0).tex(uOffset, 0.0).endVertex()
        }
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
}