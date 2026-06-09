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

    protected RendererLivingEntityMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void animatium$movePlayerModel(T entity, double x, double y, double z, float entityYaw, float tickDelta, CallbackInfo ci) {
        if (AnimatiumSettings.smoothModelSneak && AnimatiumSettings.INSTANCE.enabled &&
                entity instanceof EntityPlayerSP && entity.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, -0.2F, 0.0F);
            }
            GlStateManager.translate(0.0F, 1.62F - SmoothSneakHook.getSmoothSneak(), 0.0F);
        }
    }

    @Inject(method = "rotateCorpse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift = At.Shift.AFTER))
    private void animatium$rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float tickDelta, CallbackInfo ci) {
        boolean player = bat.getName().equals(Minecraft.getMinecraft().thePlayer.getName());
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.dinnerBoneMode && player) {
                animatium$dinnerboneRotation(bat);
            } else if (AnimatiumSettings.dinnerBoneModeEntities && !player) {
                animatium$dinnerboneRotation(bat);
            }
        }
    }

    @Unique
    private static void animatium$dinnerboneRotation(EntityLivingBase entity) {
        GlStateManager.translate(0.0f, entity.height + 0.1f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
    }

}
