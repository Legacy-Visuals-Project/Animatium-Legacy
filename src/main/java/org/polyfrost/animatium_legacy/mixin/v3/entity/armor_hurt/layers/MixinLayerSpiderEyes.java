package org.polyfrost.animatium_legacy.mixin.v3.entity.armor_hurt.layers;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySpider;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ArmorTintStyle;
import org.polyfrost.animatium_legacy.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSpiderEyes.class)
public abstract class MixinLayerSpiderEyes {
    @Shadow
    @Final
    private RenderSpider<?> spiderRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntitySpider;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    private void animatium$renderHitColor(final EntitySpider spider, final float f, final float g, final float tickDelta, final float h, final float i, final float j, final float scale, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.INSTANCE.armorDamageTintStyle() == ArmorTintStyle.V1_7) {
            final boolean hurt = spider.hurtTime > 0 || spider.deathTime > 0;
            HitColorHook.renderHitColorPre(spider, hurt, tickDelta, this.spiderRenderer);
            if (hurt) {
                this.spiderRenderer.getMainModel().render(spider, f, g, h, i, j, scale);
            }

            HitColorHook.renderHitColorPost(hurt);
        }
    }
}
