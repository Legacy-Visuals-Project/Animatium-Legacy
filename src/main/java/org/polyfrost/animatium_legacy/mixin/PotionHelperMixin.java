package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IntegerCache;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.PotionColors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

import static net.minecraft.potion.PotionHelper.calcPotionLiquidColor;
import static net.minecraft.potion.PotionHelper.getPotionEffects;

@Mixin(value = PotionHelper.class)
public abstract class PotionHelperMixin {
    @Shadow
    @Final
    private static Map<Integer, Integer> DATAVALUE_COLORS;

    @Redirect(method = "calcPotionLiquidColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/Potion;getLiquidColor()I"))
    private static int animatium$recolorPotions(final Potion instance, final Collection<PotionEffect> collection) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.modernPotColors) {
            for (final PotionEffect potionEffect : collection) {
                return PotionColors.POTION_COLORS.get(potionEffect.getPotionID());
            }
        }

        return instance.getLiquidColor();
    }

    @Inject(method = "getLiquidColor", at = @At("HEAD"))
    private static void animatium$checkColor(final int dataValue, final boolean bypassCache, final CallbackInfoReturnable<Integer> cir) {
        if (PotionColors.shouldReload) {
            PotionColors.shouldReload = false;
            DATAVALUE_COLORS.clear();
            for (final int index : PotionColors.POTION_COLORS.values()) {
                final int color = calcPotionLiquidColor(getPotionEffects(index, false));
                final Integer integer = IntegerCache.getInteger(index);
                DATAVALUE_COLORS.put(integer, color);
            }
        }
    }
}
