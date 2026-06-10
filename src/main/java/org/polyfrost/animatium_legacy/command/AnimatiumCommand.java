package org.polyfrost.animatium_legacy.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;

@Command(value = "animatium", aliases = {"animatium_legacy", "oam", "overflowanimations", "oldanimations", "animations"}, description = "Animatium Legacy")
public final class AnimatiumCommand {
    @Main
    public void handle() {
        AnimatiumSettings.INSTANCE.openGui();
    }
}
