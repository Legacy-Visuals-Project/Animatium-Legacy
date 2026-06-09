package org.polyfrost.animatium_legacy.mixin.interfaces;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiPlayerTabOverlay.class)
public interface GuiPlayerTabOverlayInvoker {
    @Invoker("drawPing")
    void animatium$drawPing(final int i, final int j, final int k, final NetworkPlayerInfo playerInfo);
}
