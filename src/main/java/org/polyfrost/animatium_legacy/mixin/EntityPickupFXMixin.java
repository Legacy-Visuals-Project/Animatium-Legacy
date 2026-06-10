package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPickupFX.class)
public abstract class EntityPickupFXMixin {
    @Shadow
    private float field_174841_aA;

    @Shadow
    private Entity field_174843_ax;

    @Inject(method = "renderParticle", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/particle/EntityPickupFX;field_174841_aA:F"))
    private void animatium$factorInEyeHeight(final WorldRenderer worldRendererIn, final Entity entityIn, final float tickDelta, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.oldPickup) {
                field_174841_aA = (field_174843_ax.getEyeHeight() / 2);
            }

            field_174841_aA += AnimatiumSettings.INSTANCE.pickupPosition;
        }
    }

    @Inject(method = "renderParticle", at = @At("HEAD"), cancellable = true)
    private void animatium$disableCollectParticle(final WorldRenderer worldRendererIn, final Entity entityIn, final float tickDelta, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.disablePickup) {
            ci.cancel();
        }
    }
}
