package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.GuiIngameForge;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameForge.class)
public abstract class GuiIngameForgeMixin extends GuiIngame {
    public GuiIngameForgeMixin(final Minecraft minecraft) {
        super(minecraft);
    }

    @Redirect(method = "renderHUDText", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/GuiIngameForge;drawRect(IIIII)V"))
    private void animatium$cancelBackgroundDrawing(final int left, final int top, final int right, final int bottom, final int color) {
        if (!AnimatiumSettings.INSTANCE.enabled || AnimatiumSettings.INSTANCE.debugScreenMode().hasBackground()) {
            GuiIngameForge.drawRect(left, top, right, bottom, color);
        }
    }

    @Redirect(method = "renderHUDText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    private int animatium$removeShadow(final FontRenderer fontRenderer, final String text, final int x, final int y, final int color) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            return fontRenderer.drawString(text, x, y, color, !AnimatiumSettings.INSTANCE.debugScreenMode().hasBackground());
        } else {
            return fontRenderer.drawString(text, x, y, color);
        }
    }

    @ModifyVariable(method = "renderHealth", at = @At(value = "LOAD", ordinal = 1), remap = false, name = "highlight")
    private boolean animatium$cancelFlash(final boolean original) {
        return (!AnimatiumSettings.INSTANCE.enabled || !AnimatiumSettings.oldHealth) && original;
    }
}
