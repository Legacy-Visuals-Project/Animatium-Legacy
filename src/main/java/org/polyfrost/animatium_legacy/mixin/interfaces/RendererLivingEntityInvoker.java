package org.polyfrost.animatium_legacy.mixin.interfaces;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RendererLivingEntity.class)
public interface RendererLivingEntityInvoker {
    @Invoker("setDoRenderBrightness")
    boolean animatium$setDoRenderBrightness(final EntityLivingBase livingEntity, final float tickDelta);

    @Invoker("unsetBrightness")
    void animatium$unsetBrightness();

    @Invoker("getColorMultiplier")
    int animatium$getColorMultiplier(final EntityLivingBase livingEntity, final float lightBrightness, final float tickDelta);
}
