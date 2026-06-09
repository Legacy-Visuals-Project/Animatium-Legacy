package org.polyfrost.animatium_legacy.mixin.layers;

import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.passive.EntityWolf;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerWolfCollar.class)
public abstract class LayerWolfCollarMixin {
    @Shadow
    @Final
    private RenderWolf wolfRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/passive/EntityWolf;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void animatium$renderHitColor(final EntityWolf wolf, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle == 1) {
            final boolean hurt = wolf.hurtTime > 0 || wolf.deathTime > 0;
            HitColorHook.renderHitColorPre(wolf, hurt, tickDelta, this.wolfRenderer);
            if (hurt) {
                this.wolfRenderer.getMainModel().render(wolf, f, g, h, i, j, scale);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
