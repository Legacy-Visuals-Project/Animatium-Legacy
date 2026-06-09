package org.polyfrost.animatium_legacy.hooks;

import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;

public final class AnimatiumConfigData {
    public float itemPositionX = 0.0F;

    public float itemPositionY = 0.0F;

    public float itemPositionZ = 0.0F;

    public float itemRotationYaw = 0.0F;

    public float itemRotationPitch = 0.0F;

    public float itemRotationRoll = 0.0F;

    public float itemScale = 0.0F;

    // Swing Position Customization
    public float itemSwingPositionX = 0.0F;

    public float itemSwingPositionY = 0.0F;

    public float itemSwingPositionZ = 0.0F;

    public float itemSwingSpeed = 0.0F;

    public float itemSwingSpeedHaste = 0.0F;

    public float itemSwingSpeedFatigue = 0.0F;

    public boolean shouldScaleSwing = false;

    // Eating/Drinking Position
    public float consumePositionX = 0.0F;

    public float consumePositionY = 0.0F;

    public float consumePositionZ = 0.0F;

    public float consumeRotationYaw = 0.0F;

    public float consumeRotationPitch = 0.0F;

    public float consumeRotationRoll = 0.0F;

    public float consumeScale = 0.0F;

    public float consumeIntensity = 0.0F;

    public float consumeSpeed = 0.0F;

    public boolean shouldScaleEat = false;

    // Sword Block Position
    public float blockedPositionX = 0.0F;

    public float blockedPositionY = 0.0F;

    public float blockedPositionZ = 0.0F;

    public float blockedRotationYaw = 0.0F;

    public float blockedRotationPitch = 0.0F;

    public float blockedRotationRoll = 0.0F;

    public float blockedScale = 0.0F;

    // Projectiles Position
    public float projectilePositionX = 0.0F;

    public float projectilePositionY = 0.0F;

    public float projectilePositionZ = 0.0F;

    public float projectileRotationYaw = 0.0F;

    public float projectileRotationPitch = 0.0F;

    public float projectileRotationRoll = 0.0F;

    public float projectileScale = 0.0F;

    public AnimatiumConfigData() {
        AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
        itemPositionX = settings.itemPositionX;
        itemPositionY = settings.itemPositionY;
        itemPositionZ = settings.itemPositionZ;
        itemRotationYaw = settings.itemRotationYaw;
        itemRotationPitch = settings.itemRotationPitch;
        itemRotationRoll = settings.itemRotationRoll;
        itemScale = settings.itemScale;
        itemSwingPositionX = advanced.itemSwingPositionX;
        itemSwingPositionY = advanced.itemSwingPositionY;
        itemSwingPositionZ = advanced.itemSwingPositionZ;
        itemSwingSpeed = settings.itemSwingSpeed;
        itemSwingSpeedHaste = settings.itemSwingSpeedHaste;
        itemSwingSpeedFatigue = settings.itemSwingSpeedFatigue;
        shouldScaleSwing = settings.swingSetting == 1;
        consumePositionX = advanced.consumePositionX;
        consumePositionY = advanced.consumePositionY;
        consumePositionZ = advanced.consumePositionZ;
        consumeRotationYaw = advanced.consumeRotationYaw;
        consumeRotationPitch = advanced.consumeRotationPitch;
        consumeRotationRoll = advanced.consumeRotationRoll;
        consumeScale = advanced.consumeScale;
        consumeIntensity = advanced.consumeIntensity;
        consumeSpeed = advanced.consumeSpeed;
        shouldScaleEat = ItemPositionAdvancedSettings.shouldScaleEat;
        blockedPositionX = advanced.blockedPositionX;
        blockedPositionY = advanced.blockedPositionY;
        blockedPositionZ = advanced.blockedPositionZ;
        blockedRotationYaw = advanced.blockedRotationYaw;
        blockedRotationPitch = advanced.blockedRotationPitch;
        blockedRotationRoll = advanced.blockedRotationRoll;
        blockedScale = advanced.blockedScale;
        projectilePositionX = advanced.projectilePositionX;
        projectilePositionY = advanced.projectilePositionY;
        projectilePositionZ = advanced.projectilePositionZ;
        projectileRotationYaw = advanced.projectileRotationYaw;
        projectileRotationPitch = advanced.projectileRotationPitch;
        projectileRotationRoll = advanced.projectileRotationRoll;
        projectileScale = advanced.projectileScale;
    }
}