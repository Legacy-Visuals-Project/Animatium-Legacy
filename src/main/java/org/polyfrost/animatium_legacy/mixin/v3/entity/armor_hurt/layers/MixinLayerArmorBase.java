package org.polyfrost.animatium_legacy.mixin.v3.entity.armor_hurt.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
    @Shadow
    public abstract T getArmorModel(final int slot);

    @Shadow
    @Final
    private RendererLivingEntity<?> renderer;

    @Inject(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal = 1, shift = At.Shift.AFTER))
    private void animatium$renderHitColor(final EntityLivingBase livingEntity, final float p_177182_2_, final float p_177182_3_, final float tickDelta, final float p_177182_5_, final float p_177182_6_, final float p_177182_7_, final float scale, final int slot, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle() == ArmorTintStyle.V1_7) {
            final T armorModel = this.getArmorModel(slot);

            final boolean hurt = livingEntity.hurtTime > 0 || livingEntity.deathTime > 0;
            HitColorHook.renderHitColorPre(livingEntity, hurt, tickDelta, renderer);
            if (hurt) {
                armorModel.render(livingEntity, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
