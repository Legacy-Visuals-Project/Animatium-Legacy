package org.polyfrost.animatium_legacy.mixin.v3.entity.armor_hurt.layers;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.passive.EntityPig;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSaddle.class)
public abstract class MixinLayerSaddle {
    @Shadow
    @Final
    private ModelPig pigModel;

    @Shadow
    @Final
    private RenderPig pigRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/passive/EntityPig;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPig;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void animatium$renderHitColor(final EntityPig pig, final float var2, final float var3, final float var4, final float var5, final float var6, final float var7, final float var8, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle() == ArmorTintStyle.V1_7) {
            final boolean hurt = pig.hurtTime > 0 || pig.deathTime > 0;
            HitColorHook.renderHitColorPre(pig, hurt, var4, this.pigRenderer);
            if (hurt) {
                this.pigModel.render(pig, var2, var3, var5, var6, var7, var8);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
