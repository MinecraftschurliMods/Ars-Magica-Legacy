package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.google.common.collect.Maps;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.Map;

/**
 * Mostly taken from {@link net.minecraft.world.item.DyeItem}
 */
public class ColoredRuneItem extends Item {
    private static final Map<DyeColor, ColoredRuneItem> ITEM_BY_COLOR = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;

    /**
     * Creates a rune with the given color.
     *
     * @param pProperties The item properties.
     * @param dyeColor    The color of the rune.
     */
    public ColoredRuneItem(Properties pProperties, DyeColor dyeColor) {
        super(pProperties);
        this.dyeColor = dyeColor;
        ITEM_BY_COLOR.put(dyeColor, this);
    }

    /**
     * @return The color of this rune.
     */
    public DyeColor getDyeColor() {
        return dyeColor;
    }

    /**
     * Gets a rune by color.
     *
     * @param pColor The color to get the rune for.
     * @return The rune for the given color.
     */
    public static ColoredRuneItem byColor(DyeColor pColor) {
        return ITEM_BY_COLOR.get(pColor);
    }
}
