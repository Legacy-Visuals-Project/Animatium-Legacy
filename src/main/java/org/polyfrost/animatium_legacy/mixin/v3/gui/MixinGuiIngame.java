package org.polyfrost.animatium_legacy.mixin.v3.gui;

import net.minecraft.client.gui.GuiIngame;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.DebugCrosshairMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiIngame.class, priority = 1001)
public abstract class MixinGuiIngame {
    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    private void animatium$renderCrosshair(final CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.debugCrosshairMode() == DebugCrosshairMode.V1_7) {
            cir.setReturnValue(true);
        }
    }
}
