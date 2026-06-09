package org.polyfrost.animatium_legacy.mixin.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSheepWool.class)
public abstract class LayerSheepWoolMixin {
    @Shadow
    @Final
    private ModelSheep1 sheepModel;

    @Shadow
    @Final
    private RenderSheep sheepRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/passive/EntitySheep;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelSheep1;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void animatium$renderHitColor(final EntitySheep sheep, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.armorDamageTintStyle == 1 && AnimatiumSettings.INSTANCE.enabled) {
            final boolean hurt = sheep.hurtTime > 0 || sheep.deathTime > 0;
            HitColorHook.renderHitColorPre(sheep, hurt, tickDelta, this.sheepRenderer);
            if (hurt) {
                this.sheepModel.render(sheep, f, g, h, i, j, scale);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
