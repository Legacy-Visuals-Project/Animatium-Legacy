package org.polyfrost.animatium_legacy.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.polyfrost.animatium_legacy.mixin.accessor.EntityLivingBaseAccessor;

public final class SwingHook {
    public static void swingItem() {
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (!player.isSwingInProgress || player.swingProgressInt >= ((EntityLivingBaseAccessor) player).animatium$getArmSwingAnimation() / 2 || player.swingProgressInt < 0) {
            player.swingProgressInt = -1;
            player.isSwingInProgress = true;
        }
    }
}
