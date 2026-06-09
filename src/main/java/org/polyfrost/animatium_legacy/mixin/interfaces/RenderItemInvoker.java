package org.polyfrost.animatium_legacy.mixin.interfaces;

import net.minecraft.client.renderer.entity.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderItem.class)
public interface RenderItemInvoker {
    @Invoker("setupGuiTransform")
    void animatium$setupGuiTransform(final int xPosition, final int yPosition, final boolean isGui3d);
}