package org.polyfrost.animatium_legacy

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.RenderEvent
import cc.polyfrost.oneconfig.events.event.Stage
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UDesktop
import cc.polyfrost.oneconfig.utils.Notifications
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import dulkirmod.config.Config
import dulkirmod.config.DulkirConfig
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import org.polyfrost.animatium_legacy.command.OldAnimationsCommand
import org.polyfrost.animatium_legacy.config.AnimatiumSettings
import org.polyfrost.animatium_legacy.gui.PleaseMigrateDulkirModGui
import java.net.URI

@Mod(
    modid = Animatium.MODID,
    name = Animatium.NAME,
    version = Animatium.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object Animatium {
    const val MODID: String = "@ID@"
    const val NAME: String = "@NAME@"
    const val VERSION: String = "@VER@"

    @JvmField
    var isPatcherPresent: Boolean = false

    @JvmField
    var doTheFunnyDulkirThing = false

    @JvmField
    var oldDulkirMod: Boolean = false
    private var customCrosshair = false

    @JvmField
    var isDamageTintPresent: Boolean = false

    @JvmField
    var isItemPhysics: Boolean = false

    @JvmField
    var isNEUPresent: Boolean = false

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        AnimatiumSettings.INSTANCE.preload()
        CommandManager.INSTANCE.registerCommand(OldAnimationsCommand())
        EventManager.INSTANCE.register(this)
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        if (Loader.isModLoaded("dulkirmod")) {
            doTheFunnyDulkirThing = true
        }

        isPatcherPresent = Loader.isModLoaded("patcher")
        customCrosshair = Loader.isModLoaded("custom-crosshair-mod")
        isDamageTintPresent = Loader.isModLoaded("damagetint")
        isItemPhysics = Loader.isModLoaded("itemphysic")
        isNEUPresent = Loader.isModLoaded("notenoughupdates")
    }

    @Mod.EventHandler
    fun onLoad(event: FMLLoadCompleteEvent) {
        if (customCrosshair) {
            AnimatiumSettings.smoothModelSneak = false
            AnimatiumSettings.INSTANCE.save()
            sendNotification(
                "Custom Crosshair Mod has been detected, which is written poorly and causes major issues with Animatium Legacy. Disabling Smooth Model Sneak. If you want a better crosshair mod, please click here to use PolyCrosshair instead.",
                5000f,
                Runnable {
                    UDesktop.browse(URI("https://modrinth.com/mod/crosshair"))
                })
        }
    }

    @Subscribe
    private fun onTick(event: RenderEvent) {
        if (event.stage == Stage.START && Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null && doTheFunnyDulkirThing && !AnimatiumSettings.didTheFunnyDulkirThingElectricBoogaloo) {
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

    private const val DEFAULT_NOTIFICATION_DURATION = 4000F

    @JvmStatic
    fun sendNotification(message: String, duration: Float, action: Runnable?) {
        Notifications.INSTANCE.send(NAME, message, duration, action)
    }

    @JvmStatic
    fun sendNotification(message: String, action: Runnable?) {
        sendNotification(message, DEFAULT_NOTIFICATION_DURATION, action)
    }

    @JvmStatic
    fun sendNotification(message: String, time: Float) {
        sendNotification(message, time, null)
    }

    @JvmStatic
    fun sendNotification(message: String) {
        sendNotification(message, DEFAULT_NOTIFICATION_DURATION)
    }

    private fun dulkirTrollage() {
        GuiUtils.displayScreen(PleaseMigrateDulkirModGui())
    }
}