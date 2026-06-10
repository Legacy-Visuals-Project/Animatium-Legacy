package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.mixin.interfaces.RendererLivingEntityInvoker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
    @Shadow
    @Final
    private RendererLivingEntity<?> renderer;

    @Unique
    private static ModelBase animatium$entity = null;

    @ModifyVariable(method = "renderLayer", at = @At(value = "STORE"), name = "t")
    private T animatium$captureEntity(final T entity) {
        animatium$entity = entity;
        return entity;
    }

    @Inject(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void animatium$addRender(final EntityLivingBase entitylivingbaseIn, final float p_177182_2_, final float p_177182_3_, final float tickDelta, final float p_177182_5_, final float p_177182_6_, final float p_177182_7_, final float scale, final int armorSlot, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle == ArmorTintStyle.V1_8 && ((RendererLivingEntityInvoker) renderer).animatium$setDoRenderBrightness(entitylivingbaseIn, tickDelta)) {
            animatium$entity.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
            ((RendererLivingEntityInvoker) this.renderer).animatium$unsetBrightness();
        }
    }

    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$allowCombine(final CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle.hasRedOverlay());
    }
}
