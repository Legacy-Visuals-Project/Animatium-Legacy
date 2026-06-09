package org.polyfrost.animatium_legacy.hooks;

import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import com.google.gson.Gson;
import dulkirmod.config.Config;
import dulkirmod.config.DulkirConfig;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.polyfrost.animatium_legacy.Animatium;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Base64;

public final class AnimationExportUtils {
    private static final Gson GSON = new Gson();

    public static void exportItemPositions() {
        String string = Base64.getEncoder().encodeToString(GSON.toJson(new AnimatiumConfigData()).getBytes());
        StringSelection selection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
        Animatium.sendNotification("Exported item positions to clipboard");
    }

    public static void importItemPositions() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String base64 = (String) clipboard.getData(DataFlavor.stringFlavor);
            String jsonString = new String(Base64.getDecoder().decode(base64));
            if (jsonString.contains("ignoreHaste")) {
                System.out.println("Detected DulkirMod config data, trying to import");
                try {
                    DulkirConfigData importSettings = GSON.fromJson(jsonString, DulkirConfigData.class);
                    importDulkir(importSettings);
                } catch (Exception e) {
                    e.printStackTrace();
                    Animatium.sendNotification("Failed to detect clipboard data! Please make sure you have copied a valid config from either Animatium Legacy or DulkirMod!");
                }
            }
            System.out.println("Detected Animatium Legacy config data, trying to import");
            try {
                AnimatiumConfigData importSettings = GSON.fromJson(jsonString, AnimatiumConfigData.class);
                importAnimatium(importSettings);
            } catch (Exception ignored) {
                Animatium.sendNotification("Failed to detect clipboard data! Please make sure you have copied a valid config from either Animatium Legacy or DulkirMod!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Animatium.sendNotification("Failed to decode clipboard data");
        }
    }

    public static void importAnimatium(final AnimatiumConfigData importSettings) {
        AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
        settings.itemPositionX = importSettings.itemPositionX;
        settings.itemPositionY = importSettings.itemPositionY;
        settings.itemPositionZ = importSettings.itemPositionZ;
        settings.itemRotationYaw = importSettings.itemRotationYaw;
        settings.itemRotationPitch = importSettings.itemRotationPitch;
        settings.itemRotationRoll = importSettings.itemRotationRoll;
        settings.itemScale = importSettings.itemScale;
        advanced.itemSwingPositionX = importSettings.itemSwingPositionX;
        advanced.itemSwingPositionY = importSettings.itemSwingPositionY;
        advanced.itemSwingPositionZ = importSettings.itemSwingPositionZ;
        settings.itemSwingSpeed = importSettings.itemSwingSpeed;
        settings.itemSwingSpeedHaste = importSettings.itemSwingSpeedHaste;
        settings.itemSwingSpeedFatigue = importSettings.itemSwingSpeedFatigue;
        settings.swingSetting = importSettings.shouldScaleSwing ? 1 : 0;
        advanced.consumePositionX = importSettings.consumePositionX;
        advanced.consumePositionY = importSettings.consumePositionY;
        advanced.consumePositionZ = importSettings.consumePositionZ;
        advanced.consumeRotationYaw = importSettings.consumeRotationYaw;
        advanced.consumeRotationPitch = importSettings.consumeRotationPitch;
        advanced.consumeRotationRoll = importSettings.consumeRotationRoll;
        advanced.consumeScale = importSettings.consumeScale;
        advanced.consumeIntensity = importSettings.consumeIntensity;
        advanced.consumeSpeed = importSettings.consumeSpeed;
        ItemPositionAdvancedSettings.shouldScaleEat = importSettings.shouldScaleEat;
        advanced.blockedPositionX = importSettings.blockedPositionX;
        advanced.blockedPositionY = importSettings.blockedPositionY;
        advanced.blockedPositionZ = importSettings.blockedPositionZ;
        advanced.blockedRotationYaw = importSettings.blockedRotationYaw;
        advanced.blockedRotationPitch = importSettings.blockedRotationPitch;
        advanced.blockedRotationRoll = importSettings.blockedRotationRoll;
        advanced.blockedScale = importSettings.blockedScale;
        advanced.projectilePositionX = importSettings.projectilePositionX;
        advanced.projectilePositionY = importSettings.projectilePositionY;
        advanced.projectilePositionZ = importSettings.projectilePositionZ;
        advanced.projectileRotationYaw = importSettings.projectileRotationYaw;
        advanced.projectileRotationPitch = importSettings.projectileRotationPitch;
        advanced.projectileRotationRoll = importSettings.projectileRotationRoll;
        advanced.projectileScale = importSettings.projectileScale;

        settings.save();
    }

