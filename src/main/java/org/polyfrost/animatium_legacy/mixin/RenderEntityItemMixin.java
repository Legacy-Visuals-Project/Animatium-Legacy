package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import org.polyfrost.animatium_legacy.Animatium;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.DroppedItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin extends Render<EntityItem> {

    //    @Shadow @Final private RenderItem itemRenderer;
    @Unique
    private boolean animatium$isGui3d;

//    @Unique
//    private ItemStack animatium$stack = null;

    protected RenderEntityItemMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(
            method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
            at = @At(
                    value = "HEAD"
            )
    )
    private void animatium$setHook(EntityItem entity, double x, double y, double z, float entityYaw, float tickDelta, CallbackInfo ci) {
        DroppedItemHook.isItemDropped = true;
    }

    @Inject(
            method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
            at = @At(
                    value = "TAIL"
            )
    )
    private void animatium$setHook2(EntityItem entity, double x, double y, double z, float entityYaw, float tickDelta, CallbackInfo ci) {
        DroppedItemHook.isItemDropped = false;
    }

    @ModifyVariable(method = "func_177077_a", at = @At("STORE"), ordinal = 0)
    private boolean animatium$hookGui3d(boolean isGui3d) {
        animatium$isGui3d = isGui3d;
        return isGui3d;
    }

    @ModifyArg(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"), index = 0)
    private float animatium$apply2dItem(float angle) {
        if (!animatium$isGui3d && AnimatiumSettings.itemSprites && AnimatiumSettings.INSTANCE.enabled && !Animatium.isItemPhysics) {
            return 180.0F - renderManager.playerViewY;

        }
        return angle;
    }

    @Inject(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift = At.Shift.AFTER))
    private void animatium$fix2dRotation(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, CallbackInfoReturnable<Integer> cir) {
        if (!animatium$isGui3d && AnimatiumSettings.itemSprites && AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.rotationFix && !Animatium.isItemPhysics) {
            GlStateManager.rotate(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        }
    }

//    @ModifyVariable(
//            method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
//            at = @At(
//                    value = "STORE"
//            ),
//            index = 10
//    )
//    private ItemStack animatium$captureStack(ItemStack stack) {
//        animatium$stack = stack;
//        return stack;
//    }
//
//    @ModifyArg(
//            method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"
//            ),
//            index = 1
//    )
//    private IBakedModel animatium$swapToCustomModel(IBakedModel model) {
//        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotionsDropped) return model;
//        if (animatium$stack.getItem() instanceof ItemPotion) {
//            return CustomModelBakery.BOTTLE_OVERLAY.getBakedModel();
//        }
//        return model;
//    }
//
//    @Inject(
//            method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
//                    shift = At.Shift.AFTER
//            )
//    )
//    private void animatium$renderCustomBottle(EntityItem entity, double x, double y, double z, float entityYaw, float tickDelta, CallbackInfo ci) {
//        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotionsDropped) return;
//        if (entity.getEntityItem().getItem() instanceof ItemPotion) {
//            itemRenderer.renderItem(new ItemStack(Items.glass_bottle), animatium$getBottleModel(entity.getEntityItem()));
//        }
//    }
//
//    @Unique
//    private IBakedModel animatium$getBottleModel(ItemStack stack) {
//        return ItemPotion.isSplash(stack.getMetadata()) ? CustomModelBakery.BOTTLE_SPLASH_EMPTY.getBakedModel() : CustomModelBakery.BOTTLE_DRINKABLE_EMPTY.getBakedModel();
//    }

}
