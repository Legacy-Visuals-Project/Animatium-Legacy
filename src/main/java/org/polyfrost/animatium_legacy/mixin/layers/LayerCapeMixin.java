package org.polyfrost.animatium_legacy.mixin.layers;

import net.minecraft.client.renderer.entity.layers.LayerCape;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerCape.class)
public abstract class LayerCapeMixin {
    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$allowCombine(final CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.damageCape);
    }
}
