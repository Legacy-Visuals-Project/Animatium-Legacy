package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.network.NetHandlerPlayClient;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    @ModifyConstant(method = "handleSpawnExperienceOrb", constant = @Constant(doubleValue = 32.0D))
    private double animatium$oldXPOrbs(final double original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldXPOrbsPosition) {
            return original / 32.0D;
        } else {
            return original;
        }
    }
}
