package org.polyfrost.animatium_legacy.mixin;

import cc.polyfrost.oneconfig.libs.universal.UMinecraft;
import net.minecraft.entity.Entity;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public double motionZ;

    @Shadow
    public double motionX;

    @Shadow
    public float rotationYaw;

    @Inject(method = "setVelocity", at = @At("HEAD"))
    private void directionalHurtCam(final double x, final double y, final double z, final CallbackInfo ci) {
        if (AnimatiumSettings.damageTilt && (Object) this == UMinecraft.getPlayer()) {
            final float result = (float) (Math.atan2(z - motionZ, x - motionX) * (180D / Math.PI) - (double) rotationYaw);
            if (Float.isFinite(result)) {
                UMinecraft.getPlayer().attackedAtYaw = result;
            }
        }
    }
}