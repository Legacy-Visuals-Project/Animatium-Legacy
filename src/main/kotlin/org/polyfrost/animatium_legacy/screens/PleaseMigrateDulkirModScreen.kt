package org.polyfrost.animatium_legacy.screens

import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.gui.elements.BasicButton
import cc.polyfrost.oneconfig.libs.universal.UResolution
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.font.Fonts
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.color.ColorPalette
import cc.polyfrost.oneconfig.utils.color.ColorUtils
import cc.polyfrost.oneconfig.utils.gui.GuiUtils
import cc.polyfrost.oneconfig.utils.gui.OneUIScreen
import org.polyfrost.animatium_legacy.Animatium
import org.polyfrost.animatium_legacy.config.AnimatiumSettings
import org.polyfrost.animatium_legacy.hooks.AnimationExportUtils

class PleaseMigrateDulkirModScreen : OneUIScreen() {
    companion object {
        const val TRANSFER = "Transfer"
        const val CONFIRM = "Confirm"
        const val CANCEL = "Cancel"

        val GRAY_800 = ColorUtils.getColor(21, 22, 23, 255)
        val WHITE_90 = ColorUtils.getColor(255, 255, 255, 229)
    }

    private val transferButton = BasicButton(-1, 40, TRANSFER, 2, ColorPalette.PRIMARY)
    private val cancelButton = BasicButton(-1, 40, CANCEL, 2, ColorPalette.PRIMARY_DESTRUCTIVE)
    private var ticks = 0

    override fun draw(vg: Long, partialTicks: Float, inputHandler: InputHandler) {
        if (this.ticks < 10) {
            this.ticks++
            if (this.ticks == 10) {
                this.markAsViewed()
            }
        }

        if (this.transferButton.width == -1) {
            this.transferButton.width =
                (NanoVGHelper.INSTANCE.getTextWidth(vg, TRANSFER, 14f, Fonts.MEDIUM) + 40).toInt()
            this.transferButton.setClickAction {
                this.markAsViewed()
                AnimationExportUtils.transferDulkirConfig()
                GuiUtils.displayScreen(null)
            }
        }

        if (this.cancelButton.width == -1) {
            this.cancelButton.width = (NanoVGHelper.INSTANCE.getTextWidth(vg, CANCEL, 14f, Fonts.MEDIUM) + 40).toInt()
            this.cancelButton.setClickAction {
                this.cancelButton.text = CONFIRM
                this.cancelButton.width =
                    (NanoVGHelper.INSTANCE.getTextWidth(vg, CONFIRM, 14f, Fonts.MEDIUM) + 40).toInt()
                this.cancelButton.setClickAction {
                    this.markAsViewed()
                    this.cancelButton.text = CANCEL
                    this.cancelButton.width =
                        (NanoVGHelper.INSTANCE.getTextWidth(vg, CANCEL, 14f, Fonts.MEDIUM) + 40).toInt()
                    GuiUtils.displayScreen(null)
                }
            }
        }

        val scale = OneConfigGui.getScaleFactor()
        val x = ((UResolution.windowWidth - 600 * scale) / 2.0F / scale)
        val y = ((UResolution.windowHeight - 240 * scale) / 2.0F / scale)
        NanoVGHelper.INSTANCE.scale(vg, scale, scale)
        inputHandler.scale(scale.toDouble(), scale.toDouble())

        NanoVGHelper.INSTANCE.drawRoundedRect(vg, x, y, 600.0F, 240.0F, GRAY_800, 20.0F)
        NanoVGHelper.INSTANCE.drawCenteredText(
            vg,
            "Animatium Legacy",
            x + 300.0F,
            y + 40.0F,
            WHITE_90,
            28.0F,
            Fonts.MEDIUM
        )
        NanoVGHelper.INSTANCE.drawCenteredText(
            vg,
            "Animatium Legacy now replaces DulkirMod's animations feature.",
            x + 300,
            y + 100,
            WHITE_90,
            16.0F,
            Fonts.REGULAR
        )
        NanoVGHelper.INSTANCE.drawCenteredText(
            vg,
            "Would you like to import your DulkirMod config?",
            x + 300,
            y + 120,
            WHITE_90,
            16.0F,
            Fonts.REGULAR
        )
        NanoVGHelper.INSTANCE.drawCenteredText(
            vg,
            "(You can transfer your config later in the settings)",
            x + 300,
            y + 140,
            WHITE_90,
            12.0F,
            Fonts.REGULAR
        )

        this.transferButton.draw(vg, x + 300 - transferButton.width, y + 180, inputHandler)
        this.cancelButton.draw(vg, x + 300 + 10, y + 180, inputHandler)
    }

    private fun markAsViewed() {
        Animatium.doTheFunnyDulkirThing = false
        AnimatiumSettings.didTheFunnyDulkirThingElectricBoogaloo = true
        AnimatiumSettings.INSTANCE.save()
    }
}