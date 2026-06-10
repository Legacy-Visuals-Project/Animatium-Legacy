package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "shouldCauseReequipAnimation", at = @At("HEAD"), cancellable = true, remap = false)
    private void animatium$modifyReequip(final ItemStack oldStack, final ItemStack newStack, final boolean slotChanged, final CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.INSTANCE.itemSwitchMode == 0) {
                cir.setReturnValue(false);
            } else if (AnimatiumSettings.fixReequip && AnimatiumSettings.INSTANCE.itemSwitchMode != 1 && !slotChanged) {
                cir.setReturnValue(false);
            } else if (AnimatiumSettings.INSTANCE.itemSwitchMode == 1) {
                cir.setReturnValue(!AnimatiumSettings.fixReequip || slotChanged || Minecraft.getMinecraft().currentScreen instanceof GuiContainer);
            }
        }
    }
}
