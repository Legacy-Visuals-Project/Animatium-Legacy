package org.polyfrost.animatium_legacy.mixin.compat;

import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Pseudo
@Mixin(targets = {"io.github.moulberry.notenoughupdates.miscfeatures.ItemCustomizeManager", "io.github.moulberry.notenoughupdates.miscgui.itemcustomization.ItemCustomizeManager" /* NEU 2.3.1+ */})
public class ItemCustomizeManagerMixin {
    @Dynamic
    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 0)
    private static float animatium$modifySpeed(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlint) {
            return original * 64.0F;
        } else {
            return original;
        }
    }

    @Dynamic
    @ModifyArgs(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private static void animatium$modifyScale(final Args args) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlint) {
            for (int i : new int[]{0, 1, 2}) {
                args.set(i, 1 / (float) args.get(i));
            }
        }
    }
}