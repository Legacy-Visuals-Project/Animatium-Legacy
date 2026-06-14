package org.polyfrost.animatium_legacy.hooks

import net.minecraft.client.renderer.block.model.ItemCameraTransforms

object TransformTypeHook {
    val excludeGlintTransforms =
        setOf(ItemCameraTransforms.TransformType.GROUND, ItemCameraTransforms.TransformType.FIXED)

    @JvmField
    var transform: ItemCameraTransforms.TransformType? = null

    @JvmStatic
    fun shouldBeSprite() = this.shouldNotHaveGlint() || this.isRenderingInGUI()

    @JvmStatic
    fun isRenderingInGUI() = transform == ItemCameraTransforms.TransformType.GUI

    @JvmStatic
    fun shouldNotHaveGlint() = this.transform != null && excludeGlintTransforms.contains(this.transform)
}