package org.polyfrost.animatium_legacy.mixin.v3.entity;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityOtherPlayerMP.class, priority = 980)
public abstract class MixinEntityOtherPlayerMP extends MixinEntityLivingBase {
    public MixinEntityOtherPlayerMP(final World world) {
        super(world);
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void animatium$updateHeadYaw(final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.headYawFix && this.animatium$headYawLerpWeight > 0) {
            this.rotationYawHead += MathHelper.wrapAngleTo180_float(this.animatium$newHeadYaw - this.rotationYawHead) / this.animatium$headYawLerpWeight;
            this.rotationYawHead = MathHelper.wrapAngleTo180_float(this.rotationYawHead);
            this.animatium$headYawLerpWeight--;
        }
    }
}