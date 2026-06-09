package org.polyfrost.animatium_legacy.hooks;

import club.sk1er.patcher.config.PatcherConfig;
import org.polyfrost.animatium_legacy.Animatium;

public final class PatcherConfigHook {
    public static boolean isParallaxFixEnabled() {
        return Animatium.isPatcherPresent && PatcherConfig.parallaxFix;
    }
}
