package org.polyfrost.animatium_legacy.hooks

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RendererLivingEntity
import net.minecraft.entity.EntityLivingBase
import org.lwjgl.opengl.GL11.*
import org.polyfrost.animatium_legacy.Animatium
import org.polyfrost.animatium_legacy.mixin.accessor.RendererLivingEntityAccessor
import org.polyfrost.damagetint.config.DamageTintConfig

object HitColorHook {
    @JvmStatic
    fun preHitColor(
        livingEntity: EntityLivingBase,
        hurt: Boolean,
        tickDelta: Float,
        instance: RendererLivingEntity<*>
    ) {
        val brightness = livingEntity.getBrightness(tickDelta)

        val colorMultiplier = (instance as RendererLivingEntityAccessor).`animatium$getColorMultiplier`(
            livingEntity,
            brightness,
            tickDelta
        )
        val flag = ((colorMultiplier shr 24) and 0xFF) > 0

        val isDT = Animatium.isDamageTintPresent
        val red = if (isDT) DamageTintConfig.color.getRed() / 255.0F else brightness
        val green = if (isDT) DamageTintConfig.color.getGreen() / 255.0F else 0.0F
        val blue = if (isDT) DamageTintConfig.color.getBlue() / 255.0F else 0.0F
        val alpha = if (isDT) DamageTintConfig.color.getAlpha() / 255.0F else 0.4F

        Minecraft.getMinecraft().entityRenderer.disableLightmap()
        if (flag || hurt) {
            GlStateManager.disableTexture2D()
            GlStateManager.disableAlpha()
            GlStateManager.enableBlend()
            GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
            GlStateManager.depthFunc(GL_EQUAL)
            GlStateManager.color(red, green, blue, alpha)
        }
    }

    @JvmStatic
    fun postHitColor(hurt: Boolean) {
        if (hurt) {
            GlStateManager.depthFunc(GL_LEQUAL)
            GlStateManager.disableBlend()
            GlStateManager.enableAlpha()
            GlStateManager.enableTexture2D()
        }

        Minecraft.getMinecraft().entityRenderer.enableLightmap()
    }
}