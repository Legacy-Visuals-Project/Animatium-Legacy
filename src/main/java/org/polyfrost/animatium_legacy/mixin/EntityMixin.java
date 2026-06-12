package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.damageTilt && (Object) this == player) {
            final float result = (float) (Math.atan2(z - this.motionZ, x - this.motionX) * (180D / Math.PI) - (double) this.rotationYaw);
            if (Float.isFinite(result)) {
                player.attackedAtYaw = result;
            }
        }
    }
}