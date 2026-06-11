package org.polyfrost.animatium_legacy.hooks;

public final class SmoothSneakHook {
    private static float sneakingHeight;

    public static void setSneakingHeight(float sneakingHeight) {
        SmoothSneakHook.sneakingHeight = sneakingHeight;
    }

    public static float getSmoothSneak() {
        return sneakingHeight;
    }
}
