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
        FontRenderer var8 = Minecraft.getMinecraft().fontRendererObj;
        NetHandlerPlayClient nethandlerplayclient = Minecraft.getMinecraft().thePlayer.sendQueue;
        List<NetworkPlayerInfo> var42 = infoOrdering.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int var15 = Minecraft.getMinecraft().thePlayer.sendQueue.currentServerMaxPlayers;
        int var16 = var15;
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        int var17;
        int var6 = scaledresolution.getScaledWidth();
        int var21;
        int var22;
        int var23;
        for (var17 = 1; var16 > 20; var16 = (var15 + var17 - 1) / var17) {
            ++var17;
        }
        int var46 = 300 / var17;
        if (var46 > 150) {
            var46 = 150;
        }
        int var19 = (var6 - var17 * var46) / 2;
        byte var47 = 10;
        drawRect(var19 - 1, var47 - 1, var19 + var46 * var17, var47 + 9 * var16, Integer.MIN_VALUE);
        GuiPlayerTabOverlayAccessor accessor = (GuiPlayerTabOverlayAccessor) instance;
        for (var21 = 0; var21 < var15; ++var21) {
            var22 = var19 + var21 % var17 * var46;
            var23 = var47 + var21 / var17 * 9;
            drawRect(var22, var23, var22 + var46 - 1, var23 + 8, 553648127);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            if (var21 < var42.size()) {
                NetworkPlayerInfo var48 = var42.get(var21);
                ScorePlayerTeam var49 = Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(var48.getGameProfile().getName());
                String var50 = ScorePlayerTeam.formatPlayerName(var49, var48.getGameProfile().getName());
                var8.drawStringWithShadow(var50, var22, var23, 16777215);
                if (objective != null) {
                    int var27 = var22 + var8.getStringWidth(var50) + 5;
                    int var28 = var22 + var46 - 12 - 5;
                    if (var28 - var27 > 5) {
                        Score var29 = objective.getScoreboard().getValueFromObjective(var48.getGameProfile().getName(), objective);
                        String var30 = EnumChatFormatting.YELLOW + String.valueOf(var29.getScorePoints());
                        var8.drawStringWithShadow(var30, var28 - var8.getStringWidth(var30), var23, 16777215);
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
