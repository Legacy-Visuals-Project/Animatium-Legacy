package org.polyfrost.animatium_legacy.config;

public enum ArmorTintStyle {
    NONE,
    V1_7,
    V1_8_W_GLINT,
    V1_8,
    V1_8_ORANGE_MARSHALL;

    public static final ArmorTintStyle[] VALUES = values();

    public boolean hasRedOverlay() {
        return this == V1_8_W_GLINT || this == V1_8_ORANGE_MARSHALL;
    }
}
