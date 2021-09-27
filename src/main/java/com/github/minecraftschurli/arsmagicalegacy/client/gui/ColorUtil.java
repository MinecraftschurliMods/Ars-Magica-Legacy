package com.github.minecraftschurli.arsmagicalegacy.client.gui;

public final class ColorUtil {
    public static final int UNKNOWN_SKILL_LINE_COLOR_MASK = 0x999999;
    public static final int KNOWS_COLOR = 0x00ff00;
    public static final int BLACK = 0x000000;

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
