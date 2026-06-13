package org.polyfrost.animatium_legacy.hooks

import club.sk1er.patcher.config.PatcherConfig
import org.polyfrost.animatium_legacy.Animatium

object PatcherConfigHook {
    @JvmStatic
    fun isParallaxFixEnabled(): Boolean = Animatium.isPatcherPresent && PatcherConfig.parallaxFix
}