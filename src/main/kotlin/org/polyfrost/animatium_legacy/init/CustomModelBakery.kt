package org.polyfrost.animatium_legacy.init

import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelBakeEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.client.model.IModel
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.model.ModelLoaderRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.polyfrost.animatium_legacy.Animatium

enum class CustomModelBakery(modelPath: String) {
    BOTTLE_OVERLAY("item/bottle_overlay"),
    BOTTLE_DRINKABLE_EMPTY("item/bottle_drinkable_empty"),
    BOTTLE_SPLASH_EMPTY("item/bottle_splash_empty"),
    SKULL_CHAR("item/skull_char"),
    SKULL_CREEPER("item/skull_creeper"),
    SKULL_SKELETON("item/skull_skeleton"),
    SKULL_WITHER("item/skull_wither"),
    SKULL_ZOMBIE("item/skull_zombie");

    private val resourceLocation = ResourceLocation(Animatium.MODID, modelPath)
    private lateinit var loadedModel: IModel
    lateinit var bakedModel: IBakedModel
        private set

    private fun stitch(map: TextureMap) {
        loadedModel =
            ModelLoaderRegistry.getModel(resourceLocation).apply { textures.forEach { map.registerSprite(it) } }
    }

    private fun bake() {
        bakedModel =
            loadedModel.bake(loadedModel.defaultState, DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter())
    }

    companion object {
        init {
            MinecraftForge.EVENT_BUS.register(this)
        }

        @SubscribeEvent
        fun onStitch(event: TextureStitchEvent.Pre) {
            CustomModelBakery.entries.forEach { it.stitch(event.map) }
        }

        @SubscribeEvent
        fun onBake(event: ModelBakeEvent) {
            CustomModelBakery.entries.forEach { it.bake() }
        }
    }
}