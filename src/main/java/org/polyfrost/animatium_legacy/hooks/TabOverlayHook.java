package org.polyfrost.animatium_legacy.hooks;

import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import org.polyfrost.animatium_legacy.mixin.accessor.GuiPlayerTabOverlayAccessor;

import java.util.List;

import static net.minecraft.client.gui.Gui.drawRect;

/**
 * This is obviously quite an intrusive overwrite, so we put it in a hook so other mods can inject into it easier.
 */
public final class TabOverlayHook {
    public static void renderOldTab(final GuiPlayerTabOverlay instance, final ScoreObjective objective, final Ordering<NetworkPlayerInfo> infoOrdering) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final FontRenderer fontRenderer = minecraft.fontRendererObj;
        final NetHandlerPlayClient sendQueue = minecraft.thePlayer.sendQueue;
        final List<NetworkPlayerInfo> playerInfos = infoOrdering.sortedCopy(sendQueue.getPlayerInfoMap());
        int currentServerMaxPlayers = minecraft.thePlayer.sendQueue.currentServerMaxPlayers;
        int var16 = currentServerMaxPlayers;

        final ScaledResolution scaledresolution = new ScaledResolution(minecraft);
        int var17;
        int scaledWidth = scaledresolution.getScaledWidth();
        int var21;
        int var22;
        int var23;
        for (var17 = 1; var16 > 20; var16 = (currentServerMaxPlayers + var17 - 1) / var17) {
            ++var17;
        }

        final int var46 = Math.min(300 / var17, 150);
        int var19 = (scaledWidth - var17 * var46) / 2;
        byte var47 = 10;
        drawRect(var19 - 1, var47 - 1, var19 + var46 * var17, var47 + 9 * var16, Integer.MIN_VALUE);
        GuiPlayerTabOverlayAccessor accessor = (GuiPlayerTabOverlayAccessor) instance;
        for (var21 = 0; var21 < currentServerMaxPlayers; ++var21) {
            var22 = var19 + var21 % var17 * var46;
            var23 = var47 + var21 / var17 * 9;
            drawRect(var22, var23, var22 + var46 - 1, var23 + 8, 553648127);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            if (var21 < playerInfos.size()) {
                NetworkPlayerInfo var48 = playerInfos.get(var21);
                ScorePlayerTeam var49 = minecraft.theWorld.getScoreboard().getPlayersTeam(var48.getGameProfile().getName());
                String var50 = ScorePlayerTeam.formatPlayerName(var49, var48.getGameProfile().getName());
                fontRenderer.drawStringWithShadow(var50, var22, var23, 16777215);
                if (objective != null) {
                    int var27 = var22 + fontRenderer.getStringWidth(var50) + 5;
                    int var28 = var22 + var46 - 12 - 5;
                    if (var28 - var27 > 5) {
                        Score var29 = objective.getScoreboard().getValueFromObjective(var48.getGameProfile().getName(), objective);
                        String var30 = EnumChatFormatting.YELLOW + String.valueOf(var29.getScorePoints());
                        fontRenderer.drawStringWithShadow(var30, var28 - fontRenderer.getStringWidth(var30), var23, 16777215);
                    }
                }

                accessor.animatium$drawPing(50, var22 + var46 - 52, var23, var48);
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }
}
