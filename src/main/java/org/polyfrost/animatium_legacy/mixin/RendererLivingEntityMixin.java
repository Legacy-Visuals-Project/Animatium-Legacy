package org.polyfrost.animatium_legacy.mixin;

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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin<T extends EntityLivingBase> extends Render<T> {
    protected RendererLivingEntityMixin(final RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void animatium$movePlayerModel(final T entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.smoothModelSneak && AnimatiumSettings.INSTANCE.enabled && entity instanceof EntityPlayerSP && entity.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, -0.2F, 0.0F);
            }

            GlStateManager.translate(0.0F, 1.62F - SmoothSneakHook.getSmoothSneak(entity.getEyeHeight()), 0.0F);
        }
    }

    @Inject(method = "rotateCorpse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift = At.Shift.AFTER))
    private void animatium$rotateCorpse(final T bat, final float p_77043_2_, final float p_77043_3_, final float tickDelta, final CallbackInfo ci) {
        final boolean isLocalPlayer = bat.getName().equals(Minecraft.getMinecraft().thePlayer.getName());
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.dinnerBoneMode && isLocalPlayer) {
                animatium$dinnerboneRotation(bat);
            } else if (AnimatiumSettings.dinnerBoneModeEntities && !isLocalPlayer) {
                animatium$dinnerboneRotation(bat);
            }
        }
    }

    @Unique
    private static void animatium$dinnerboneRotation(final EntityLivingBase entity) {
        GlStateManager.translate(0.0f, entity.height + 0.1f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
    }
}
