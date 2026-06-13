package org.polyfrost.animatium_legacy.hooks;

import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.config.ItemPositionAdvancedSettings;

public final class AnimatiumConfigData {
    public float itemPositionX;

    public float itemPositionY;

    public float itemPositionZ;

    public float itemRotationYaw;

    public float itemRotationPitch;

    public float itemRotationRoll;

    public float itemScale;

    // Swing Position Customization
    public float itemSwingPositionX;

    public float itemSwingPositionY;

    public float itemSwingPositionZ;

    public float itemSwingSpeed;

    public float itemSwingSpeedHaste;

    public float itemSwingSpeedFatigue;

    public boolean shouldScaleSwing = false;

    // Eating/Drinking Position
    public float consumePositionX;

    public float consumePositionY;

    public float consumePositionZ;

    public float consumeRotationYaw;

    public float consumeRotationPitch;

    public float consumeRotationRoll;

    public float consumeScale;

    public float consumeIntensity;

    public float consumeSpeed;

    public boolean shouldScaleEat = false;

    // Sword Block Position
    public float blockedPositionX;

    public float blockedPositionY;

    public float blockedPositionZ;

    public float blockedRotationYaw;

    public float blockedRotationPitch;

    public float blockedRotationRoll;

    public float blockedScale;

    // Projectiles Position
    public float projectilePositionX;

    public float projectilePositionY;

    public float projectilePositionZ;

    public float projectileRotationYaw;

    public float projectileRotationPitch;

    public float projectileRotationRoll;

    public float projectileScale;

    public AnimatiumConfigData() {
        final AnimatiumSettings settings = AnimatiumSettings.INSTANCE;
        final ItemPositionAdvancedSettings advanced = AnimatiumSettings.advancedSettings;
        this.itemPositionX = settings.itemPositionX;
        this.itemPositionY = settings.itemPositionY;
        this.itemPositionZ = settings.itemPositionZ;
        this.itemRotationYaw = settings.itemRotationYaw;
        this.itemRotationPitch = settings.itemRotationPitch;
        this.itemRotationRoll = settings.itemRotationRoll;
        this.itemScale = settings.itemScale;
        this.itemSwingPositionX = advanced.itemSwingPositionX;
        this.itemSwingPositionY = advanced.itemSwingPositionY;
        this.itemSwingPositionZ = advanced.itemSwingPositionZ;
        this.itemSwingSpeed = settings.itemSwingSpeed;
        this.itemSwingSpeedHaste = settings.itemSwingSpeedHaste;
        this.itemSwingSpeedFatigue = settings.itemSwingSpeedFatigue;
        this.shouldScaleSwing = settings.swingSetting == 1;
        this.consumePositionX = advanced.consumePositionX;
        this.consumePositionY = advanced.consumePositionY;
        this.consumePositionZ = advanced.consumePositionZ;
        this.consumeRotationYaw = advanced.consumeRotationYaw;
        this.consumeRotationPitch = advanced.consumeRotationPitch;
        this.consumeRotationRoll = advanced.consumeRotationRoll;
        this.consumeScale = advanced.consumeScale;
        this.consumeIntensity = advanced.consumeIntensity;
        this.consumeSpeed = advanced.consumeSpeed;
        this.shouldScaleEat = ItemPositionAdvancedSettings.shouldScaleEat;
        this.blockedPositionX = advanced.blockedPositionX;
        this.blockedPositionY = advanced.blockedPositionY;
        this.blockedPositionZ = advanced.blockedPositionZ;
        this.blockedRotationYaw = advanced.blockedRotationYaw;
        this.blockedRotationPitch = advanced.blockedRotationPitch;
        this.blockedRotationRoll = advanced.blockedRotationRoll;
        this.blockedScale = advanced.blockedScale;
        this.projectilePositionX = advanced.projectilePositionX;
        this.projectilePositionY = advanced.projectilePositionY;
        this.projectilePositionZ = advanced.projectilePositionZ;
        this.projectileRotationYaw = advanced.projectileRotationYaw;
        this.projectileRotationPitch = advanced.projectileRotationPitch;
        this.projectileRotationRoll = advanced.projectileRotationRoll;
        this.projectileScale = advanced.projectileScale;
    }
}