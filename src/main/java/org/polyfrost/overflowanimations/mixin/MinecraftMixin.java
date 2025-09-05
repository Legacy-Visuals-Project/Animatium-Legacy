package org.polyfrost.overflowanimations.mixin;

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
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.SwingHook;
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

    @Shadow public GameSettings gameSettings;

    @Shadow public EntityRenderer entityRenderer;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    public void overflowAnimations$blockHitAnimation(boolean leftClick, CallbackInfo ci) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.punching && OldAnimationsSettings.INSTANCE.enabled && gameSettings.keyBindUseItem.isKeyDown()) {
            if (leftClickCounter <= 0 && leftClick && objectMouseOver != null
                    && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                    //todo: fix the logic
                    && ((thePlayer.isUsingItem()) || !OldAnimationsSettings.adventurePunching)) {
                BlockPos posBlock = objectMouseOver.getBlockPos();
                if (!theWorld.isAirBlock(posBlock)) {
                    if ((thePlayer.isAllowEdit() || !OldAnimationsSettings.adventureParticles) && OldAnimationsSettings.punchingParticles) {
                        effectRenderer.addBlockHitEffects(posBlock, objectMouseOver.sideHit);
                    }
                    if ((thePlayer.isAllowEdit() || !OldAnimationsSettings.adventureBlockHit)) {
                        SwingHook.swingItem();
                    }
                }
            }
        }
    }

    @Inject(method = "clickMouse", at = @At(value = "TAIL"))
    public void overflowAnimations$onHitParticles(CallbackInfo ci) {
        if (OldAnimationsSettings.visualSwing && OldAnimationsSettings.INSTANCE.enabled && leftClickCounter > 0) {
            if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY &&
                    !objectMouseOver.entityHit.hitByEntity(thePlayer) && objectMouseOver.entityHit instanceof EntityLivingBase) {
                if (thePlayer.fallDistance > 0.0F && !thePlayer.onGround && !thePlayer.isOnLadder() &&
                        !thePlayer.isInWater() && !thePlayer.isPotionActive(Potion.blindness) && thePlayer.ridingEntity == null) {
                    thePlayer.onCriticalHit(objectMouseOver.entityHit);
                }
                if (EnchantmentHelper.getModifierForCreature(thePlayer.getHeldItem(),
                        ((EntityLivingBase)objectMouseOver.entityHit).getCreatureAttribute()) > 0.0F) {
                    thePlayer.onEnchantmentCritical(objectMouseOver.entityHit);
                }
            }
            SwingHook.swingItem();
        }
    }

    @Redirect(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;clickBlock(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"))
    private boolean overflowAnimations$preventMiningWhenUsing(PlayerControllerMP instance, BlockPos itemstack, EnumFacing block1) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.INSTANCE.enabled && overflowAnimations$hasUseAction()) {
            /* interestingly enough, badlion also does something like this */
            return false;
        }
        return instance.clickBlock(itemstack, block1);
    }

    @Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    public boolean overflowAnimations$enabledRightClick(PlayerControllerMP instance) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.INSTANCE.enabled && overflowAnimations$hasUseAction()) {
            return false;
        }
        return instance.getIsHittingBlock();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z", ordinal = 7))
    public void overflowAnimations$fakeBlockHit(CallbackInfo ci) {
        if (OldAnimationsSettings.fakeBlockHit && OldAnimationsSettings.INSTANCE.enabled) {
            while (gameSettings.keyBindAttack.isPressed()) {
                SwingHook.swingItem();
            }
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;dropOneItem(Z)Lnet/minecraft/entity/item/EntityItem;", shift = At.Shift.AFTER))
    public void overflowAnimations$dropItemSwing(CallbackInfo ci) {
        if (OldAnimationsSettings.modernDropSwing && OldAnimationsSettings.INSTANCE.enabled && thePlayer.getHeldItem() != null) {
            SwingHook.swingItem();
        }
    }

    @Inject(method = "rightClickMouse", at = @At(value = "HEAD"))
    public void overflowAnimations$funnyFidgetyThing(CallbackInfo ci) {
        if (OldAnimationsSettings.funnyFidget && OldAnimationsSettings.INSTANCE.enabled && thePlayer != null && thePlayer.getHeldItem() != null && thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE) {
            entityRenderer.itemRenderer.resetEquippedProgress();
        }
    }

    @Unique
    private boolean overflowAnimations$hasUseAction() {
        /* unironically, sk1er's old animations mod was on to something wtf */
        return thePlayer.getHeldItem() != null &&
                (thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE ||
                        thePlayer.getHeldItem().getItem() instanceof ItemBlock);
    }

}
