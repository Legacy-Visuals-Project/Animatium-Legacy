package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.Animatium;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.DroppedItemHook;
import org.polyfrost.animatium_legacy.init.CustomModelBakery;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin extends Render<EntityItem> {
    @Shadow
    @Final
    private RenderItem itemRenderer;

    @Unique
    private boolean animatium$isGui3d;

    @Unique
    private ItemStack animatium$stack = null;

    protected RenderEntityItemMixin(final RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "HEAD"))
    private void animatium$setHook(final EntityItem entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        DroppedItemHook.isItemDropped = true;
        this.animatium$stack = entity.getEntityItem();
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "TAIL"))
    private void animatium$setHook2(final EntityItem entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        DroppedItemHook.isItemDropped = false;
    }

    @ModifyVariable(method = "func_177077_a", at = @At("STORE"), name = "flag")
    private boolean animatium$hookGui3d(final boolean original) {
        this.animatium$isGui3d = original;
        return original;
    }

    @ModifyArg(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"), index = 0)
    private float animatium$apply2dItem(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.itemSprites && !Animatium.isItemPhysics && !this.animatium$isGui3d) {
            return 180.0F - renderManager.playerViewY;
        } else {
            return original;
        }
    }

    @Inject(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", shift = At.Shift.AFTER))
    private void animatium$fix2dRotation(final EntityItem itemIn, final double p_177077_2_, final double p_177077_4_, final double p_177077_6_, final float p_177077_8_, final IBakedModel p_177077_9_, final CallbackInfoReturnable<Integer> cir) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.itemSprites && AnimatiumSettings.rotationFix && !Animatium.isItemPhysics && !this.animatium$isGui3d) {
            GlStateManager.rotate(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        }
    }

    @ModifyArg(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"), index = 1)
    private IBakedModel animatium$swapToCustomModel(final IBakedModel model) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldPotionsDropped && animatium$stack.getItem() instanceof ItemPotion) {
            return CustomModelBakery.BOTTLE_OVERLAY.getBakedModel();
        } else {
            return model;
        }
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", shift = At.Shift.AFTER))
    private void animatium$renderCustomBottle(final EntityItem entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        final ItemStack stack = entity.getEntityItem();
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldPotionsDropped && stack.getItem() instanceof ItemPotion) {
            IBakedModel potionModel = CustomModelBakery.BOTTLE_DRINKABLE_EMPTY.getBakedModel();
            if (ItemPotion.isSplash(stack.getMetadata())) {
                potionModel = CustomModelBakery.BOTTLE_SPLASH_EMPTY.getBakedModel();
            }

            this.itemRenderer.renderItem(new ItemStack(Items.glass_bottle), potionModel);
        }
    }
}
