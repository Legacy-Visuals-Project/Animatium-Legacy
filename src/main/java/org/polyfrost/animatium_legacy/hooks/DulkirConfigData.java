package org.polyfrost.animatium_legacy.hooks;

public final class DulkirConfigData {
    private final float size;
    private final boolean scaleSwing;
    private final float x;
    private final float y;
    private final float z;
    private final float yaw;
    private final float pitch;
    private final float roll;
    private final float speed;
    private final boolean ignoreHaste;
    private final int drinkingFix;

    public DulkirConfigData(float size, boolean scaleSwing, float x, float y, float z, float yaw, float pitch, float roll, float speed, boolean ignoreHaste, int drinkingFix) {
        this.size = size;
        this.scaleSwing = scaleSwing;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.speed = speed;
        this.ignoreHaste = ignoreHaste;
        this.drinkingFix = drinkingFix;
    }

    public float getSize() {
        return this.size;
    }

    public boolean getScaleSwing() {
        return this.scaleSwing;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getRoll() {
        return this.roll;
    }

    public float getSpeed() {
        return this.speed;
    }

    public boolean getIgnoreHaste() {
        return this.ignoreHaste;
    }

    public int getDrinkingFix() {
        return this.drinkingFix;
    }

    public String toString() {
        return "ConfigData(size=" + this.size + ", scaleSwing=" + this.scaleSwing + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", yaw=" + this.yaw + ", pitch=" + this.pitch + ", roll=" + this.roll + ", speed=" + this.speed + ", ignoreHaste=" + this.ignoreHaste + ", drinkingFix=" + this.drinkingFix + ')';
    }

    public int hashCode() {
        int result = Float.hashCode(this.size);
        int n = this.scaleSwing ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        result = result * 31 + Float.hashCode(this.x);
        result = result * 31 + Float.hashCode(this.y);
        result = result * 31 + Float.hashCode(this.z);
        result = result * 31 + Float.hashCode(this.yaw);
        result = result * 31 + Float.hashCode(this.pitch);
        result = result * 31 + Float.hashCode(this.roll);
        result = result * 31 + Float.hashCode(this.speed);
        int n2 = this.ignoreHaste ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result = result * 31 + n2;
        result = result * 31 + Integer.hashCode(this.drinkingFix);
        return result;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DulkirConfigData)) {
            return false;
        }
        DulkirConfigData configData = (DulkirConfigData) other;
        if (Float.compare(this.size, configData.size) != 0) {
            return false;
        }
        if (this.scaleSwing != configData.scaleSwing) {
            return false;
        }
        if (Float.compare(this.x, configData.x) != 0) {
            return false;
        }
        if (Float.compare(this.y, configData.y) != 0) {
            return false;
        }
        if (Float.compare(this.z, configData.z) != 0) {
            return false;
        }
        if (Float.compare(this.yaw, configData.yaw) != 0) {
            return false;
        }
        if (Float.compare(this.pitch, configData.pitch) != 0) {
            return false;
        }
        if (Float.compare(this.roll, configData.roll) != 0) {
            return false;
        }
        if (Float.compare(this.speed, configData.speed) != 0) {
            return false;
        }
        if (this.ignoreHaste != configData.ignoreHaste) {
            return false;
        }
        return this.drinkingFix == configData.drinkingFix;
    }
}