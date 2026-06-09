package org.polyfrost.animatium_legacy.gui;

import cc.polyfrost.oneconfig.gui.OneConfigGui;
import cc.polyfrost.oneconfig.gui.elements.BasicButton;
import cc.polyfrost.oneconfig.libs.universal.UResolution;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import cc.polyfrost.oneconfig.renderer.font.Fonts;
import cc.polyfrost.oneconfig.utils.InputHandler;
import cc.polyfrost.oneconfig.utils.color.ColorPalette;
import cc.polyfrost.oneconfig.utils.color.ColorUtils;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import cc.polyfrost.oneconfig.utils.gui.OneUIScreen;
import org.polyfrost.animatium_legacy.Animatium;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.AnimationExportUtils;

public class PleaseMigrateDulkirModGui extends OneUIScreen {
    private static final int GRAY_800 = ColorUtils.getColor(21, 22, 23, 255);
    private static final int WHITE_90 = ColorUtils.getColor(255, 255, 255, 229);
    private static final String TRANSFER = "Transfer";
    private static final String CANCEL = "Cancel";
    private final BasicButton transferButton = new BasicButton(-1, 40, TRANSFER, 2, ColorPalette.PRIMARY);
    private final BasicButton cancelButton = new BasicButton(-1, 40, CANCEL, 2, ColorPalette.PRIMARY_DESTRUCTIVE);
    private int ticks = 0;

    @Override
    public void draw(final long vg, final float tickDelta, final InputHandler inputHandler) {
        if (ticks < 10) {
            ticks++;
            if (ticks == 10) {
                markAsViewed();
            }
        }

        if (transferButton.getWidth() == -1) {
            transferButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, TRANSFER, 14f, Fonts.MEDIUM) + 40));
            transferButton.setClickAction(() -> {
                markAsViewed();
                AnimationExportUtils.transferDulkirConfig();
                GuiUtils.displayScreen(null);
            });
        }

        if (cancelButton.getWidth() == -1) {
            cancelButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, CANCEL, 14f, Fonts.MEDIUM) + 40));
            cancelButton.setClickAction(() -> {
                cancelButton.setText("Confirm");
                cancelButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, "Confirm", 14f, Fonts.MEDIUM) + 40));
                cancelButton.setClickAction(() -> {
                    markAsViewed();
                    cancelButton.setText(CANCEL);
                    cancelButton.setWidth((int) (NanoVGHelper.INSTANCE.getTextWidth(vg, CANCEL, 14f, Fonts.MEDIUM) + 40));
                    GuiUtils.displayScreen(null);
                });
            });
        }

        float scale = OneConfigGui.getScaleFactor();
        int x = (int) ((UResolution.getWindowWidth() - 600 * scale) / 2f / scale);
        int y = (int) ((UResolution.getWindowHeight() - 240 * scale) / 2f / scale);
        NanoVGHelper.INSTANCE.scale(vg, scale, scale);
        inputHandler.scale(scale, scale);

        NanoVGHelper.INSTANCE.drawRoundedRect(vg, x, y, 600, 240, GRAY_800, 20);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "Animatium Legacy", x + 300, y + 40, WHITE_90, 28, Fonts.MEDIUM);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "Animatium Legacy now replaces DulkirMod's animations feature.", x + 300, y + 100, WHITE_90, 16, Fonts.REGULAR);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "Would you like to import your DulkirMod config?", x + 300, y + 120, WHITE_90, 16, Fonts.REGULAR);
        NanoVGHelper.INSTANCE.drawCenteredText(vg, "(You can transfer your config later in the settings)", x + 300, y + 140, WHITE_90, 12, Fonts.REGULAR);

        transferButton.draw(vg, x + 300 - transferButton.getWidth(), y + 180, inputHandler);
        cancelButton.draw(vg, x + 300 + 10, y + 180, inputHandler);
    }

    private void markAsViewed() {
        Animatium.doTheFunnyDulkirThing = false;
        AnimatiumSettings.didTheFunnyDulkirThingElectricBoogaloo = true;
        AnimatiumSettings.INSTANCE.save();
    }
}
