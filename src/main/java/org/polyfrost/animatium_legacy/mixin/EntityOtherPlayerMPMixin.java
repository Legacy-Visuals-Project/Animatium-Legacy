package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityOtherPlayerMP.class, priority = 980)
public abstract class EntityOtherPlayerMPMixin extends EntityLivingBaseMixin {
    public EntityOtherPlayerMPMixin(final World worldIn) {
        super(worldIn);
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void animatium$updateHeadYaw(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.headYawFix && animatium$headYawLerpWeight > 0) {
            rotationYawHead += MathHelper.wrapAngleTo180_float(animatium$newHeadYaw - rotationYawHead) / animatium$headYawLerpWeight;
            rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
            animatium$headYawLerpWeight--;
        }
    }
}