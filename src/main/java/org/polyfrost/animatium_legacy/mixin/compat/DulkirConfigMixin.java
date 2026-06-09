package org.polyfrost.animatium_legacy.mixin.compat;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.data.Mod;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dulkirmod.config.DulkirConfig")
public abstract class DulkirConfigMixin extends Config {
    @Unique
    @Exclude
    private static final String animatium$USE_OURS = "Please use Animatium Legacy' Custom Item Positions instead of Dulkir's, as it is more compatible with old animations and has more features. You can find it in the OneConfig Mods menu. You can use the Dulkir export buttons and import it into Animatium Legacy directly. For more info, please join the Polyfrost Discord server: discord.gg/polyfrost.";

    public DulkirConfigMixin(final Mod modData, final String configFile) {
        super(modData, configFile);
    }

    @Dynamic("DulkirMod")
    @Inject(method = "init", at = @At("RETURN"))
    private void animatium$onInit(final CallbackInfo ci) {
        addDependency("customAnimations", animatium$USE_OURS, () -> false);
        addDependency("customSize", animatium$USE_OURS, () -> false);
        addDependency("doesScaleSwing", animatium$USE_OURS, () -> false);
        addDependency("customX", animatium$USE_OURS, () -> false);
        addDependency("customY", animatium$USE_OURS, () -> false);
        addDependency("customZ", animatium$USE_OURS, () -> false);
        addDependency("customYaw", animatium$USE_OURS, () -> false);
        addDependency("customPitch", animatium$USE_OURS, () -> false);
        addDependency("customRoll", animatium$USE_OURS, () -> false);
        addDependency("customSpeed", animatium$USE_OURS, () -> false);
        addDependency("ignoreHaste", animatium$USE_OURS, () -> false);
        addDependency("drinkingSelector", animatium$USE_OURS, () -> false);
    }
}
