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
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.SwingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

    @Shadow
    public PlayerControllerMP playerController;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    private void animatium$blockHitAnimation(final boolean leftClick, final CallbackInfo ci) {
        final boolean isAdventure = this.playerController.getCurrentGameType().isAdventure();
        boolean allowedUsage = AnimatiumSettings.usagePunching &&
                this.thePlayer != null &&
                !(this.thePlayer.getHeldItem() == null || !this.thePlayer.isUsingItem() || !this.gameSettings.keyBindAttack.isKeyDown()) &&
                !(isAdventure && AnimatiumSettings.disableAdventurePunching);
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldBlockhitting && allowedUsage) {
            final MovingObjectPosition object = this.objectMouseOver;
            if (object != null && object.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                if (AnimatiumSettings.usagePunchingParticles &&
                        (this.thePlayer.isAllowEdit() || !AnimatiumSettings.disableAdventureUsageParticles) &&
                        this.theWorld != null &&
                        !this.theWorld.isAirBlock(blockPos)) {
                    this.effectRenderer.addBlockHitEffects(blockPos, this.objectMouseOver.sideHit);
                }

                if ((!AnimatiumSettings.disableAdventureBlockHit || this.thePlayer.isAllowEdit())) {
                    SwingHook.swingItem();
                }
            }
        }
    }

    @Inject(method = "clickMouse", at = @At(value = "TAIL"))
    private void animatium$onHitParticles(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.fakeMissPenaltySwing && this.leftClickCounter > 0) {
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

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z", ordinal = 7))
    private void animatium$fakeBlockHit(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.fakeBlockHit) {
            while (this.gameSettings.keyBindAttack.isPressed()) {
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
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.funnyFidget && this.thePlayer != null && this.thePlayer.getHeldItem() != null && this.thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE) {
            this.entityRenderer.itemRenderer.resetEquippedProgress();
        }
    }
}
