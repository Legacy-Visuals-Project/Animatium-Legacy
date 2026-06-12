package org.polyfrost.animatium_legacy.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiFlatPresets;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.polyfrost.animatium_legacy.Animatium;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;
import org.polyfrost.animatium_legacy.hooks.DroppedItemHook;
import org.polyfrost.animatium_legacy.hooks.GlintModelHook;
import org.polyfrost.animatium_legacy.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin {
    //    @Shadow
//    public abstract void renderItem(ItemStack stack, IBakedModel model);
    @Shadow
    @Final
    private static ResourceLocation RES_ITEM_GLINT;
    //    @Unique private ItemStack animatium$stackGui = null;
//    @Unique private ItemStack animatium$stackHeld = null;

    @Unique
    private ItemStack animatium$stack = null;

    @Unique
    public IBakedModel animatium$model;

    @Unique
    private EntityLivingBase animatium$entityLivingBase;

    @Inject(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    private void animatium$setModel(final IBakedModel model, final int color, final ItemStack stack, final CallbackInfo ci) {
        this.animatium$model = model;
    }

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    private void animatium$getLastEntity(final ItemStack stack, final EntityLivingBase entityToRenderFor, final ItemCameraTransforms.TransformType cameraTransformType, final CallbackInfo ci) {
        this.animatium$entityLivingBase = entityToRenderFor;
    }

    @ModifyArg(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderQuads(Lnet/minecraft/client/renderer/WorldRenderer;Ljava/util/List;ILnet/minecraft/item/ItemStack;)V", ordinal = 1), index = 1)
    private List<BakedQuad> animatium$changeToSprite(final List<BakedQuad> quads) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.itemSprites && !this.animatium$model.isGui3d() && TransformTypeHook.shouldBeSprite() && !DroppedItemHook.isItemPhysicsAndEntityDropped()) {
            return quads.stream().filter(baked -> baked.getFace() == EnumFacing.SOUTH).collect(Collectors.toList());
        } else {
            return quads;
        }
    }

    // TODO: Double check accuracy as didn't <=1.7 and below not have diffuse lighting or whatever
    @ModifyArgs(method = "putQuadNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;putNormal(FFF)V"))
    private void animatium$modifyNormalValue(final Args args) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.itemSprites && AnimatiumSettings.itemSpritesColor && !this.animatium$model.isGui3d() && !(Minecraft.getMinecraft().currentScreen instanceof GuiFlatPresets) && TransformTypeHook.shouldNotHaveGlint() && !DroppedItemHook.isItemPhysicsAndEntityDropped()) {
            args.setAll(args.get(0), args.get(2), args.get(1));
        }
    }

    // TODO: WrapMethod
    @Inject(method = "renderEffect", at = @At(value = "HEAD"), cancellable = true)
    private void animatium$disableGlint(final IBakedModel model, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled) {
            if (AnimatiumSettings.potionGlint && this.animatium$stack.getItem() instanceof ItemPotion) {
                ci.cancel();
            }

            if (AnimatiumSettings.itemSprites && AnimatiumSettings.spritesGlint && TransformTypeHook.shouldNotHaveGlint() && !DroppedItemHook.isItemPhysicsAndEntityDropped()) {
                ci.cancel();
            }

            if (AnimatiumSettings.enchantmentGlintGui && TransformTypeHook.isRenderingInGUI() && !Animatium.isNEUPresent) {
//                if (OldAnimationsSettings.oldPotionsGui && animatium$stackGui.getItem() instanceof ItemPotion) { return; }
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void animatium$translateSprite(final ItemStack stack, final IBakedModel model, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.itemSprites && !model.isGui3d() && TransformTypeHook.shouldNotHaveGlint()) {
            GlStateManager.translate(0.0F, 0.0F, -0.0625F);
        }
    }

    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    private void animatium$modifyModelPosition(final ItemStack stack, final IBakedModel model, final ItemCameraTransforms.TransformType cameraTransformType, final CallbackInfo ci) {
        if (AnimatiumSettings.INSTANCE.enabled && stack != null && stack.getItem() != null && !(stack.getItem() instanceof ItemBanner)) {
            final boolean isRod = stack.getItem().shouldRotateAroundWhenRendering();
            final boolean isBlock = stack.getItem() instanceof ItemBlock;

            boolean isCarpet = false;
            if (isBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();
                isCarpet = block instanceof BlockCarpet || block instanceof BlockSnow;
            }

            if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON && !AnimatiumSettings.lunarPositions) {
                if (AnimatiumSettings.firstPersonFishingRodPosition && isRod) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(50.0F, 0.0F, 0.0F, 1.0F);
                } else if (AnimatiumSettings.firstPersonItemPositions && AnimatiumSettings.firstPersonCarpetPosition && isCarpet) {
                    GlStateManager.translate(0.0F, -5.25F * 0.0625F, 0.0F);
                }
            } else if (AnimatiumSettings.thirdPersonItemPositions && cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON && (this.animatium$entityLivingBase instanceof EntityPlayer || !AnimatiumSettings.disableEntityItemTransforms)) {
                if (AnimatiumSettings.thirdPersonFishingRodPosition && isRod) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(110.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translate(0.002F, 0.037F, -0.003F);
                } else if (AnimatiumSettings.thirdPersonCarpetPosition && isCarpet) {
                    GlStateManager.translate(0.0F, -0.25F, 0.0F);
                }

                if (isBlock) {
                    if (Block.getBlockFromItem(stack.getItem()).getRenderType() != 2) {
                        GlStateManager.translate(-0.0285F, -0.0375F, 0.0285F);
                        GlStateManager.rotate(-5.0f, 1.0f, 0.0f, 0.0f);
                        GlStateManager.rotate(-5.0f, 0.0f, 0.0f, 1.0f);
                    }

                    GlStateManager.scale(-1.0F, 1.0F, -1.0F);
                }
            } else if (cameraTransformType == ItemCameraTransforms.TransformType.GUI) {
                // TODO: add gui item rotations
            }
        }
    }

    @Redirect(method = "renderItemIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelMesher;getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;"))
    private IBakedModel animatium$rodBowModelTexture(final ItemModelMesher instance, final ItemStack stack) {
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.rodBowGuiFix && player != null) {
            final Item item = stack.getItem();
            if (stack == player.getHeldItem()) {
                ModelResourceLocation location = null;

                final int itemInUseTicks = player.getItemInUseCount();
                if (item instanceof ItemBow && player.getItemInUse() != null) {
                    final int ticks = stack.getMaxItemUseDuration() - itemInUseTicks;
                    if (ticks >= 18) {
                        location = animatium$inventoryModel("bow_pulling_2");
                    } else if (ticks > 13) {
                        location = animatium$inventoryModel("bow_pulling_1");
                    } else if (ticks > 0) {
                        location = animatium$inventoryModel("bow_pulling_0");
                    }
                } else if (item instanceof ItemFishingRod && player.fishEntity != null) {
                    location = animatium$inventoryModel("fishing_rod_cast");
                } else {
                    location = item.getModel(stack, player, itemInUseTicks);
                }

                if (location != null) {
                    return instance.getModelManager().getModel(location);
                }
            }
        }

        return instance.getItemModel(stack);
    }

    @Unique
    private static ModelResourceLocation animatium$inventoryModel(final String path) {
        return new ModelResourceLocation(path, "inventory");
    }

    @Inject(method = "renderItemIntoGUI", at = @At(value = "TAIL"))
    private void animatium$renderGuiGlint(final ItemStack stack, final int x, final int y, final CallbackInfo ci) {
        if (AnimatiumSettings.potionGlint && stack.getItem() instanceof ItemPotion) return;
//        if (OldAnimationsSettings.oldPotionsGui && stack.getItem() instanceof ItemPotion) return;
        if (AnimatiumSettings.INSTANCE.enabled && !Animatium.isNEUPresent && AnimatiumSettings.enchantmentGlintGui && stack.hasEffect()) {
            GlintModelHook.INSTANCE.renderGlintGui(x, y, RES_ITEM_GLINT);
        }
    }

    @ModifyArg(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderEffect(Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    private IBakedModel animatium$replaceModel(final IBakedModel model) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlint) {
            return GlintModelHook.INSTANCE.getGlint(model);
        } else {
            return model;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 0)
    private float animatium$modifySpeed(final float original) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlint) {
            return original * 64.0F;
        } else {
            return original;
        }
    }

    @ModifyArgs(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void animatium$modifyScale(final Args args) {
        if (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.enchantmentGlint) {
            for (int i : new int[]{0, 1, 2}) {
                args.set(i, 1 / (float) args.get(i));
            }
        }
    }

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "HEAD", ordinal = 0), index = 1, argsOnly = true)
    private ItemStack animatium$captureStack(final ItemStack stack) {
        animatium$stack = stack;
        return stack;
    }

