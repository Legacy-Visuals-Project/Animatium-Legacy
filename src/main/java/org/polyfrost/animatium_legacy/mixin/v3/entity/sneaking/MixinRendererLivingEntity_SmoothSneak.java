package org.polyfrost.animatium_legacy.mixin.v3.entity.sneaking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.SmoothSneakHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity_SmoothSneak<T extends EntityLivingBase> extends Render<T> {
    protected MixinRendererLivingEntity_SmoothSneak(final RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void animatium$sneakModelOffset(final T entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.smoothModelSneak && entity instanceof EntityPlayerSP && entity.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, -0.2F, 0.0F);
            }

            final float eyeHeightOffset = AnimatiumSettings.smoothSneaking ? SmoothSneakHook.getSmoothSneak() : entity.getEyeHeight();
            GlStateManager.translate(0.0F, 1.62F - eyeHeightOffset, 0.0F);
        }
    }
}
