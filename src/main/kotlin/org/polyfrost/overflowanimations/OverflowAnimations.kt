package org.polyfrost.overflowanimations

//#if FORGE
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
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.RenderEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe
import org.polyfrost.oneconfig.api.platform.v1.Platform
import org.polyfrost.oneconfig.api.ui.v1.Notifications
import org.polyfrost.overflowanimations.command.OldAnimationsCommand
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
        CommandManager.registerCommand(OldAnimationsCommand())
        //#if FORGE
        EventManager.INSTANCE.register(this)
        //#endif
    }

    //#if FORGE
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        initialize()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        val loaderPlatform = Platform.loader()

        doTheFunnyDulkirThing = loaderPlatform.isModLoaded("dulkirmod")
        isPatcherPresent = loaderPlatform.isModLoaded("patcher")
        customCrosshair = loaderPlatform.isModLoaded("custom-crosshair-mod")
        isDamageTintPresent = loaderPlatform.isModLoaded("damagetint")
        isItemPhysics = loaderPlatform.isModLoaded("itemphysic")
        isNEUPresent = loaderPlatform.isModLoaded("notenoughupdates")
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
    private fun onTick(event: RenderEvent.Start) {
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