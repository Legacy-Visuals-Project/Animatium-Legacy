package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameForge.class)
public class GuiIngameForgeMixin extends GuiIngame {

    @Shadow(remap = false)
    private ScaledResolution res;

    public GuiIngameForgeMixin(Minecraft minecraft) {
        super(minecraft);
    }

    @Redirect(method = "renderHUDText", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/GuiIngameForge;drawRect(IIIII)V"))
    private void animatium$cancelBackgroundDrawing(int left, int top, int right, int bottom, int color) {
        if (!AnimatiumSettings.INSTANCE.enabled || !(AnimatiumSettings.INSTANCE.debugScreenMode == 0 ||
                AnimatiumSettings.INSTANCE.debugScreenMode == 2)) {
            GuiIngameForge.drawRect(left, top, right, bottom, color);
        }
    }

    @Redirect(method = "renderHUDText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int animatium$removeShadow(FontRenderer fontRenderer, String text, int x, int y, int color) {
        return fontRenderer.drawString(text, x, y, color, (AnimatiumSettings.INSTANCE.debugScreenMode == 0 || AnimatiumSettings.INSTANCE.debugScreenMode == 2)
                && AnimatiumSettings.INSTANCE.enabled);
    }

    @ModifyVariable(method = "renderHealth", at = @At(value = "LOAD", ordinal = 1), index = 5, remap = false)
    private boolean animatium$cancelFlash(boolean original) {
        return (!AnimatiumSettings.oldHealth || !AnimatiumSettings.INSTANCE.enabled) && original;
    }

}
