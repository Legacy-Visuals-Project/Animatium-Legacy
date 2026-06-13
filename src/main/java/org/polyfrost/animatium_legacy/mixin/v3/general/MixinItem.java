package org.polyfrost.animatium_legacy.mixin.v3.general;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemSwitchMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "shouldCauseReequipAnimation", at = @At("HEAD"), cancellable = true, remap = false)
    private void animatium$modifyReequip(final ItemStack oldStack, final ItemStack newStack, final boolean slotChanged, final CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.INSTANCE.itemSwitchMode() == ItemSwitchMode.DISABLED) {
                cir.setReturnValue(false);
            } else if (AnimatiumSettings.fixReequip && AnimatiumSettings.INSTANCE.itemSwitchMode() != ItemSwitchMode.V1_7 && !slotChanged) {
                cir.setReturnValue(false);
            } else if (AnimatiumSettings.INSTANCE.itemSwitchMode() == ItemSwitchMode.V1_7) {
                cir.setReturnValue(!AnimatiumSettings.fixReequip || slotChanged || Minecraft.getMinecraft().currentScreen instanceof GuiContainer);
            }
        }
    }
}
