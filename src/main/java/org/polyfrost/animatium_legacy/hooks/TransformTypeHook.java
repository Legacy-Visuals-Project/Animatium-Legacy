package org.polyfrost.animatium_legacy.hooks;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;

import java.util.EnumSet;

public final class TransformTypeHook {
    public static ItemCameraTransforms.TransformType transform;
    private static final EnumSet<ItemCameraTransforms.TransformType> cameraTypes = EnumSet.of(ItemCameraTransforms.TransformType.GROUND, ItemCameraTransforms.TransformType.FIXED);

    public static boolean shouldBeSprite() {
        return shouldNotHaveGlint() || isRenderingInGUI();
    }

    public static boolean isRenderingInGUI() {
        return transform == ItemCameraTransforms.TransformType.GUI;
    }

    public static boolean shouldNotHaveGlint() {
        return cameraTypes.contains(TransformTypeHook.transform);
    }
}
