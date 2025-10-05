package org.polyfrost.overflowanimations

//#if FORGE
import com.mojang.brigadier.context.CommandContext
import dev.deftu.omnicore.api.client.commands.OmniClientCommandSource
import dev.deftu.omnicore.api.client.commands.OmniClientCommands
import dev.deftu.omnicore.api.loader.OmniLoader
import dulkirmod.config.Config
import dulkirmod.config.DulkirConfig
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
//#else
//$$ import net.fabricmc.api.ClientModInitializer
//#endif

import net.minecraft.client.Minecraft
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.RenderEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe
import org.polyfrost.oneconfig.api.ui.v1.Notifications
import org.polyfrost.oneconfig.utils.v1.dsl.createScreen
import org.polyfrost.overflowanimations.config.OldAnimationsSettings

//import org.polyfrost.overflowanimations.gui.PleaseMigrateDulkirModGui
//import org.polyfrost.universal.UDesktop
//import java.net.URI

//#if FORGE
@Mod(modid = OverflowAnimations.ID, version = OverflowAnimations.VERSION, name = OverflowAnimations.NAME, modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter")
//#endif
object OverflowAnimations
    //#if FABRIC
    //$$ : ClientModInitializer
    //#endif
{

    const val ID: String = "@MOD_ID@"
    const val NAME: String = "@MOD_NAME@"
    const val VERSION: String = "@MOD_VERSION@"

    //#if FORGE
    @JvmField
    var isPatcherPresent: Boolean = false

    private var doTheFunnyDulkirThing = false

    @JvmField
    var oldDulkirMod: Boolean = false

    private var customCrosshair = false

    @JvmField
    var isDamageTintPresent: Boolean = false

    @JvmField
    var isItemPhysics: Boolean = false

    @JvmField
    var isNEUPresent: Boolean = false
    //#endif

    fun initialize() {
        OldAnimationsSettings.INSTANCE.preload()
        initializeCommands()

        //#if FORGE
        EventManager.INSTANCE.register(this)
        //#endif
    }

    private fun initializeCommands() {
        val executor = { ctx: CommandContext<OmniClientCommandSource> ->
            ctx.source.openScreen(OldAnimationsSettings.INSTANCE.createScreen())
        }

        val node = OmniClientCommands.literal("animatium")
            .executes(executor)
            .build()
        OmniClientCommands.register(node)

        val aliases = listOf("overflowanimations", "oam", "oldanimations", "animations")
        for (alias in aliases) {
            val aliasNode = OmniClientCommands.literal(alias)
                .executes(executor)
                .redirect(node)
                .build()
            OmniClientCommands.register(aliasNode)
        }
    }

    //#if FORGE
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        initialize()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        doTheFunnyDulkirThing = OmniLoader.isLoaded("dulkirmod")
        isPatcherPresent = OmniLoader.isLoaded("patcher")
        customCrosshair = OmniLoader.isLoaded("custom-crosshair-mod")
        isDamageTintPresent = OmniLoader.isLoaded("damagetint")
        isItemPhysics = OmniLoader.isLoaded("itemphysic")
        isNEUPresent = OmniLoader.isLoaded("notenoughupdates")
    }

    @Mod.EventHandler
    fun onLoad(event: FMLLoadCompleteEvent) {
        if (customCrosshair) {
            OldAnimationsSettings.smoothModelSneak = false
            OldAnimationsSettings.INSTANCE.save()
            Notifications.enqueue(
                Notifications.Type.Warning,
                "OverflowAnimations",
                "Custom Crosshair Mod has been detected, which is written poorly and causes major issues with OverflowAnimations. Disabling Smooth Model Sneak. If you want a better crosshair mod, please click here to use PolyCrosshair instead."
//                Runnable {
//                UDesktop.browse(URI("https://modrinth.com/mod/crosshair"))
//            }
            )
        }
    }

    @Subscribe
    private fun onTick(event: RenderEvent.Pre) {
        if (Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null && doTheFunnyDulkirThing && !OldAnimationsSettings.didTheFunnyDulkirThingElectricBoogaloo) {
            try {
                Class.forName("dulkirmod.config.DulkirConfig")
                if (DulkirConfig.INSTANCE.customAnimations) {
                    dulkirTrollage()
                }
            } catch (e: ClassNotFoundException) {
                oldDulkirMod = true
                if (Config.INSTANCE.customAnimations) {
                    dulkirTrollage()
                }
            }
        }
    }

    private fun dulkirTrollage() {
//        GuiUtils.displayScreen(PleaseMigrateDulkirModGui())
        doTheFunnyDulkirThing = false
        OldAnimationsSettings.didTheFunnyDulkirThingElectricBoogaloo = true
        OldAnimationsSettings.INSTANCE.save()
    }
    //#else
    //$$ override fun onInitializeClient() {
    //$$     initialize()
    //$$ }
    //#endif

}