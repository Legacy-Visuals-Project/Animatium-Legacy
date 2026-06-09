package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemPotion.class)
public abstract class ItemPotionMixin {

    @Shadow
    public abstract int getColorFromDamage(int meta);

    @Inject(method = "getColorFromItemStack", at = @At("HEAD"), cancellable = true)
    private void animatium$allowPotColors(ItemStack stack, int renderPass, CallbackInfoReturnable<Integer> cir) {
        if (AnimatiumSettings.coloredBottles && AnimatiumSettings.INSTANCE.enabled) {
            cir.setReturnValue(getColorFromDamage(stack.getMetadata()));
        }
    }

}
