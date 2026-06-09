package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSnowball.class)
public abstract class RenderSnowballMixin<T extends Entity> extends Render<T> {
    public RenderSnowballMixin(final RenderManager renderManager) {
        super(renderManager);
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float animatium$fixRotationY(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && (AnimatiumSettings.itemSprites || AnimatiumSettings.oldProjectiles)) {
            return original + 180.0F;
        } else {
            return original;
        }
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float animatium$fixRotationX(final float original) {
        return (AnimatiumSettings.INSTANCE.enabled && (AnimatiumSettings.itemSprites || AnimatiumSettings.oldProjectiles) ? -1F : 1F) * original;
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void animatium$shiftProjectile(final T entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldProjectiles) {
            GlStateManager.translate(0.0F, 0.25F, 0.0F);
        }
    }
}
