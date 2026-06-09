package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private int equippedItemSlot;
    @Shadow
    private ItemStack itemToRender;
    @Shadow
    @Final
    private RenderItem itemRenderer;

    @Shadow
    protected abstract void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float tickDelta);

    @Unique
    private static float animatium$f1 = 0.0F;

    @ModifyVariable(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "STORE"
            ),
            index = 4
    )
    private float animatium$captureF1(float f1) {
        animatium$f1 = f1;
        return f1;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;performDrinking(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;doBowTransformations(FLnet/minecraft/client/entity/AbstractClientPlayer;)V")
            ), index = 1
    )
    private float animatium$useF1(float swingProgress) {
        if (AnimatiumSettings.oldBlockhitting && AnimatiumSettings.INSTANCE.enabled) {
            return animatium$f1;
        }
        return swingProgress;
    }

    @Inject(method = "doBowTransformations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void animatium$preBowTransform(float tickDelta, AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (AnimatiumSettings.firstTransformations && !AnimatiumSettings.lunarPositions && AnimatiumSettings.INSTANCE.enabled) {
            GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.5F, 0.0F);
        }
    }

    @Inject(method = "doBowTransformations", at = @At(value = "TAIL"))
    private void animatium$postBowTransform(float tickDelta, AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (AnimatiumSettings.firstTransformations && !AnimatiumSettings.lunarPositions && AnimatiumSettings.INSTANCE.enabled) {
            GlStateManager.translate(0.0F, -0.5F, 0.0F);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void animatium$firstPersonItemPositions(float tickDelta, CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && !AnimatiumSettings.lunarPositions && !itemRenderer.shouldRenderItemIn3D(itemToRender)) {
            if ((AnimatiumSettings.fishingRodPosition && itemToRender.getItem().shouldRotateAroundWhenRendering())) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                animatium$itemTransforms();
            } else if (AnimatiumSettings.firstTransformations && !(itemToRender.getItem() instanceof ItemSword && AnimatiumSettings.lunarBlockhit)) {
                animatium$itemTransforms();
            }
        }
    }

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;rotateWithPlayerRotations(Lnet/minecraft/client/entity/EntityPlayerSP;F)V"))
    private void animatium$removeRotations(ItemRenderer instance, EntityPlayerSP entityPlayerSP, float v) {
        if (!AnimatiumSettings.oldItemRotations || !AnimatiumSettings.INSTANCE.enabled) {
            rotateWithPlayerRotations(entityPlayerSP, v);
        }
    }

    @Unique
    private static void animatium$itemTransforms() {
        float scale = 1.5F / 1.7F;
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(5.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.29F, 0.149F, -0.0328F);
    }

    @ModifyConstant(method = "updateEquippedItem", constant = @Constant(floatValue = 0.4F))
    private float animatium$changeEquipSpeed(float original) {
        return AnimatiumSettings.INSTANCE.enabled ? AnimatiumSettings.INSTANCE.reequipSpeed : original;
    }

    @Inject(method = "resetEquippedProgress", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$disableReEquip1(CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.itemSwitchMode == 0 && AnimatiumSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

    @Inject(method = "resetEquippedProgress2", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$disableReEquip2(CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.itemSwitchMode == 0 && AnimatiumSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

    @ModifyVariable(
            method = "updateEquippedItem",
            at = @At(
                    value = "STORE",
                    ordinal = 3
            ),
            index = 3
    )
    private boolean animatium$disableReEquip(boolean flag) {
        if (AnimatiumSettings.INSTANCE.itemSwitchMode == 0 && AnimatiumSettings.INSTANCE.enabled) {
            EntityPlayer entityplayer = this.mc.thePlayer;
            this.itemToRender = entityplayer.inventory.getCurrentItem();
            this.equippedItemSlot = entityplayer.inventory.currentItem;
            return false;
        }
        return flag;
    }

}
