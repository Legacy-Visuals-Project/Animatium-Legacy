package dulkirmod.config;

public class Config {
    public static final Config INSTANCE = new Config();

    protected boolean customAnimations;
    protected float customSize;
    protected boolean doesScaleSwing;
    protected float customX;
    protected float customY;
    protected float customZ;
    protected float customYaw;
    protected float customPitch;
    protected float customRoll;
    protected float customSpeed;
    protected boolean ignoreHaste;
    protected int drinkingSelector;

    public boolean getCustomAnimations() {
        return customAnimations;
    }

    public void setCustomAnimations(final boolean bl) {
        customAnimations = bl;
    }

    public float getCustomSize() {
        return customSize;
    }

    public void setCustomSize(final float f) {
        customSize = f;
    }

    public boolean getDoesScaleSwing() {
        return doesScaleSwing;
    }

    public void setDoesScaleSwing(final boolean bl) {
        doesScaleSwing = bl;
    }

    public float getCustomX() {
        return customX;
    }

    public void setCustomX(final float f) {
        customX = f;
    }

    public float getCustomY() {
        return customY;
    }

    public void setCustomY(final float f) {
        customY = f;
    }

    public float getCustomZ() {
        return customZ;
    }

    public void setCustomZ(final float f) {
        customZ = f;
    }

    public float getCustomYaw() {
        return customYaw;
    }

    public void setCustomYaw(final float f) {
        customYaw = f;
    }

    public float getCustomPitch() {
        return customPitch;
    }

    public void setCustomPitch(final float f) {
        customPitch = f;
    }

    public float getCustomRoll() {
        return customRoll;
    }

    public void setCustomRoll(final float f) {
        customRoll = f;
    }

    public float getCustomSpeed() {
        return customSpeed;
    }

    public void setCustomSpeed(final float f) {
        customSpeed = f;
    }

    public boolean getIgnoreHaste() {
        return ignoreHaste;
    }

    public void setIgnoreHaste(final boolean bl) {
        ignoreHaste = bl;
    }

    public int getDrinkingSelector() {
        return drinkingSelector;
    }

    public void setDrinkingSelector(final int n) {
        drinkingSelector = n;
    }

    public void demoButton() {
    }
}
