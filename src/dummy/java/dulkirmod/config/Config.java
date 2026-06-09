package dulkirmod.config;

public class Config {
    public static final Config INSTANCE = new Config();

    private static boolean customAnimations;
    private static float customSize;
    private static boolean doesScaleSwing;
    private static float customX;
    private static float customY;
    private static float customZ;
    private static float customYaw;
    private static float customPitch;
    private static float customRoll;
    private static float customSpeed;
    private static boolean ignoreHaste;
    private static int drinkingSelector;

    public final boolean getCustomAnimations() {
        return customAnimations;
    }

    public final void setCustomAnimations(boolean bl) {
        customAnimations = bl;
    }

    public final float getCustomSize() {
        return customSize;
    }

    public final void setCustomSize(float f) {
        customSize = f;
    }

    public final boolean getDoesScaleSwing() {
        return doesScaleSwing;
    }

    public final void setDoesScaleSwing(boolean bl) {
        doesScaleSwing = bl;
    }

    public final float getCustomX() {
        return customX;
    }

    public final void setCustomX(float f) {
        customX = f;
    }

    public final float getCustomY() {
        return customY;
    }

    public final void setCustomY(float f) {
        customY = f;
    }

    public final float getCustomZ() {
        return customZ;
    }

    public final void setCustomZ(float f) {
        customZ = f;
    }

    public final float getCustomYaw() {
        return customYaw;
    }

    public final void setCustomYaw(float f) {
        customYaw = f;
    }

    public final float getCustomPitch() {
        return customPitch;
    }

    public final void setCustomPitch(float f) {
        customPitch = f;
    }

    public final float getCustomRoll() {
        return customRoll;
    }

    public final void setCustomRoll(float f) {
        customRoll = f;
    }

    public final float getCustomSpeed() {
        return customSpeed;
    }

    public final void setCustomSpeed(float f) {
        customSpeed = f;
    }

    public final boolean getIgnoreHaste() {
        return ignoreHaste;
    }

    public final void setIgnoreHaste(boolean bl) {
        ignoreHaste = bl;
    }

    public final int getDrinkingSelector() {
        return drinkingSelector;
    }

    public final void setDrinkingSelector(int n) {
        drinkingSelector = n;
    }

    public final void demoButton() {
    }
}
