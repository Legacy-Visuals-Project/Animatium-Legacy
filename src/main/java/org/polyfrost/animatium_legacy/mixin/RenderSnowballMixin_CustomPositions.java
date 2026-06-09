package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSnowball.class)
public abstract class RenderSnowballMixin_CustomPositions<T extends Entity> extends Render<T> {
    public RenderSnowballMixin_CustomPositions(final RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderSnowball;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", shift = At.Shift.AFTER))
    private void animatium$projectileTransforms(final T entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.globalPositions) {
            final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
            GlStateManager.translate(advanced.projectilePositionX, advanced.projectilePositionY, advanced.projectilePositionZ);
            GlStateManager.rotate(advanced.projectileRotationPitch, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(advanced.projectileRotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(advanced.projectileRotationRoll, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(1.0F * Math.exp(advanced.projectileScale), 1.0F * Math.exp(advanced.projectileScale), 1.0F * Math.exp(advanced.projectileScale));
        }
    }
}
