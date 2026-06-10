package org.polyfrost.animatium_legacy.mixin;

import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "gg.essential.handlers.OnlineIndicator", remap = false)
public abstract class OnlineIndicatorMixin {
    @Dynamic("Essential")
    @Inject(method = "drawTabIndicator", at = @At("HEAD"), cancellable = true, remap = false)
    private static void animatium$removeTabIndicator(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.tabMode == 2) {
            ci.cancel(); // TODO: WrapMethod
        }
    }
}
