package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.renderer.ItemModelMesher;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemModelMesher.class)
public class ItemModelMesherMixin {

//    @Inject(
//            method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;",
//            at = @At(
//                    value = "HEAD"
//            ),
//            cancellable = true
//    )
//    private void animatium$useCustomBlockModel(ItemStack stack, CallbackInfoReturnable<IBakedModel> cir) {
//        if (stack == null) return;
//        if (!OldAnimationsSettings.oldSkulls || !OldAnimationsSettings.INSTANCE.enabled) return;
//        if (stack.getItem() instanceof ItemSkull) {
//            cir.setReturnValue(SkullModelHook.INSTANCE.getSkullModel(stack));
//        }
//    }

}