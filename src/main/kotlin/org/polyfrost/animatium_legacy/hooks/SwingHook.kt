package org.polyfrost.animatium_legacy.hooks

import net.minecraft.client.Minecraft
import org.polyfrost.animatium_legacy.mixin.accessor.EntityLivingBaseAccessor

object SwingHook {
    @JvmStatic
    fun swingHand() {
        val player = Minecraft.getMinecraft().thePlayer ?: return
        val swingDuration = (player as EntityLivingBaseAccessor).`animatium$getArmSwingAnimation`()
        if (!player.isSwingInProgress || player.swingProgressInt >= swingDuration / 2 || player.swingProgressInt < 0) {
            player.swingProgressInt = -1
            player.isSwingInProgress = true
        }
    }
}