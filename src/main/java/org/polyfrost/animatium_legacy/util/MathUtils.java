package org.polyfrost.animatium_legacy.util;

public final class MathUtils {
    public static float lerp(final float tickDelta, final float start, final float end) {
        return start + tickDelta * (end - start);
    }
}
