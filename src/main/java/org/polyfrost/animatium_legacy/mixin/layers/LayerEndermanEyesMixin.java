package org.polyfrost.animatium_legacy.mixin.layers;

import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.entity.monster.EntityEnderman;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerEndermanEyes.class)
public abstract class LayerEndermanEyesMixin {
    @Shadow
    @Final
    private RenderEnderman endermanRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntityEnderman;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void animatium$renderHitColor(final EntityEnderman enderman, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle() == ArmorTintStyle.V1_7) {
            final boolean hurt = enderman.hurtTime > 0 || enderman.deathTime > 0;
            HitColorHook.renderHitColorPre(enderman, hurt, tickDelta, this.endermanRenderer);
            if (hurt) {
                this.endermanRenderer.getMainModel().render(enderman, f, g, h, i, j, scale);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