    public static void importDulkir(final DulkirConfigData importSettings) {
        AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
        settings.itemPositionX = importSettings.getX();
        settings.itemPositionY = importSettings.getY();
        settings.itemPositionZ = importSettings.getZ();
        settings.itemRotationYaw = importSettings.getYaw();
        settings.itemRotationPitch = importSettings.getPitch();
        settings.itemRotationRoll = importSettings.getRoll();
        settings.itemScale = importSettings.getSize();
        settings.itemSwingSpeed = importSettings.getSpeed();
        settings.itemSwingSpeedHaste = importSettings.getSpeed();
        settings.itemSwingSpeedFatigue = importSettings.getSpeed();
        AnimatiumSettings.ignoreHaste = importSettings.getIgnoreHaste();
        settings.swingSetting = importSettings.getScaleSwing() ? 1 : 0;
        ItemPositionAdvancedSettings.shouldScaleEat = importSettings.getDrinkingFix() == 2;

        settings.save();
    }

    public static void transferDulkirConfig() {
        try {
            File dulkirConfigFile = ConfigUtils.getProfileFile("dulkirmod-config.json");
            String dulkirConfig = FileUtils.readFileToString(dulkirConfigFile, Charsets.UTF_8);
            FileUtils.writeStringToFile(new File(dulkirConfigFile.getParentFile(), "dulkirmod-config_backup.json"), dulkirConfig, Charsets.UTF_8);
            File animatiumConfigFile = ConfigUtils.getProfileFile("animatium.json");
            String animatiumConfig = FileUtils.readFileToString(animatiumConfigFile, Charsets.UTF_8);
            FileUtils.writeStringToFile(new File(animatiumConfigFile.getParentFile(), "overflowanimations_backup.json"), animatiumConfig, Charsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            Animatium.sendNotification("Failed to backup configs! Click here if you want to continue despite this error.", AnimationExportUtils::importDulkirConfig);
            return;
        }
        AnimationExportUtils.importDulkirConfig();
    }

    private static void importDulkirConfig() {
        DulkirConfigData data;
        if (!Animatium.oldDulkirMod) {
            data = new DulkirConfigData(
                    DulkirConfig.INSTANCE.getCustomSize(),
                    DulkirConfig.INSTANCE.getDoesScaleSwing(),
                    DulkirConfig.INSTANCE.getCustomX(),
                    DulkirConfig.INSTANCE.getCustomY(),
                    DulkirConfig.INSTANCE.getCustomZ(),
                    DulkirConfig.INSTANCE.getCustomYaw(),
                    DulkirConfig.INSTANCE.getCustomPitch(),
                    DulkirConfig.INSTANCE.getCustomRoll(),
                    DulkirConfig.INSTANCE.getCustomSpeed(),
                    DulkirConfig.INSTANCE.getIgnoreHaste(),
                    DulkirConfig.INSTANCE.getDrinkingSelector()
            );
        } else {
            data = new DulkirConfigData(
                    Config.INSTANCE.getCustomSize(),
                    Config.INSTANCE.getDoesScaleSwing(),
                    Config.INSTANCE.getCustomX(),
                    Config.INSTANCE.getCustomY(),
                    Config.INSTANCE.getCustomZ(),
                    Config.INSTANCE.getCustomYaw(),
                    Config.INSTANCE.getCustomPitch(),
                    Config.INSTANCE.getCustomRoll(),
                    Config.INSTANCE.getCustomSpeed(),
                    Config.INSTANCE.getIgnoreHaste(),
                    Config.INSTANCE.getDrinkingSelector()
            );
        }

        AnimationExportUtils.importDulkir(data);
        DulkirConfig.INSTANCE.setCustomAnimations(false);
        Animatium.sendNotification("Successfully imported DulkirMod config!");
    }
}
