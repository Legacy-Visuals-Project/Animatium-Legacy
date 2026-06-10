package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.SwingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public MovingObjectPosition objectMouseOver;

    @Shadow
    public EffectRenderer effectRenderer;

    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    private int leftClickCounter;

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    public EntityRenderer entityRenderer;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    private void animatium$blockHitAnimation(final boolean leftClick, final CallbackInfo ci) {
        if (AnimatiumSettings.oldBlockhitting && AnimatiumSettings.punching && AnimatiumSettings.INSTANCE.enabled && gameSettings.keyBindUseItem.isKeyDown()) {
            if (this.leftClickCounter <= 0 && leftClick && this.objectMouseOver != null
                    && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                    //todo: fix the logic
                    && ((thePlayer.isUsingItem()) || !AnimatiumSettings.adventurePunching)) {
                final BlockPos posBlock = this.objectMouseOver.getBlockPos();
                if (!theWorld.isAirBlock(posBlock)) {
                    if ((this.thePlayer.isAllowEdit() || !AnimatiumSettings.adventureParticles) && AnimatiumSettings.punchingParticles) {
                        effectRenderer.addBlockHitEffects(posBlock, this.objectMouseOver.sideHit);
                    }

                    if ((this.thePlayer.isAllowEdit() || !AnimatiumSettings.adventureBlockHit)) {
                        SwingHook.swingItem();
                    }
                }
            }
        }
    }

    @Inject(method = "clickMouse", at = @At(value = "TAIL"))
    private void animatium$onHitParticles(final CallbackInfo ci) {
        if (AnimatiumSettings.visualSwing && AnimatiumSettings.INSTANCE.enabled && this.leftClickCounter > 0) {
            if (this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && !this.objectMouseOver.entityHit.hitByEntity(thePlayer) && this.objectMouseOver.entityHit instanceof EntityLivingBase) {
                if (this.thePlayer.fallDistance > 0.0F && !this.thePlayer.onGround && !this.thePlayer.isOnLadder() && !this.thePlayer.isInWater() && !this.thePlayer.isPotionActive(Potion.blindness) && this.thePlayer.ridingEntity == null) {
                    this.thePlayer.onCriticalHit(this.objectMouseOver.entityHit);
                }

                if (EnchantmentHelper.getModifierForCreature(this.thePlayer.getHeldItem(), ((EntityLivingBase) this.objectMouseOver.entityHit).getCreatureAttribute()) > 0.0F) {
                    this.thePlayer.onEnchantmentCritical(this.objectMouseOver.entityHit);
                }
            }

            SwingHook.swingItem();
        }
    }

    @Redirect(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;clickBlock(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"))
    private boolean animatium$preventMiningWhenUsing(final PlayerControllerMP instance, final BlockPos blockPos, final EnumFacing facing) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldBlockhitting && animatium$hasUseAction()) {
            return false;
        } else {
            return instance.clickBlock(blockPos, facing);
        }
    }

    @Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean animatium$enabledRightClick(final PlayerControllerMP instance) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldBlockhitting && animatium$hasUseAction()) {
            return false;
        } else {
            return instance.getIsHittingBlock();
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z", ordinal = 7))
    private void animatium$fakeBlockHit(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.fakeBlockHit) {
            while (gameSettings.keyBindAttack.isPressed()) {
                SwingHook.swingItem();
            }
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;dropOneItem(Z)Lnet/minecraft/entity/item/EntityItem;", shift = At.Shift.AFTER))
    private void animatium$dropItemSwing(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.modernDropSwing && this.thePlayer.getHeldItem() != null) {
            SwingHook.swingItem();
        }
    }

    @Inject(method = "rightClickMouse", at = @At(value = "HEAD"))
    private void animatium$funnyFidgetyThing(final CallbackInfo ci) {
        if (AnimatiumSettings.funnyFidget && AnimatiumSettings.INSTANCE.enabled && this.thePlayer != null && this.thePlayer.getHeldItem() != null && this.thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE) {
            entityRenderer.itemRenderer.resetEquippedProgress();
        }
    }

    @Unique
    private boolean animatium$hasUseAction() {
        /* unironically, sk1er's old animations mod was on to something wtf */
        return this.thePlayer.getHeldItem() != null && (this.thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE || this.thePlayer.getHeldItem().getItem() instanceof ItemBlock);
    }
}
