package org.polyfrost.animatium_legacy.mixin.v3.entity.armor_hurt.layers;

import net.minecraft.client.renderer.entity.layers.LayerCape;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerCape.class)
public abstract class MixinLayerCape {
    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$allowCombine(final CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.damageTintCape) {
            cir.setReturnValue(true);
        }
    }
}
