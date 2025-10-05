package org.polyfrost.overflowanimations.mixin;

import dev.deftu.omnicore.api.client.OmniClient;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow
    public double motionZ;

    @Shadow
    public double motionX;

    @Shadow
    public float rotationYaw;

    @Inject(method = "setVelocity", at = @At("HEAD"))
    private void directionalHurtCam(double x, double y, double z, CallbackInfo ci) {
        if (!OldAnimationsSettings.damageTilt) return;
        if ((Entity) (Object) this == OmniClient.getPlayer()) {
            float result = (float) (Math.atan2(z - motionZ, x - motionX) * (180D / Math.PI) - (double) rotationYaw);
            if (Float.isFinite(result)) {
                OmniClient.getPlayer().attackedAtYaw = result;
            }
        }
    }
}