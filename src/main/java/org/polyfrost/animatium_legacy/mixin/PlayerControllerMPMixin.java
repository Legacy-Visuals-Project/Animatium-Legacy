package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.BlockPos;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {
    @Shadow
    protected abstract boolean isHittingPosition(final BlockPos pos);

    @Shadow
    public abstract boolean getIsHittingBlock();

    @ModifyArg(method = {"clickBlock", "onPlayerDamageBlock"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;sendBlockBreakProgress(ILnet/minecraft/util/BlockPos;I)V"), index = 2)
    private int animatium$fixDelay(final int original) {
        return original + (AnimatiumSettings.modernBreak && AnimatiumSettings.INSTANCE.enabled ? 1 : 0);
    }

    @Redirect(method = "onPlayerDamageBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;isHittingPosition(Lnet/minecraft/util/BlockPos;)Z"))
    private boolean animatium$fixLogic(final PlayerControllerMP instance, final BlockPos pos) {
        return AnimatiumSettings.breakFix && AnimatiumSettings.INSTANCE.enabled ? isHittingPosition(pos) && getIsHittingBlock() : isHittingPosition(pos);
    }
}
