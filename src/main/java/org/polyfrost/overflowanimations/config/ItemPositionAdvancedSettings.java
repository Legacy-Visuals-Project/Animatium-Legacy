package org.polyfrost.overflowanimations.config;

import org.polyfrost.oneconfig.api.config.v1.annotations.Button;
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox;
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;

@SuppressWarnings("unused")
public class ItemPositionAdvancedSettings {

    // Swing Position Customization
    @Slider(
            title = "Item Swing X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingPositionX = 0.0F;

    @Slider(
            title = "Item Swing Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingPositionY = 0.0F;

    @Slider(
            title = "Item Swing Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingPositionZ = 0.0F;

    @Button(
            title = "Reset Item Swing Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public void resetSwing() {
        itemSwingPositionX = 0.0F;
        itemSwingPositionY = 0.0F;
        itemSwingPositionZ = 0.0F;
    }

    // Eating/Drinking Position
    @Slider(
            title = "Eating/Drinking X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumePositionX = 0.0F;

    @Slider(
            title = "Eating/Drinking Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumePositionY = 0.0F;

    @Slider(
            title = "Eating/Drinking Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumePositionZ = 0.0F;

    @Slider(
            title = "Eating/Drinking Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeRotationYaw = 0.0F;

    @Slider(
            title = "Eating/Drinking Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeRotationPitch = 0.0F;

    @Slider(
            title = "Eating/Drinking Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeRotationRoll = 0.0F;

    @Slider(
            title = "Eating/Drinking Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeScale = 0.0F;

    @Slider(
            title = "Eating/Drinking Intensity Animation",
            min = -6.5F, max = 6.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeIntensity = 0.0F;

    @Slider(
            title = "Eating/Drinking Rotation Speed",
            min = -1.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeSpeed = 0.0F;

    @Button(
            title = "Reset Eating/Drinking Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public void resetConsume() {
        consumePositionX = 0.0F;
        consumePositionY = 0.0F;
        consumePositionZ = 0.0F;
        consumeRotationYaw = 0.0F;
        consumeRotationPitch = 0.0F;
        consumeRotationRoll = 0.0F;
        consumeScale = 0.0F;
        consumeIntensity = 0.0F;
        consumeSpeed = 0.0F;
        shouldScaleEat = false;
    }

    @Checkbox(
            title = "Scale Eating/Drinking Based on Item Position",
            description = "Scales the Eating/Drinking animation based on the position of the item.",
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public static boolean shouldScaleEat = false;

    // Sword Block Position
    @Slider(
            title = "Sword Block X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedPositionX = 0.0F;

    @Slider(
            title = "Sword Block Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedPositionY = 0.0F;

    @Slider(
            title = "Sword Block Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedPositionZ = 0.0F;

    @Slider(
            title = "Sword Block Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedRotationYaw = 0.0F;

    @Slider(
            title = "Sword Block Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedRotationPitch = 0.0F;

    @Slider(
            title = "Sword Block Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedRotationRoll = 0.0F;

    @Slider(
            title = "Sword Block Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedScale = 0.0F;

    @Button(
            title = "Reset Sword Block Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public void resetBlockItem() {
        blockedPositionX = 0.0F;
        blockedPositionY = 0.0F;
        blockedPositionZ = 0.0F;
        blockedRotationYaw = 0.0F;
        blockedRotationPitch = 0.0F;
        blockedRotationRoll = 0.0F;
        blockedScale = 0.0F;
    }

    // Dropped Item Position
    @Slider(
            title = "Dropped Item X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedPositionX = 0.0F;

    @Slider(
            title = "Dropped Item Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedPositionY = 0.0F;

    @Slider(
            title = "Dropped Item Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedPositionZ = 0.0F;

    @Slider(
            title = "Dropped Item Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedRotationYaw = 0.0F;

    @Slider(
            title = "Dropped Item Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedRotationPitch = 0.0F;

    @Slider(
            title = "Dropped Item Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedRotationRoll = 0.0F;

    @Slider(
            title = "Dropped Item Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public float droppedScale = 0.0F;

    @Button(
            title = "Reset Dropped Item Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    public void resetDropped() {
        droppedPositionX = 0.0F;
        droppedPositionY = 0.0F;
        droppedPositionZ = 0.0F;
        droppedRotationYaw = 0.0F;
        droppedRotationPitch = 0.0F;
        droppedRotationRoll = 0.0F;
        droppedScale = 0.0F;
    }

    // Projectiles Position
    @Slider(
            title = "Thrown Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectilePositionX = 0.0F;

    @Slider(
            title = "Thrown Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectilePositionY = 0.0F;

    @Slider(
            title = "Thrown Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectilePositionZ = 0.0F;

    @Slider(
            title = "Thrown Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileRotationYaw = 0.0F;

    @Slider(
            title = "Thrown Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileRotationPitch = 0.0F;

    @Slider(
            title = "Thrown Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileRotationRoll = 0.0F;

    @Slider(
            title = "Thrown Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileScale = 0.0F;

    @Button(
            title = "Reset Thrown Projectile Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public void resetProjectile() {
        projectilePositionX = 0.0F;
        projectilePositionY = 0.0F;
        projectilePositionZ = 0.0F;
        projectileRotationYaw = 0.0F;
        projectileRotationPitch = 0.0F;
        projectileRotationRoll = 0.0F;
        projectileScale = 0.0F;
    }

    // Fireball Position
    @Slider(
            title = "Fireball Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballPositionX = 0.0F;

    @Slider(
            title = "Fireball Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballPositionY = 0.0F;

    @Slider(
            title = "Fireball Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballPositionZ = 0.0F;

    @Slider(
            title = "Fireball Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballRotationYaw = 0.0F;

    @Slider(
            title = "Fireball Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballRotationPitch = 0.0F;

    @Slider(
            title = "Fireball Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballRotationRoll = 0.0F;

    @Slider(
            title = "Fireball Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public float fireballScale = 0.0F;

    @Button(
            title = "Reset Fireball Projectile Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    public void resetFireball() {
        fireballPositionX = 0.0F;
        fireballPositionY = 0.0F;
        fireballPositionZ = 0.0F;
        fireballRotationYaw = 0.0F;
        fireballRotationPitch = 0.0F;
        fireballRotationRoll = 0.0F;
        fireballScale = 0.0F;
    }

    // Fishing Line Position

    @Switch(
            title = "Custom Fishing Rod Line Position",
            description = "Allows customization of the fishing rod line.",
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    public static boolean customRodLine = false;

    @Slider(
            title = "Fishing Line X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    public float fishingLinePositionX = -0.36F;

    @Slider(
            title = "Fishing Line Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    public float fishingLinePositionY = 0.03f;

    @Slider(
            title = "Fishing Line Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    public float fishingLinePositionZ = 0.35f;

    @Button(
            title = "Reset Fishing Rod Line Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Fishing Rod Line Position"
    )
    public void resetFishingLine() {
        fishingLinePositionX = OldAnimationsSettings.fishingRodPosition ? -0.5f : -0.36f;
        fishingLinePositionY = 0.03f;
        fishingLinePositionZ = OldAnimationsSettings.fishingRodPosition ? 0.8f : 0.35f;
    }
}
