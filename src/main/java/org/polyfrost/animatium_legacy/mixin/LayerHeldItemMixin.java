package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LayerHeldItem.class)
public abstract class LayerHeldItemMixin {

    @Unique
    public ItemStack simpliefied$itemStack;

    @Inject(method = "doRenderLayer", at = @At(value = "HEAD"))
    private void animatium$hookHeldItem(EntityLivingBase entitylivingbaseIn, float f, float g, float tickDelta, float h, float i, float j, float scale, CallbackInfo ci) {
        simpliefied$itemStack = entitylivingbaseIn.getHeldItem();
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBiped;postRenderArm(F)V"))
    private void animatium$applyOldSneaking(EntityLivingBase entitylivingbaseIn, float f, float g, float tickDelta, float h, float i, float j, float scale, CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isSneaking()Z"))
    private boolean animatium$cancelNewSneaking(EntityLivingBase instance) {
        return instance.isSneaking() && !AnimatiumSettings.INSTANCE.enabled;
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void animatium$changeItemToStick(EntityLivingBase entitylivingbaseIn, float f, float g, float tickDelta, float h, float i, float j, float scale, CallbackInfo ci, ItemStack itemStack) {
        if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer) entitylivingbaseIn).fishEntity != null) {
            simpliefied$itemStack = new ItemStack(Items.stick, 0);
        }
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private Item animatium$replaceStack(ItemStack instance) {
        return AnimatiumSettings.fishingStick && AnimatiumSettings.INSTANCE.enabled ? simpliefied$itemStack.getItem() : instance.getItem();
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void animatium$thirdPersonItemPositions(EntityLivingBase entitylivingbaseIn, float f, float g, float tickDelta, float h, float i, float j, float s, CallbackInfo ci, ItemStack stack, Item item) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.thirdPersonBlock && entitylivingbaseIn instanceof AbstractClientPlayer && ((AbstractClientPlayer) entitylivingbaseIn).isBlocking()) {
                GlStateManager.translate(0.05F, 0.0F, -0.1F);
                GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-10.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
            }
            if (AnimatiumSettings.thirdTransformations && (entitylivingbaseIn instanceof EntityPlayer || !AnimatiumSettings.entityTransforms)
                    && !Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(stack) && !(stack.getItem() instanceof ItemSkull || stack.getItem() instanceof ItemBanner)) {
                float scale = 1.5F * 0.625F;
                if (item instanceof ItemBow) {
                    GlStateManager.rotate(-12.0F, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-7.0F, 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(10.0F, 0.0f, 0.0f, 1.0f);
                    GlStateManager.rotate(1.0F, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-4.5F, 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(-1.5F, 0.0f, 0.0f, 1.0f);
                    GlStateManager.translate(0.022F, -0.01F, -0.108F);
                    GlStateManager.scale(scale, scale, scale);
                } else if (item.isFull3D()) {
                    if (item.shouldRotateAroundWhenRendering()) {
                        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                    }
                    GlStateManager.scale(scale / 0.85F, scale / 0.85F, scale / 0.85F);
                    GlStateManager.rotate(-2.4F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(4.5F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translate(-0.013F, 0.01F, 0.125F);
                } else {
                    scale = 1.5F * 0.375F;
                    GlStateManager.scale(scale / 0.55, scale / 0.55, scale / 0.55);
                    GlStateManager.rotate(-195.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-168.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(15.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translate(-0.047F, -0.28F, 0.038F);
                }
            }
        }
    }

    @ModifyArg(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private ItemStack animatium$replaceStack2(ItemStack heldStack) {
        return AnimatiumSettings.fishingStick && AnimatiumSettings.INSTANCE.enabled ? simpliefied$itemStack : heldStack;
    }

    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$allowCombine(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(AnimatiumSettings.damageHeldItems && AnimatiumSettings.INSTANCE.enabled);
    }

}
