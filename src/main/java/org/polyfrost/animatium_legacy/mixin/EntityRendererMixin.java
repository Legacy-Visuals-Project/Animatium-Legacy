package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.DebugCrosshairHook;
import org.polyfrost.animatium_legacy.hooks.DebugOverlayHook;
import org.polyfrost.animatium_legacy.hooks.SmoothSneakHook;
import org.polyfrost.animatium_legacy.util.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = 1001)
public abstract class EntityRendererMixin {
    @Shadow
    private Minecraft mc;

    @Shadow
    public abstract void setupOverlayRendering();

    @Unique
    private float animatium$height;

    @Unique
    private float animatium$previousHeight;

    @Inject(method = "setupCameraTransform", at = @At("HEAD"))
    protected void animatium$getInterpolatedEyeHeight(float tickDelta, int pass, CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            SmoothSneakHook.setSneakingHeight(MathUtils.lerp(tickDelta, this.animatium$previousHeight, this.animatium$height));
        }
    }

    @ModifyVariable(method = "orientCamera", at = @At(value = "STORE", ordinal = 0), name = "f")
    private float animatium$modifyEyeHeight(final float original) {
        return SmoothSneakHook.getSmoothSneak(original);
    }

    @ModifyArg(method = "renderWorldDirections", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 1)
    private float animatium$syncCrossHair(final float original) {
        return SmoothSneakHook.getSmoothSneak(original);
    }

    // TODO: WrapMethod
    @Inject(method = "renderWorldDirections", at = @At("HEAD"), cancellable = true)
    private void animatium$renderCrosshair(final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.debugCrosshairMode != 1) {
            ci.cancel();
        }
    }

    @Inject(method = "updateRenderer", at = @At("HEAD"))
    private void animatium$interpolateHeight(CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            final Entity entity = this.mc.getRenderViewEntity();
            final float eyeHeight = entity.getEyeHeight();
            this.animatium$previousHeight = this.animatium$height;
            if (AnimatiumSettings.longerUnsneak) {
                if (eyeHeight < this.animatium$height) {
                    this.animatium$height = eyeHeight;
                } else {
                    this.animatium$height += (eyeHeight - this.animatium$height) * 0.5f;
                }
            } else {
                this.animatium$height = eyeHeight;
            }

            DebugOverlayHook.setEyeHeight(this.animatium$height);
        }
    }

    // TODO: WrapMethod
    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    private void animatium$cancelHurtCamera(final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.noHurtCam && AnimatiumSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 2))
    private void animatium$modernBobbing(final float angle, final float x, final float y, final float z) {
        if (!AnimatiumSettings.INSTANCE.enabled || !AnimatiumSettings.modernBobbing) {
            GlStateManager.rotate(angle, x, y, z);
        }
    }

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V"))
    private void draw(float tickDelta, long nanoTime, CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.debugCrosshairMode == 2 && this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            this.setupOverlayRendering();
            DebugCrosshairHook.renderDirections(tickDelta, this.mc);
        }
    }
}
