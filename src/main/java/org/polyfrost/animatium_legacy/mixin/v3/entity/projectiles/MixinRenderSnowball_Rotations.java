package org.polyfrost.animatium_legacy.mixin.v3.entity.projectiles;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RenderSnowball.class)
public abstract class MixinRenderSnowball_Rotations<T extends Entity> extends Render<T> {
    public MixinRenderSnowball_Rotations(final RenderManager renderManager) {
        super(renderManager);
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float animatium$fixRotationY(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && (AnimatiumSettings.itemSprites || AnimatiumSettings.oldProjectilesPosition)) {
            return original + 180.0F;
        } else {
            return original;
        }
    }

    @ModifyArg(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float animatium$fixRotationX(final float original) {
        return (AnimatiumSettings.INSTANCE.enabled && (AnimatiumSettings.itemSprites || AnimatiumSettings.oldProjectilesPosition) ? -1F : 1F) * original;
    }
}
