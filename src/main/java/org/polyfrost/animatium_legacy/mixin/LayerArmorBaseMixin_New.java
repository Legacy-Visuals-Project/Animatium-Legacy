package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * Modified from Skyblockcatia under MIT License
 * <a href="https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md">...</a>
 */
@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin_New<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
    @Inject(method = "renderGlint(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/model/ModelBase;FFFFFFF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V", ordinal = 0))
    private void animatium$renderNewArmorGlintPre(final EntityLivingBase entitylivingbaseIn, final T modelbaseIn, final float p_177183_3_, final float p_177183_4_, final float tickDelta, final float p_177183_6_, final float p_177183_7_, final float p_177183_8_, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlintNew) {
            final float light = 240.0F;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);
        }
    }

    @Inject(method = "renderGlint(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/model/ModelBase;FFFFFFF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/model/ModelBase.render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void animatium$renderNewArmorGlintPost(final EntityLivingBase entitylivingbaseIn, final T modelbaseIn, final float p_177183_3_, final float p_177183_4_, final float tickDelta, final float p_177183_6_, final float p_177183_7_, final float p_177183_8_, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlintNew) {
            final int light = entitylivingbaseIn.getBrightnessForRender(tickDelta);
            final int u = light % 65536;
            final int v = light / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, u, v);
        }
    }

    @ModifyArgs(method = "renderGlint(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/model/ModelBase;FFFFFFF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V", ordinal = 1))
    private void animatium$newArmorGlintColor(final Args args) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlintNew) {
            final int rgb = animatium$getRGB((int) (((float) args.get(0)) * 255), (int) (((float) args.get(1)) * 255), (int) (((float) args.get(2)) * 255), (int) (((float) args.get(3)) * 255));
            if (rgb == -8372020 || rgb == -10473317) {
                args.set(0, 0.5608F);
                args.set(1, 0.3408F);
                args.set(2, 0.8608F);
                args.set(3, 1.0F);
            }
        }
    }

    @Unique
    private static int animatium$getRGB(final int r, final int g, final int b, final int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }
}
