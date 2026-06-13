package org.polyfrost.animatium_legacy.hooks

import net.minecraft.potion.Potion

object PotionColors {
    @JvmField
    val MAP = mapOf(
        Potion.moveSpeed.id to 3402751,
        Potion.moveSlowdown.id to 9154528,
        Potion.digSpeed.id to 14270531,
        Potion.digSlowdown.id to 4866583,
        Potion.damageBoost.id to 16762624,
        Potion.heal.id to 16262179,
        Potion.harm.id to 11101546,
        Potion.jump.id to 16646020,
        Potion.confusion.id to 5578058,
        Potion.regeneration.id to 13458603,
        Potion.resistance.id to 9520880,
        Potion.fireResistance.id to 0xFF9900,
        Potion.waterBreathing.id to 10017472,
        Potion.invisibility.id to 0xF6F6F6,
        Potion.blindness.id to 2039587,
        Potion.nightVision.id to 12779366,
        Potion.hunger.id to 5797459,
        Potion.weakness.id to 0x484D48,
        Potion.poison.id to 8889187,
        Potion.wither.id to 7561558,
        Potion.healthBoost.id to 16284963,
        Potion.absorption.id to 0x2552A5,
        Potion.saturation.id to 16262179
    )

    @JvmField
    var shouldReload = false

    @JvmStatic
    fun reloadColor() {
        shouldReload = true
    }
}