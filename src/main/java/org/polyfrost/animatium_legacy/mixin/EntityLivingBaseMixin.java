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
import org.spongepowered.asm.mixin.injection.ModifyArg;
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
    public float swingProgress;

    @Shadow
    public float renderYawOffset;

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
            animatium$newHeadYaw = MathHelper.wrapAngleTo180_float(rotation);
            animatium$headYawLerpWeight = 3;
        }
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void animatium$updateHeadYaw(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.headYawFix && animatium$headYawLerpWeight > 0) {
            rotationYawHead += MathHelper.wrapAngleTo180_float(animatium$newHeadYaw - rotationYawHead) / animatium$headYawLerpWeight;
            rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
            animatium$headYawLerpWeight--;
        }
    }

    // TODO: hook swing speed
    @Inject(method = "getArmSwingAnimationEnd()I", at = @At("HEAD"), cancellable = true)
    private void animatium$modifySwingSpeed(final CallbackInfoReturnable<Integer> cir) {
        AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled && AnimatiumSettings.globalPositions) {
            int length = 6;
            if (isPotionActive(Potion.digSpeed) && !AnimatiumSettings.ignoreHaste) {
                length -= (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier());
                cir.setReturnValue(Math.max((int) (length * Math.exp(-settings.itemSwingSpeedHaste)), 1));
            } else if (isPotionActive(Potion.digSlowdown) && !AnimatiumSettings.ignoreFatigue) {
                length += (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2;
                cir.setReturnValue(Math.max((int) (length * Math.exp(-settings.itemSwingSpeedFatigue)), 1));
            } else {
                cir.setReturnValue(Math.max((int) (length * Math.exp(-settings.itemSwingSpeed)), 1));
            }
        }
    }

    // TODO: see if i can write this better
    @ModifyArg(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;updateDistance(FF)F"), index = 0)
    private float animatium$modifyYaw(final float yaw) {
        double d0 = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f = (float) (d0 * d0 + d1 * d1);
        float h = renderYawOffset;
        if (f > 0.0025000002F) {
            float f1 = (float) MathHelper.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
            float g = MathHelper.abs(MathHelper.wrapAngleTo180_float(rotationYaw) - f1);
            h = f1 - (95.0F < g && g < 265.0F ? 180.0F : 0.0F);
        }

        if (swingProgress > 0.0F) {
            h = rotationYaw;
        }

        return AnimatiumSettings.modernMovement ? h : yaw;
    }
}
