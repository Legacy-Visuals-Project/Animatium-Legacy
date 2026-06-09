package org.polyfrost.animatium_legacy.hooks;

import org.polyfrost.animatium_legacy.Animatium;

public final class DroppedItemHook {
    public static boolean isItemDropped;

    public static boolean isItemPhysicsAndEntityDropped() {
        return Animatium.isItemPhysics && isItemDropped;
    }
}