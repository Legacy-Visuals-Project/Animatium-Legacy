package org.polyfrost.animatium_legacy.util

object MathUtils {
    @JvmStatic
    fun lerp(tickDelta: Float, start: Float, end: Float): Float =
        start + tickDelta * (end - start)
}