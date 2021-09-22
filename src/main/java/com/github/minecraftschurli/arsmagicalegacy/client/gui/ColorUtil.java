package com.github.minecraftschurli.arsmagicalegacy.client.gui;

public final class ColorUtil {
    private ColorUtil() {}

    public static float getBlue(int color) {
        return (0xFF & color) / 255f;
    }

    public static float getGreen(int color) {
        return (0xFF & (color >> 8)) / 255f;
    }

    public static float getRed(int color) {
        return (0xFF & (color >> 16)) / 255f;
    }
}
