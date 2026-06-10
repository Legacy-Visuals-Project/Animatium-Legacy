package org.polyfrost.animatium_legacy.mixin.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSlimeGel.class)
public abstract class LayerSlimeGelMixin {
    @Shadow
    @Final
    private ModelBase slimeModel;

    @Shadow
    @Final
    private RenderSlime slimeRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntitySlime;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER), cancellable = true)
    private void animatium$renderHitColor(final EntitySlime slime, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle == ArmorTintStyle.V1_7) {
            final boolean hurt = slime.hurtTime > 0 || slime.deathTime > 0;
            HitColorHook.renderHitColorPre(slime, hurt, tickDelta, this.slimeRenderer);
            if (hurt) {
                this.slimeModel.render(slime, f, g, h, i, j, scale);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
