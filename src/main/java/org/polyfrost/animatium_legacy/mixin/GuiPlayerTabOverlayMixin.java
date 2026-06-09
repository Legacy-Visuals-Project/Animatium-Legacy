package org.polyfrost.animatium_legacy.mixin;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.TabOverlayHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiPlayerTabOverlay.class, priority = 999)
public abstract class GuiPlayerTabOverlayMixin {

    @Shadow
    @Final
    private static Ordering<NetworkPlayerInfo> field_175252_a;

    @Inject(method = "renderPlayerlist", at = @At("HEAD"), cancellable = true)
    private void animatium$renderOldTab(int width, Scoreboard scoreboardIn, ScoreObjective var37, CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.tabMode == 0 && AnimatiumSettings.INSTANCE.enabled) {
            ci.cancel();
            TabOverlayHook.renderOldTab(((GuiPlayerTabOverlay) (Object) this), var37, field_175252_a);
        }
    }

    @ModifyVariable(method = "renderPlayerlist", at = @At("STORE"), index = 11)
    private boolean animatium$disablePlayerHead(boolean original) {
        return (AnimatiumSettings.INSTANCE.tabMode != 2 || !AnimatiumSettings.INSTANCE.enabled) && original;
    }
}