//    @ModifyVariable(
//            method = "renderItemModelTransform",
//            at = @At(
//                    value = "HEAD",
//                    ordinal = 0
//            ),
//            index = 1,
//            argsOnly = true
//    )
//    private ItemStack animatium$captureHeldStack(ItemStack stack) {
//        animatium$stackHeld = stack;
//        return stack;
//    }
//
//    @ModifyArg(
//            method = "renderItemModelTransform",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"
//            ),
//            index = 1
//    )
//    private IBakedModel animatium$swapToCustomModel(IBakedModel model) {
//        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotions) return model;
//        if (animatium$stackHeld.getItem() instanceof ItemPotion) {
//            return CustomModelBakery.BOTTLE_OVERLAY.getBakedModel();
//        }
//        return model;
//    }
//
//    @Inject(
//            method = "renderItemModelTransform",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
//                    shift = At.Shift.AFTER
//            )
//    )
//    private void animatium$renderCustomBottle(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
//        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotions) return;
//
//        if (stack.getItem() instanceof ItemPotion) {
//            renderItem(new ItemStack(Items.glass_bottle), animatium$getBottleModel(stack));
//        }
//    }
//
//    @ModifyVariable(
//            method = "renderItemIntoGUI",
//            at = @At(
//                    value = "HEAD",
//                    ordinal = 0
//            ),
//            index = 1,
//            argsOnly = true
//    )
//    private ItemStack animatium$captureGuiStack(ItemStack stack) {
//        animatium$stackGui = stack;
//        return stack;
//    }
//
//    @ModifyArg(
//            method = "renderItemIntoGUI",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"
//            ),
//            index = 1
//    )
//    private IBakedModel animatium$swapToCustomModel2(IBakedModel model) {
//        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotionsGui) return model;
//        if (animatium$stackGui.getItem() instanceof ItemPotion) {
//            return CustomModelBakery.BOTTLE_OVERLAY.getBakedModel();
//        }
//        return model;
//    }
//
//    @Inject(
//            method = "renderItemIntoGUI",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
//                    shift = At.Shift.AFTER
//            )
//    )
//    private void animatium$renderCustomBottle2(ItemStack stack, int x, int y, CallbackInfo ci) {
//        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotionsGui) return;
//
//        if (stack.getItem() instanceof ItemPotion) {
//            renderItem(new ItemStack(Items.glass_bottle), animatium$getBottleModel(stack));
//        }
//    }
//
//    @Unique
//    private IBakedModel animatium$getBottleModel(ItemStack stack) {
//        return ItemPotion.isSplash(stack.getMetadata()) ? CustomModelBakery.BOTTLE_SPLASH_EMPTY.getBakedModel() : CustomModelBakery.BOTTLE_DRINKABLE_EMPTY.getBakedModel();
//    }
}
