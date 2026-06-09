package org.polyfrost.animatium_legacy.mixin.interfaces;

import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface ItemRendererInvoker {
    @Accessor("equippedItemSlot")
    int animatium$getEquippedItemSlot();
}
