package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.SkullModelHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelMesher.class)
public abstract class ItemModelMesherMixin {
    @Inject(method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$useCustomBlockModel(final ItemStack stack, final CallbackInfoReturnable<IBakedModel> cir) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldSkullModels && stack != null && stack.getItem() instanceof ItemSkull) {
            cir.setReturnValue(SkullModelHook.INSTANCE.getSkullModel(stack));
        }
    }
}