package org.polyfrost.animatium_legacy.mixin.v3.general.network;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.BlockPos;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {
    @Shadow
    protected abstract boolean isHittingPosition(final BlockPos pos);

    @Shadow
    public abstract boolean getIsHittingBlock();

    @ModifyArg(method = {"clickBlock", "onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;sendBlockBreakProgress(ILnet/minecraft/util/BlockPos;I)V"), index = 2)
    private int animatium$fixDelay(final int original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.modernBreak) {
            return original + 1;
        } else {
            return original;
        }
    }

    @Redirect(method = "onPlayerDamageBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;isHittingPosition(Lnet/minecraft/util/BlockPos;)Z"))
    private boolean animatium$fixLogic(final PlayerControllerMP instance, final BlockPos pos) {
        final boolean isHittingPos = this.isHittingPosition(pos);
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.breakFix) {
            return isHittingPos && this.getIsHittingBlock();
        } else {
            return isHittingPos;
        }
    }
}
