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
    private void animatium$renderOldTab(final int width, final Scoreboard scoreboardIn, final ScoreObjective objective, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.tabMode == 0) {
            ci.cancel();
            TabOverlayHook.renderOldTab(((GuiPlayerTabOverlay) (Object) this), objective, field_175252_a);
        }
    }

    @ModifyVariable(method = "renderPlayerlist", at = @At("STORE"), name = "bl")
    private boolean animatium$disablePlayerHead(final boolean original) {
        return original && (!AnimatiumSettings.INSTANCE.enabled || AnimatiumSettings.INSTANCE.tabMode != 2);
    }
}
