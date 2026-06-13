package org.polyfrost.animatium_legacy.mixin.forge;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import org.polyfrost.animatium_legacy.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public abstract class MixinForgeHooksClient {
    @Inject(method = "handleCameraTransforms", at = @At("HEAD"))
    private static void animatium$getCameraPerspective(final IBakedModel model, final ItemCameraTransforms.TransformType cameraTransformType, final CallbackInfoReturnable<IBakedModel> cir) {
        TransformTypeHook.transform = cameraTransformType;
    }
}
