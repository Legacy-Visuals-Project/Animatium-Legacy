package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityLivingBase.class, priority = 980)
public abstract class EntityLivingBaseMixin extends Entity {
    public EntityLivingBaseMixin(final World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract boolean isPotionActive(final Potion potion);

    @Shadow
    public abstract PotionEffect getActivePotionEffect(final Potion potion);

    @Shadow
    protected abstract float updateDistance(final float yaw, final float pitch);

    @Shadow
    public float swingProgress;

    @Shadow
    public float rotationYawHead;

    @Unique
    protected float animatium$newHeadYaw;

    @Unique
    protected int animatium$headYawLerpWeight;

    @Inject(method = "setRotationYawHead", at = @At("HEAD"), cancellable = true)
    private void animatium$setAsNewHeadYaw(final float rotation, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.headYawFix) {
            ci.cancel();
            this.animatium$newHeadYaw = MathHelper.wrapAngleTo180_float(rotation);
            this.animatium$headYawLerpWeight = 3;
        }
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void animatium$updateHeadYaw(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.headYawFix && this.animatium$headYawLerpWeight > 0) {
            this.rotationYawHead += MathHelper.wrapAngleTo180_float(this.animatium$newHeadYaw - this.rotationYawHead) / this.animatium$headYawLerpWeight;
            this.rotationYawHead = MathHelper.wrapAngleTo180_float(this.rotationYawHead);
            this.animatium$headYawLerpWeight--;
        }
    }

    // TODO: hook swing speed
    @Inject(method = "getArmSwingAnimationEnd()I", at = @At("HEAD"), cancellable = true)
    private void animatium$modifySwingSpeed(final CallbackInfoReturnable<Integer> cir) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.globalPositions) {
            int length = 6;
            if (!AnimatiumSettings.ignoreHaste && isPotionActive(Potion.digSpeed)) {
                length -= (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier());
                cir.setReturnValue(Math.max((int) (length * Math.exp(-settings.itemSwingSpeedHaste)), 1));
            } else if (!AnimatiumSettings.ignoreFatigue && isPotionActive(Potion.digSlowdown)) {
                length += (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2;
                cir.setReturnValue(Math.max((int) (length * Math.exp(-settings.itemSwingSpeedFatigue)), 1));
            } else {
                cir.setReturnValue(Math.max((int) (length * Math.exp(-settings.itemSwingSpeed)), 1));
            }
        }
    }

    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;updateDistance(FF)F"))
    private float animatium$dontRotateBackwardsWalking(final EntityLivingBase instance, final float yaw, final float pitch) {
        float newYaw = yaw;
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.dontRotateBackwardsWalking && !(this.swingProgress > 0.0F) && pitch != 0.0F) {
            final float offsetAngle = MathHelper.abs(MathHelper.wrapAngleTo180_float(this.rotationYaw) - yaw);
            newYaw = yaw - (95.0F < offsetAngle && offsetAngle < 265.0F ? 180.0F : 0.0F);
        }

        return this.updateDistance(newYaw, pitch);
    }
}
