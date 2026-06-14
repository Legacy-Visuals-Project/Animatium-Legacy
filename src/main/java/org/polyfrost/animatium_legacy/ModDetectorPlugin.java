package org.polyfrost.animatium_legacy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ModDetectorPlugin implements IFMLLoadingPlugin {
    public ModDetectorPlugin() {
        try {
            final File modsFolder = new File(Launch.minecraftHome, "mods");
            final File[] modFolder = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));
            if (modFolder != null) {
                final JsonParser PARSER = new JsonParser();
                File oldFile = null;
                for (final File file : modFolder) {
                    try {
                        try (final ZipFile mod = new ZipFile(file)) {
                            ZipEntry entry = mod.getEntry("mcmod.info");
                            if (entry != null) {
                                try (InputStream inputStream = mod.getInputStream(entry)) {
                                    final byte[] availableBytes = new byte[inputStream.available()];

                                    final int length = inputStream.read(availableBytes, 0, inputStream.available());
                                    final JsonObject modInfo = PARSER.parse(new String(availableBytes, 0, length)).getAsJsonArray().get(0).getAsJsonObject();
                                    if (!modInfo.has("modid") || !modInfo.has("version")) {
                                        continue;
                                    }

                                    final String modid = modInfo.get("modid").getAsString();
                                    if (modid.equals("sk1er_old_animations")) {
                                        oldFile = file;
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (final MalformedJsonException | IllegalStateException ignored) {
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                }

                if (oldFile == null) {
                    return;
                }

                if (!oldFile.delete()) {
                    oldFile.deleteOnExit();
                    this.showErrorDialog(
                            "Sk1er Old Animations detected!",
                            "You have both Sk1er Old Animations and Animatium Legacy installed.\n" +
                                    "Please remove Sk1er Old Animations from your mod folder, as Animatium Legacy replaces it.\n" +
                                    "It is in " + oldFile.getAbsolutePath()
                    );
                } else {
                    this.showErrorDialog(
                            "Sk1er Old Animations detected!",
                            "You had both Sk1er Old Animations and Animatium Legacy installed, so Animatium Legacy removed Sk1er OAM.\n" +
                                    "This is because Animatium Legacy replaces it.\n" +
                                    "Close this and restart your game."
                    );
                }
            }
        } catch (final Exception ignored) {
        }
    }

    private void showErrorDialog(final String title, final String message) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception exception) {
            exception.printStackTrace();
        }

        final JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
        this.attemptExit();
    }

    private void attemptExit() {
        try {
            final Method exit = Class.forName("java.lang.Shutdown").getDeclaredMethod("exit", int.class);
            exit.setAccessible(true);
            exit.invoke(null, 1);
        } catch (final Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(final Map<String, Object> map) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}