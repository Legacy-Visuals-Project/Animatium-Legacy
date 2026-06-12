package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin_CustomPositions {
    @Shadow
    private ItemStack itemToRender;

    @Inject(method = "transformFirstPersonItem(FF)V", at = @At("HEAD"), cancellable = true)
    private void animatium$itemTransform(final float equipProgress, final float swingProgress, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            final Item item = this.itemToRender.getItem();
            if (AnimatiumSettings.lunarPositions && item != null) {
                if (item instanceof ItemSword) {
                    GlStateManager.translate(0.0F, 0.0F, -0.02F);
                    GlStateManager.rotate(1.0F, 0.0F, 0.0F, -0.1F);
                } else if (item instanceof ItemPotion) {
                    GlStateManager.translate(-0.0225F, -0.02F, 0.0F);
                    GlStateManager.rotate(1.0F, 0.0F, 0.0F, 0.1F);
                } else if (item instanceof ItemFishingRod || item instanceof ItemCarrotOnAStick) {
                    GlStateManager.translate(0.08F, -0.0275F, -0.33F);
                    GlStateManager.scale(0.949999988079071D, 1.0D, 1.0D);
                } else if (item instanceof ItemBow) {
                    GlStateManager.translate(0.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(0.9F, 0.0F, 0.001F, 0.0F);
                } else if (item instanceof ItemBlock) {
                    Block block = ((ItemBlock) item).getBlock();
                    if (block instanceof BlockCarpet || block instanceof BlockSnow) {
                        GlStateManager.translate(0.0F, -0.25F, 0.0F);
                    }
                }
            }

            if (AnimatiumSettings.globalPositions) {
                final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
                GlStateManager.translate(0.56f * (1.0F + settings.itemPositionX), -0.52f * (1.0F - settings.itemPositionY), -0.72f * (1.0F + settings.itemPositionZ));
                GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
                GlStateManager.rotate(settings.itemRotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(settings.itemRotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(settings.itemRotationRoll, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
                float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
                GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
                double scale = 0.4f * Math.exp(settings.itemScale);
                GlStateManager.scale(scale, scale, scale);
                ci.cancel();
            }
        }
    }

    @Inject(method = "doItemUsedTransformations", at = @At("HEAD"), cancellable = true)
    private void animatium$swingTransformations(final float swingProgress, final CallbackInfo ci) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.globalPositions) {
            final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
            if (settings.swingSetting == 2) {
                ci.cancel();
            } else {
                final float scale = (1.0F + (settings.swingSetting == 1 ? settings.itemScale : 0.0F));
                final float f = (-0.4f * (1.0F + advanced.itemSwingPositionX)) * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI) * scale;
                final float f1 = 0.2f * (1.0F - advanced.itemSwingPositionY) * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0f) * scale;
                final float f2 = -0.2f * (1.0F + advanced.itemSwingPositionZ) * MathHelper.sin(swingProgress * (float) Math.PI) * scale;
                GlStateManager.translate(f, f1, f2);
                ci.cancel();
            }
        }
    }

    @Inject(method = "doBlockTransformations", at = @At("HEAD"), cancellable = true)
    private void animatium$blockedItemTransform(final CallbackInfo ci) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.globalPositions) {
            final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
            GlStateManager.translate(-0.5f * (1.0F + advanced.blockedPositionX), 0.2f * (1.0F + advanced.blockedPositionY), 0.0f + advanced.blockedPositionZ);
            GlStateManager.rotate(advanced.blockedRotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(advanced.blockedRotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(advanced.blockedRotationRoll, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
            if (AnimatiumSettings.lunarBlockhit) {
                GlStateManager.translate(-0.55F, 0.2F, 0.1F);
                GlStateManager.scale(0.85F, 0.85F, 0.85F);
                GlStateManager.rotate(1.0F, 0.0F, 0.0F, -1.0F);
                GlStateManager.rotate(1.0F, 0.25F, 0.0F, 0.0F);
                GlStateManager.rotate(2.0F, 0.0F, 2.0F, 0.0F);
            }

            final double scale = 1.0f * Math.exp(advanced.blockedScale);
            GlStateManager.scale(scale, scale, scale);
            ci.cancel();
        }
    }

    @Inject(method = "doBlockTransformations", at = @At("TAIL"))
    private void animatium$lunarTransform(final CallbackInfo ci) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.lunarBlockhit && !AnimatiumSettings.globalPositions) {
            GlStateManager.translate(-0.55F, 0.2F, 0.1F);
            GlStateManager.scale(0.85F, 0.85F, 0.85F);
            GlStateManager.rotate(1.0F, 0.0F, 0.0F, -1.0F);
            GlStateManager.rotate(1.0F, 0.25F, 0.0F, 0.0F);
            GlStateManager.rotate(2.0F, 0.0F, 2.0F, 0.0F);
        }
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 0)
    private float animatium$modifyBlockEquip(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.lunarBlockhit) {
            return 0.2F;
        } else {
            return original;
        }
    }

    // TODO: add customization for equip / swing progress

    @Inject(method = "performDrinking", at = @At("HEAD"), cancellable = true)
    private void animatium$drinkingItemTransform(final AbstractClientPlayer clientPlayer, final float tickDelta, final CallbackInfo ci) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.globalPositions) {
            final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
            float f = (float) clientPlayer.getItemInUseCount() - tickDelta + 1.0f;
            float f1 = f / (float) itemToRender.getMaxItemUseDuration();
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * (float) Math.PI) * 0.1f);
            if (f1 >= 0.8f) {
                f2 = 0.0f;
            }

            float posX = 0.56f * (1.0F + settings.itemPositionX);
            float posY = -0.52f * (1.0F - settings.itemPositionY);
            float posZ = -0.72f * (1.0F + settings.itemPositionZ);
            if (ItemPositionAdvancedSettings.shouldScaleEat) {
                GlStateManager.translate(-0.56f, 0.52f, 0.72f);
                GlStateManager.translate(posX, posY, posZ);
            }

            GlStateManager.translate(advanced.consumePositionX, advanced.consumePositionY, advanced.consumePositionZ);
            GlStateManager.translate(0.0f, f2 * (1.0F + advanced.consumeIntensity), 0.0f);
            float f3 = 1.0f - (float) Math.pow(f1, 27.0f * (1.0F + advanced.consumeSpeed));
            GlStateManager.translate(f3 * 0.6f, f3 * -0.5f, f3 * 0.0f);
            GlStateManager.rotate(advanced.consumeRotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(advanced.consumeRotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(advanced.consumeRotationRoll, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(f3 * 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(f3 * 30.0f, 0.0f, 0.0f, 1.0f);
            if (ItemPositionAdvancedSettings.shouldScaleEat) {
                GlStateManager.translate(0.56f, -0.52f, -0.72f);
                GlStateManager.translate(-posX, -posY, -posZ);
            }

            ci.cancel();
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void animatium$drinkingItemScale(final float tickDelta, final CallbackInfo ci) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.globalPositions) {
            final double scale = 1.0f * Math.exp(AnimatiumSettings.advancedSettings.consumeScale);
            GlStateManager.scale(scale, scale, scale);
        }
    }

    @ModifyConstant(method = "performDrinking", constant = @Constant(floatValue = 0.6f))
    private float animatium$lunarDrinking(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.lunarPositions) {
            return 0.66F;
        } else {
            return original;
        }
    }

    @ModifyConstant(method = "performDrinking", constant = @Constant(floatValue = 10.0f))
    private float animatium$lunarDrinking2(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.lunarPositions) {
            return 5.0F;
        } else {
            return original;
        }
    }

    @ModifyConstant(method = "performDrinking", constant = @Constant(floatValue = 30.0f))
    private float animatium$lunarDrinking3(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.lunarPositions) {
            return 28.0F;
        } else {
            return original;
        }
    }

    @Inject(method = "doBowTransformations", at = @At("HEAD"))
    private void animatium$lunarBowPosition(final float tickDelta, final AbstractClientPlayer clientPlayer, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.lunarPositions) {
            GlStateManager.translate(-0.2D, 0.0D, -0.175D);
            GlStateManager.rotate(1.0F, 0.0F, 0.0F, -1.25F);
        }
    }
}
