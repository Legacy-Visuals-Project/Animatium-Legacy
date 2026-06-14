package org.polyfrost.animatium_legacy.hooks;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.polyfrost.animatium_legacy.config.AnimatiumSettings;

import java.util.List;

/**
 * This is obviously quite an intrusive overwrite, so we put it in a hook so other mods can inject into it easier.
 */
public final class DebugOverlayHook {
    private static float eyeHeight;

    public static List<String> getDebugInfoLeft() {
        final Minecraft mc = Minecraft.getMinecraft();
        final Entity entity = mc.getRenderViewEntity();
        final World level = entity.worldObj;

        final EnumFacing horizontalFacing = entity.getHorizontalFacing();
        final double x = entity.posX;
        final double y = entity.posY;
        final double z = entity.posZ;
        final float yaw = entity.rotationYaw;

        final double minY = entity.getEntityBoundingBox().minY;
        final BlockPos blockPos = new BlockPos(entity.posX, minY, entity.posZ);
        final Chunk chunk = level.getChunkFromBlockCoords(blockPos);

        int lightSubtracted = 0;
        int blockLight = 0;
        int skyLight = 0;
        if (blockPos.getY() >= 0) {
            lightSubtracted = chunk.getLightSubtracted(blockPos, 0);
            blockLight = chunk.getLightFor(EnumSkyBlock.BLOCK, blockPos);
            skyLight = chunk.getLightFor(EnumSkyBlock.SKY, blockPos);
        }

        final List<String> list = Lists.newArrayList(
                "Minecraft 1.8.9 (" + Minecraft.getDebugFPS() + " fps" + ", " + RenderChunk.renderChunksUpdated + " chunk updates)",
                mc.renderGlobal.getDebugInfoRenders(),
                mc.renderGlobal.getDebugInfoEntities(),
                "P: " + mc.effectRenderer.getStatistics() + ". T: " + level.getDebugLoadedEntities(),
                level.getProviderName(),
                "",
                String.format("x: %.5f (%d) // c: %d (%d)", x, MathHelper.floor_double(x), MathHelper.floor_double(x) >> 4, MathHelper.floor_double(x) & 15),
                String.format("y: %.3f (feet pos, %.3f eyes pos)", minY, y + (AnimatiumSettings.INSTANCE.enabled && AnimatiumSettings.smoothSneaking ? eyeHeight : entity.getEyeHeight())),
                String.format("z: %.5f (%d) // c: %d (%d)", z, MathHelper.floor_double(z), MathHelper.floor_double(z) >> 4, MathHelper.floor_double(z) & 15),
                "f: " + (MathHelper.floor_double((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3) + " (" + horizontalFacing.toString().toUpperCase() + ") / " + MathHelper.wrapAngleTo180_float(yaw),
                String.format("ws: %.3f, fs: %.3f, g: %b, fl: %.0f", mc.thePlayer.capabilities.getWalkSpeed(), mc.thePlayer.capabilities.getFlySpeed(), entity.onGround, y),
                String.format("lc: " + lightSubtracted + " b: " + chunk.getBiome(blockPos, level.getWorldChunkManager()).biomeName) + " bl: " + blockLight + " sl: " + skyLight + " rl: " + lightSubtracted
        );
        if (mc.entityRenderer != null && mc.entityRenderer.isShaderActive()) {
            list.add("shader: " + mc.entityRenderer.getShaderGroup().getShaderGroupName());
        }

        return list;
    }

    public static List<String> getDebugInfoRight() {
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();
        final long remainingMemory = totalMemory - freeMemory;
        final List<String> list = Lists.newArrayList(
                String.format("Used memory: % 2d%% (%03dMB) of %03dMB", remainingMemory * 100L / maxMemory, bytesToMb(remainingMemory), bytesToMb(maxMemory)),
                String.format("Allocated memory: % 2d%% (%03dMB)", totalMemory * 100L / maxMemory, bytesToMb(totalMemory)),
                ""
        );
        list.addAll(FMLCommonHandler.instance().getBrandings(false));
        return list;
    }

    public static void setEyeHeight(final float eyeHeight) {
        DebugOverlayHook.eyeHeight = eyeHeight;
    }

    private static long bytesToMb(final long bytes) {
        return bytes / 1024L / 1024L;
    }
}
