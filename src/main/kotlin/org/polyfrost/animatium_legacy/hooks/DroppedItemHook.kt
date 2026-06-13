package org.polyfrost.animatium_legacy.hooks

import org.polyfrost.animatium_legacy.Animatium

object DroppedItemHook {
    @JvmStatic
    var isItemDropped = false

    @JvmStatic
    fun isItemPhysicsAndEntityDropped() = Animatium.isItemPhysics && isItemDropped
}