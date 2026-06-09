package org.polyfrost.animatium_legacy.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.polyfrost.animatium_legacy.Animatium;
import org.polyfrost.animatium_legacy.mixin.interfaces.RendererLivingEntityInvoker;
import org.polyfrost.damagetint.config.DamageTintConfig;

public final class HitColorHook {
    public static void renderHitColorPre(final EntityLivingBase livingEntity, final boolean hurt, final float tickDelta, final RendererLivingEntity<?> instance) {
        float brightness = livingEntity.getBrightness(tickDelta);

        final int colorMultiplier = ((RendererLivingEntityInvoker) instance).animatium$getColorMultiplier(livingEntity, brightness, tickDelta);
        final boolean flag = (colorMultiplier >> 24 & 0xFF) > 0;

        final boolean isDT = Animatium.isDamageTintPresent;
        final float red = isDT ? DamageTintConfig.color.getRed() / 255.0F : brightness;
        final float green = isDT ? DamageTintConfig.color.getGreen() / 255.0F : 0.0F;
        final float blue = isDT ? DamageTintConfig.color.getBlue() / 255.0F : 0.0F;
        final float alpha = isDT ? DamageTintConfig.color.getAlpha() / 255.0F : 0.4F;

        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        if (flag || hurt) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.depthFunc(GL11.GL_EQUAL);
            GlStateManager.color(red, green, blue, alpha);
        }
    }

    public static void renderHitColorPost(final boolean hurt) {
        if (hurt) {
            GlStateManager.depthFunc(GL11.GL_LEQUAL);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
        }

        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }
}
