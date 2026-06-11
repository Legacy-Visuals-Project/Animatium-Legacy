package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;
import org.polyfrost.animatium_legacy.hooks.PatcherConfigHook;
import org.polyfrost.animatium_legacy.hooks.SmoothSneakHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderFish.class, priority = 2000)
public abstract class RenderFishMixin {
    @ModifyVariable(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "STORE", ordinal = 0), name = "vec3")
    private Vec3 animatium$modifyLinePosition(final Vec3 vec3) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
            final double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
            final double decimalFov = fov / 110;
            final boolean isParallaxOffset = PatcherConfigHook.isParallaxFixEnabled();

            double xCoord = vec3.xCoord;
            double yCoord = vec3.yCoord;
            double zCoord = vec3.zCoord;
            if (AnimatiumSettings.fishingRodPosition && !AnimatiumSettings.fixRod) {
                xCoord = -0.5D + (isParallaxOffset ? -0.1D : 0.0D);
                yCoord = 0.03D;
                zCoord = 0.8D;
            } else if (AnimatiumSettings.fixRod) {
                xCoord = (-decimalFov + (decimalFov / 2.5) - (decimalFov / 8)) + 0.16 + (isParallaxOffset ? 0.15D : 0.0D);
                yCoord = 0.0D;
                zCoord = 0.4D;
            }

            if (ItemPositionAdvancedSettings.customRodLine) {
                xCoord = advanced.fishingLinePositionX;
                yCoord = advanced.fishingLinePositionY;
                zCoord = advanced.fishingLinePositionZ;
            }

            return new Vec3(xCoord, yCoord, zCoord);
        } else {
            return vec3;
        }
    }

    @ModifyConstant(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", constant = @Constant(doubleValue = 0.8D))
    private double animatium$moveLinePosition(final double constant) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.fishingStick) {
            return 0.85D;
        } else {
            return constant;
        }
    }

    @Redirect(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getEyeHeight()F"))
    private float animatium$modifyEyeHeight(final EntityPlayer instance) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.smoothSneaking) {
            return SmoothSneakHook.getSmoothSneak();
        } else {
            return instance.getEyeHeight();
        }
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;begin(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V", ordinal = 1))
    private void animatium$modifyLineThickness(final EntityFishHook entity, final double x, final double y, final double z, final float entityYaw, final float tickDelta, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            GL11.glLineWidth(AnimatiumSettings.INSTANCE.rodThickness + 1.0F);
        }
    }
}
