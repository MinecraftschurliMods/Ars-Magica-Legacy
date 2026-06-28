package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;

import java.util.function.IntSupplier;

public final class LayerAnchor {
    private LayerAnchor() {}

    public enum X {
        LEFT, CENTER, RIGHT;

        public int getLocation(IntSupplier supplier) {
            return switch (this) {
                case LEFT -> supplier.getAsInt();
                case CENTER -> AMClientUtil.mc().getWindow().getGuiScaledWidth() / 2 + supplier.getAsInt();
                case RIGHT -> AMClientUtil.mc().getWindow().getGuiScaledWidth() + supplier.getAsInt();
            };
        }
    }

    public enum Y {
        TOP, MIDDLE, BOTTOM;

        public int getLocation(IntSupplier supplier) {
            return switch (this) {
                case TOP -> supplier.getAsInt();
                case MIDDLE -> AMClientUtil.mc().getWindow().getGuiScaledHeight() / 2 + supplier.getAsInt();
                case BOTTOM -> AMClientUtil.mc().getWindow().getGuiScaledHeight() + supplier.getAsInt();
            };
        }
    }
}
