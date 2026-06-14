package org.polyfrost.animatium_legacy.config;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;

@SuppressWarnings("unused")
public class ItemPositionAdvancedSettings {
    // Swing Position Customization
    @Slider(
            name = "Item Swing X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingPositionX = 0.0F;

    @Slider(
            name = "Item Swing Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingPositionY = 0.0F;

    @Slider(
            name = "Item Swing Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingPositionZ = 0.0F;

    @Button(
            name = "Reset Item Swing Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    Runnable resetSwing = (() -> {
        this.itemSwingPositionX = 0.0F;
        this.itemSwingPositionY = 0.0F;
        this.itemSwingPositionZ = 0.0F;
    });

    // Eating/Drinking Position
    @Slider(
            name = "Eating/Drinking X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumePositionX = 0.0F;

    @Slider(
            name = "Eating/Drinking Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumePositionY = 0.0F;

    @Slider(
            name = "Eating/Drinking Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumePositionZ = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeRotationYaw = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeRotationPitch = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeRotationRoll = 0.0F;

    @Slider(
            name = "Eating/Drinking Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeScale = 0.0F;

    @Slider(
            name = "Eating/Drinking Intensity Animation",
            min = -6.5F, max = 6.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeIntensity = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Speed",
            min = -1.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeSpeed = 0.0F;

    @Button(
            name = "Reset Eating/Drinking Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    Runnable resetConsume = (() -> {
        this.consumePositionX = 0.0F;
        this.consumePositionY = 0.0F;
        this.consumePositionZ = 0.0F;
        this.consumeRotationYaw = 0.0F;
        this.consumeRotationPitch = 0.0F;
        this.consumeRotationRoll = 0.0F;
        this.consumeScale = 0.0F;
        this.consumeIntensity = 0.0F;
        this.consumeSpeed = 0.0F;
        shouldScaleEat = false;
    });

    @Checkbox(
            name = "Scale Eating/Drinking Based on Item Position",
            description = "Scales the Eating/Drinking animation based on the position of the item.",
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public static boolean shouldScaleEat = false;

    // Sword Block Position
    @Slider(
            name = "Sword Block X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedPositionX = 0.0F;

    @Slider(
            name = "Sword Block Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedPositionY = 0.0F;

    @Slider(
            name = "Sword Block Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedPositionZ = 0.0F;

    @Slider(
            name = "Sword Block Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedRotationYaw = 0.0F;

    @Slider(
            name = "Sword Block Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedRotationPitch = 0.0F;

    @Slider(
            name = "Sword Block Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedRotationRoll = 0.0F;

    @Slider(
            name = "Sword Block Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedScale = 0.0F;

    @Button(
            name = "Reset Sword Block Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    Runnable resetBlockItem = (() -> {
        this.blockedPositionX = 0.0F;
        this.blockedPositionY = 0.0F;
        this.blockedPositionZ = 0.0F;
        this.blockedRotationYaw = 0.0F;
        this.blockedRotationPitch = 0.0F;
        this.blockedRotationRoll = 0.0F;
        this.blockedScale = 0.0F;
    });

    // Dropped Item Position
    @Slider(
            name = "Dropped Item X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedPositionX = 0.0F;

    @Slider(
            name = "Dropped Item Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedPositionY = 0.0F;

    @Slider(
            name = "Dropped Item Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedPositionZ = 0.0F;

    @Slider(
            name = "Dropped Item Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedRotationYaw = 0.0F;

    @Slider(
            name = "Dropped Item Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedRotationPitch = 0.0F;

    @Slider(
            name = "Dropped Item Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedRotationRoll = 0.0F;

    @Slider(
            name = "Dropped Item Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedScale = 0.0F;

    @Button(
            name = "Reset Dropped Item Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    Runnable resetDropped = (() -> {
        this.droppedPositionX = 0.0F;
        this.droppedPositionY = 0.0F;
        this.droppedPositionZ = 0.0F;
        this.droppedRotationYaw = 0.0F;
        this.droppedRotationPitch = 0.0F;
        this.droppedRotationRoll = 0.0F;
        this.droppedScale = 0.0F;
    });

    // Projectiles Position
    @Slider(
            name = "Thrown Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectilePositionX = 0.0F;

    @Slider(
            name = "Thrown Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectilePositionY = 0.0F;

    @Slider(
            name = "Thrown Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectilePositionZ = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileRotationYaw = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileRotationPitch = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileRotationRoll = 0.0F;

    @Slider(
            name = "Thrown Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileScale = 0.0F;

    @Button(
            name = "Reset Thrown Projectile Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    Runnable resetProjectile = (() -> {
        this.projectilePositionX = 0.0F;
        this.projectilePositionY = 0.0F;
        this.projectilePositionZ = 0.0F;
        this.projectileRotationYaw = 0.0F;
        this.projectileRotationPitch = 0.0F;
        this.projectileRotationRoll = 0.0F;
        this.projectileScale = 0.0F;
    });

    // Fireball Position
    @Slider(
            name = "Fireball Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballPositionX = 0.0F;

    @Slider(
            name = "Fireball Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballPositionY = 0.0F;

    @Slider(
            name = "Fireball Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballPositionZ = 0.0F;

    @Slider(
            name = "Fireball Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballRotationYaw = 0.0F;

    @Slider(
            name = "Fireball Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballRotationPitch = 0.0F;

    @Slider(
            name = "Fireball Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballRotationRoll = 0.0F;

    @Slider(
            name = "Fireball Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballScale = 0.0F;

    @Button(
            name = "Reset Fireball Projectile Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    Runnable resetFireball = (() -> {
        this.fireballPositionX = 0.0F;
        this.fireballPositionY = 0.0F;
        this.fireballPositionZ = 0.0F;
        this.fireballRotationYaw = 0.0F;
        this.fireballRotationPitch = 0.0F;
        this.fireballRotationRoll = 0.0F;
        this.fireballScale = 0.0F;
    });

    // Fishing Line Position

    @Switch(
            name = "Custom Fishing Rod Line Position",
            description = "Allows customization of the fishing rod line.",
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    public static boolean customRodLine = false;

    @Slider(
            name = "Fishing Line X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position",
            instant = true
    )
    public float fishingLinePositionX = -0.36F;

    @Slider(
            name = "Fishing Line Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position",
            instant = true
    )
    public float fishingLinePositionY = 0.03F;

    @Slider(
            name = "Fishing Line Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position",
            instant = true
    )
    public float fishingLinePositionZ = 0.35F;

    @Button(
            name = "Reset Fishing Rod Line Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    Runnable resetFishingLine = (() -> {
        this.fishingLinePositionX = AnimatiumSettings.firstPersonFishingRodPosition ? -0.5F : -0.36F;
        this.fishingLinePositionY = 0.03F;
        this.fishingLinePositionZ = AnimatiumSettings.firstPersonFishingRodPosition ? 0.8F : 0.35F;
    });
}
