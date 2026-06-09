package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.objectweb.asm.Opcodes;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public abstract class ModelBipedMixin {
    @Shadow
    public ModelRenderer bipedRightArm;

    @Shadow
    public ModelRenderer bipedLeftArm;

    @Inject(
            method = "setRotationAngles",
            at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/model/ModelRenderer;rotateAngleY:F", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/model/ModelBiped;heldItemRight:I", ordinal = 0),
                    to = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/model/ModelBiped;heldItemRight:I", ordinal = 2)
            )
    )
    private void animatium$reAssignArmPosition(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.oldArmPosition) {
            bipedRightArm.rotateAngleY = 0.0f;
        }
    }

    @Redirect(method = "setRotationAngles", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/model/ModelRenderer;rotateAngleZ:F", ordinal = 0))
    private void animatium$removeField(final ModelRenderer instance, final float value) {
        if (!AnimatiumSettings.INSTANCE.enabled || !AnimatiumSettings.wackyArms) {
            bipedRightArm.rotateAngleZ = 0.0f;
        }
    }

    @Redirect(method = "setRotationAngles", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/model/ModelRenderer;rotateAngleZ:F", ordinal = 1))
    private void animatium$removeField2(final ModelRenderer instance, final float value) {
        if (!AnimatiumSettings.INSTANCE.enabled || !AnimatiumSettings.wackyArms) {
            bipedLeftArm.rotateAngleZ = 0.0f;
        }
    }

    @Redirect(method = "setRotationAngles", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/model/ModelRenderer;rotateAngleZ:F", ordinal = 2))
    private void animatium$removeField3(final ModelRenderer instance, final float value) {
        if (!AnimatiumSettings.INSTANCE.enabled || !AnimatiumSettings.wackyArms) {
            bipedRightArm.rotateAngleZ = 0.0f;
        }
    }

    @Inject(method = "setRotationAngles", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;cos(F)F", ordinal = 2))
    private void animatium$wackyArms(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.wackyArms) {
            bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount;
            bipedRightArm.rotateAngleZ = (MathHelper.cos(limbSwing * 0.2312F) + 1.0F) * 1.0F * limbSwingAmount;
            bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount;
            bipedLeftArm.rotateAngleZ = (MathHelper.cos(limbSwing * 0.2812F) - 1.0F) * 1.0F * limbSwingAmount;
        }
    }
}
