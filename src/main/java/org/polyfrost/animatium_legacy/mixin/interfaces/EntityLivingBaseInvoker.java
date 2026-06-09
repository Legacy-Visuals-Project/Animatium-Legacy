package org.polyfrost.animatium_legacy.mixin.interfaces;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLivingBase.class)
public interface EntityLivingBaseInvoker {
    @Invoker("getArmSwingAnimationEnd")
    int animatium$getArmSwingAnimation();
}
