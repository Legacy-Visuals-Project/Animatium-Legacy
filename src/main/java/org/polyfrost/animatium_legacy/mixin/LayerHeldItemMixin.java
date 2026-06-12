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
    private ItemStack animatium$stack;

    @Inject(method = "doRenderLayer", at = @At(value = "HEAD"))
    private void animatium$hookHeldItem(final EntityLivingBase entitylivingbaseIn, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        this.animatium$stack = entitylivingbaseIn.getHeldItem();
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private void animatium$changeItemToStick(final EntityLivingBase entitylivingbaseIn, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer) entitylivingbaseIn).fishEntity != null) {
            this.animatium$stack = new ItemStack(Items.stick, 0);
        }
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBiped;postRenderArm(F)V"))
    private void animatium$applyOldSneaking(final EntityLivingBase entitylivingbaseIn, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isSneaking()Z"))
    private boolean animatium$cancelNewSneaking(final EntityLivingBase instance) {
        return !AnimatiumSettings.INSTANCE.enabled && instance.isSneaking();
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private Item animatium$replaceStack(final ItemStack instance) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.fishingRodStickCastTexture) {
            return this.animatium$stack.getItem();
        } else {
            return instance.getItem();
        }
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void animatium$thirdPersonItemPositions(final EntityLivingBase entitylivingbaseIn, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float s, final CallbackInfo ci, final ItemStack stack, final Item item) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.thirdPersonBlock && entitylivingbaseIn instanceof AbstractClientPlayer && ((AbstractClientPlayer) entitylivingbaseIn).isBlocking()) {
                GlStateManager.translate(0.05F, 0.0F, -0.1F);
                GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-10.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
            }

            if (AnimatiumSettings.thirdPersonItemPositions && (entitylivingbaseIn instanceof EntityPlayer || !AnimatiumSettings.disableEntityItemTransforms) && !Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(stack) && !(stack.getItem() instanceof ItemSkull || stack.getItem() instanceof ItemBanner)) {
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
                    if (AnimatiumSettings.thirdPersonFishingRodPosition && item.shouldRotateAroundWhenRendering()) {
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
    private ItemStack animatium$replaceStack2(final ItemStack heldStack) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.fishingRodStickCastTexture) {
            return this.animatium$stack;
        } else {
            return heldStack;
        }
    }

    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$allowCombine(final CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.damageTintHeldItems);
    }

}
