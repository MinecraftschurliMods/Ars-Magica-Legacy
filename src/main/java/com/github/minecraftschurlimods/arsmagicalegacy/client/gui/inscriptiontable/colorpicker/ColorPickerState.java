package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.colorpicker;

public class ColorPickerState {
    private static final ColorPickerState INSTANCE = new ColorPickerState();
    private float centerX, centerY, radius, brightness;

    private ColorPickerState() {}

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getRadius() {
        return radius;
    }

    public float getBrightness() {
        return brightness;
    }

    public static ColorPickerState get() {
        return INSTANCE;
    }

    public void setColorWheel(float centerX, float centerY, float radius, float brightness) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.brightness = brightness;
    }
}
