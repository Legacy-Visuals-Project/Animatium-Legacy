package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemSwitchMode;
import org.polyfrost.animatium_legacy.mixin.interfaces.ItemRendererInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getIsItemStackEqual", at = @At("RETURN"), cancellable = true)
    private void animatium$modifyReequip(final ItemStack stack, final CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.itemSwitchMode == ItemSwitchMode.V1_7) {
            final Minecraft mc = Minecraft.getMinecraft();
            final int currentItem = mc.thePlayer.inventory.currentItem;
            final int equippedProgress = ((ItemRendererInvoker) mc.getItemRenderer()).animatium$getEquippedItemSlot();
            cir.setReturnValue(cir.getReturnValue() && equippedProgress == currentItem);
        }
    }
}
