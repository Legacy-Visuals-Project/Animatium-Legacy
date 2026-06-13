package org.polyfrost.animatium_legacy.mixin.v3.entity.armor_hurt.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
    @Shadow
    protected ModelBase mainModel;

    @Shadow
    protected abstract boolean setBrightness(final T livingEntity, final float tickDelta, final boolean combineTextures);

    @Shadow
    protected abstract float handleRotationFloat(final T livingBase, final float tickDelta);

    @Shadow
    protected abstract float interpolateRotation(final float par1, final float par2, final float par3);

    @Shadow
    protected abstract boolean setDoRenderBrightness(final T livingEntity, final float tickDelta);

    protected MixinRendererLivingEntity(final RenderManager renderManager) {
        super(renderManager);
    }

    @Redirect(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;setDoRenderBrightness(Lnet/minecraft/entity/EntityLivingBase;F)Z"))
    private boolean animatium$disableBrightness(final RendererLivingEntity<?> instance, final T livingEntity, final float tickDelta) {
        return (!AnimatiumSettings.INSTANCE.enabled || AnimatiumSettings.INSTANCE.armorDamageTintStyle() != ArmorTintStyle.V1_7) && setDoRenderBrightness(livingEntity, tickDelta);
    }

    @Redirect(method = "renderLayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;setBrightness(Lnet/minecraft/entity/EntityLivingBase;FZ)Z"))
    private boolean animatium$disableLayerBrightness(final RendererLivingEntity<?> instance, final T livingEntity, float tickDelta, final boolean combineTextures) {
        return (!AnimatiumSettings.INSTANCE.enabled || AnimatiumSettings.INSTANCE.armorDamageTintStyle() != ArmorTintStyle.V1_7) && setBrightness(livingEntity, tickDelta, combineTextures);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V"))
    private void animatium$renderHitColor(final T entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle() == ArmorTintStyle.V1_7) {
            final float f = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, tickDelta);
            final float f1 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, tickDelta);
            final float f2 = f1 - f;
            final float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * tickDelta;
            final float f8 = handleRotationFloat(entity, tickDelta);
            final float f5 = Math.min(entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * tickDelta, 1.0F);

            float f6 = entity.limbSwing - entity.limbSwingAmount * (1.0f - tickDelta);
            if (entity.isChild()) {
                f6 *= 3.0f;
            }

            final boolean hurt = entity.hurtTime > 0 || entity.deathTime > 0;
            HitColorHook.renderHitColorPre(entity, hurt, tickDelta, (RendererLivingEntity<?>) (Object) this);
            if (hurt) {
                this.mainModel.render(entity, f6, f5, f8, f2, f7, 0.0625f);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }

    @ModifyArg(method = "setBrightness", at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;", ordinal = 3), index = 0)
    private float animatium$orangesHitColor(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle() == ArmorTintStyle.V1_8_ORANGE_MARSHALL) {
            return 0.5F;
        } else {
            return original;
        }
    }
}
