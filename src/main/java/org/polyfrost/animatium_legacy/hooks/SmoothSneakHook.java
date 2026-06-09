package org.polyfrost.animatium_legacy.hooks;

import net.minecraft.client.Minecraft;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;

public final class SmoothSneakHook {
    private static float sneakingHeight;

    public static void setSneakingHeight(float sneakingHeight) {
        SmoothSneakHook.sneakingHeight = sneakingHeight;
    }

    public static float getSmoothSneak(final float originalEyeHeight) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.smoothSneaking) {
            return sneakingHeight;
        } else {
            return originalEyeHeight;
        }
    }
}
