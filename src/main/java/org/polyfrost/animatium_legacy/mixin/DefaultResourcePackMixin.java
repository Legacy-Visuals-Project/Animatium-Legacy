package org.polyfrost.animatium_legacy.mixin;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.resources.DefaultResourcePack;
import org.polyfrost.animatium_legacy.Animatium;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * TODO/NOTE: Needed to load our own resources from the "assets/animatium/" directory
 * Mainly used for 1.7 Item Skulls
 * TODO: Replace with WrapOperation once MixinExtras is available for full compatibility
 */
@Mixin(DefaultResourcePack.class)
public abstract class DefaultResourcePackMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;of(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"))
    private static <E> ImmutableSet<E> animatium$registerOurNamespace(final E value1, final E value2) {
        return ImmutableSet.of(value1, value2, (E) Animatium.MOD_ID);
    }
}
