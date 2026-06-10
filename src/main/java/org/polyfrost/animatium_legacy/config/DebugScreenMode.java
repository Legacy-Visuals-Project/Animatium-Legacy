package org.polyfrost.animatium_legacy.config;

public enum DebugScreenMode {
    V1_7,
    V1_8,
    DISABLE_BACKGROUND;

    public static final DebugScreenMode[] VALUES = values();

    public boolean hasBackground() {
        return this == V1_8;
    }
}
