package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.SwingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Inject(method = "dropItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"))
    private void animatium$dropItemSwing(final ItemStack droppedItem, final boolean dropAround, final boolean traceItem, final CallbackInfoReturnable<EntityItem> cir) {
        if (AnimatiumSettings.INSTANCE.enabled
                && AnimatiumSettings.modernDropSwing
                && Minecraft.getMinecraft().theWorld.isRemote
                && !(AnimatiumSettings.modernDropSwingFix && Minecraft.getMinecraft().currentScreen instanceof GuiChest)) {
            SwingHook.swingItem();
        }
    }
}
