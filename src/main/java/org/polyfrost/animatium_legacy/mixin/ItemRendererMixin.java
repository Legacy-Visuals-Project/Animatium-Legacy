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
import org.polyfrost.animatium_legacy.config.ItemSwitchMode;
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
    protected abstract void rotateWithPlayerRotations(final EntityPlayerSP player, final float tickDelta);

    @Unique
    private static float animatium$swingProgress = 0.0F;

    @ModifyVariable(method = "renderItemInFirstPerson", at = @At(value = "STORE"), name = "f1")
    private float animatium$captureSwingProgress(final float original) {
        animatium$swingProgress = original;
        return original;
    }

    @ModifyArg(
            method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;performDrinking(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;doBowTransformations(FLnet/minecraft/client/entity/AbstractClientPlayer;)V")
            ),
            index = 1
    )
    private float animatium$useSwingProgress(final float swingProgress) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldBlockhitting) {
            return animatium$swingProgress;
        } else {
            return swingProgress;
        }
    }

    @Inject(method = "doBowTransformations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void animatium$preBowTransform(final float tickDelta, final AbstractClientPlayer clientPlayer, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.firstPersonItemPositions && !AnimatiumSettings.lunarPositions) {
            GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.5F, 0.0F);
        }
    }

    @Inject(method = "doBowTransformations", at = @At(value = "TAIL"))
    private void animatium$postBowTransform(final float tickDelta, final AbstractClientPlayer clientPlayer, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.firstPersonItemPositions && !AnimatiumSettings.lunarPositions) {
            GlStateManager.translate(0.0F, -0.5F, 0.0F);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void animatium$firstPersonItemPositions(final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && !AnimatiumSettings.lunarPositions && !itemRenderer.shouldRenderItemIn3D(itemToRender)) {
            if ((AnimatiumSettings.firstPersonFishingRodPosition && itemToRender.getItem().shouldRotateAroundWhenRendering())) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                animatium$applyItemTransforms();
            } else if (AnimatiumSettings.firstPersonItemPositions && !(itemToRender.getItem() instanceof ItemSword && AnimatiumSettings.lunarBlockhit)) {
                animatium$applyItemTransforms();
            }
        }
    }

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;rotateWithPlayerRotations(Lnet/minecraft/client/entity/EntityPlayerSP;F)V"))
    private void animatium$removeRotations(final ItemRenderer instance, final EntityPlayerSP entityPlayerSP, final float tickDelta) {
        if (!AnimatiumSettings.oldItemRotations || !AnimatiumSettings.INSTANCE.enabled) {
            this.rotateWithPlayerRotations(entityPlayerSP, tickDelta);
        }
    }

    @Unique
    private static void animatium$applyItemTransforms() {
        final float scale = 1.5F / 1.7F;
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(5.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.29F, 0.149F, -0.0328F);
    }

    @ModifyConstant(method = "updateEquippedItem", constant = @Constant(floatValue = 0.4F))
    private float animatium$changeEquipSpeed(final float original) {
        return AnimatiumSettings.INSTANCE.enabled ? AnimatiumSettings.INSTANCE.reequipSpeed : original;
    }

    @Inject(method = "resetEquippedProgress", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$disableReEquip1(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.itemSwitchMode() == ItemSwitchMode.DISABLED) {
            ci.cancel();
        }
    }

    @Inject(method = "resetEquippedProgress2", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$disableReEquip2(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.itemSwitchMode() == ItemSwitchMode.DISABLED) {
            ci.cancel();
        }
    }

    @ModifyVariable(method = "updateEquippedItem", at = @At(value = "STORE", ordinal = 3), name = "flag")
    private boolean animatium$disableReEquip(final boolean original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.itemSwitchMode() == ItemSwitchMode.DISABLED) {
            final EntityPlayer player = this.mc.thePlayer;
            this.itemToRender = player.inventory.getCurrentItem();
            this.equippedItemSlot = player.inventory.currentItem;
            return false;
        } else {
            return original;
        }
    }
}
