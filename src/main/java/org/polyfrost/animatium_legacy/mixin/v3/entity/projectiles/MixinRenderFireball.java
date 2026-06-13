package org.polyfrost.animatium_legacy.mixin.v3.entity.projectiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderFireball.class)
public abstract class MixinRenderFireball extends Render<EntityFireball> {
    protected MixinRenderFireball(final RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/projectile/EntityFireball;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V", shift = At.Shift.AFTER), cancellable = true)
    private void animatium$changeToModel(final EntityFireball entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        if (settings.enabled) {
            final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
            if (AnimatiumSettings.globalPositions) {
                GlStateManager.translate(advanced.fireballPositionX, advanced.fireballPositionY, advanced.fireballPositionZ);
                GlStateManager.rotate(advanced.fireballRotationPitch, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(advanced.fireballRotationYaw, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(advanced.fireballRotationRoll, 0.0F, 0.0F, 1.0F);
                GlStateManager.scale(1.0F * Math.exp(advanced.fireballScale), 1.0F * Math.exp(advanced.fireballScale), 1.0F * Math.exp(advanced.fireballScale));
            }

            if (AnimatiumSettings.fireballModel) {
                final RenderItem instance = Minecraft.getMinecraft().getRenderItem();
                final ItemStack stack = new ItemStack(Items.fire_charge, 1, 0);
                GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                instance.renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.disableRescaleNormal();
                GlStateManager.popMatrix();
                super.doRender(entity, x, y, z, entityYaw, tickDelta);
                ci.cancel();
            }
        }
    }
}
