package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.gui.GuiOverlayDebug;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.DebugOverlayHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GuiOverlayDebug.class)
public abstract class GuiOverlayDebugMixin {

    @Inject(method = "call", at = @At("HEAD"), cancellable = true)
    private void animatium$oldDebugLeft(CallbackInfoReturnable<List<String>> cir) {
        if (AnimatiumSettings.INSTANCE.debugScreenMode == 0 && AnimatiumSettings.INSTANCE.enabled) {
            cir.setReturnValue(DebugOverlayHook.getDebugInfoLeft());
        }
    }

    @Inject(method = "getDebugInfoRight", at = @At("HEAD"), cancellable = true)
    private void animatium$oldDebugRight(CallbackInfoReturnable<List<String>> cir) {
        if (AnimatiumSettings.INSTANCE.debugScreenMode == 0 && AnimatiumSettings.INSTANCE.enabled) {
            cir.setReturnValue(DebugOverlayHook.getDebugInfoRight());
        }
    }
}
