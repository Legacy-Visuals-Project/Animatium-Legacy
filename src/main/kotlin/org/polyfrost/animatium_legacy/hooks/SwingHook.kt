package org.polyfrost.animatium_legacy.hooks

import net.minecraft.client.Minecraft
import org.polyfrost.animatium_legacy.config.AnimatiumSettings
import org.polyfrost.animatium_legacy.mixin.accessor.EntityLivingBaseAccessor

object SwingHook {
    @JvmStatic
    fun swingHand() {
        if (this.canSwingHand()) {
            this.forceSwingHand()
        }
    }

    @JvmStatic
    fun forceSwingHand() {
        val player = Minecraft.getMinecraft().thePlayer ?: return
        player.swingProgressInt = -1
        player.isSwingInProgress = true
    }

    @JvmStatic
    fun canSwingHand(): Boolean {
        val player = Minecraft.getMinecraft().thePlayer ?: return false
        val swingDuration = (player as EntityLivingBaseAccessor).`animatium$getArmSwingAnimation`()
        return AnimatiumSettings.betaHandSwing || (!player.isSwingInProgress || player.swingProgressInt >= swingDuration / 2 || player.swingProgressInt < 0)
    }
}