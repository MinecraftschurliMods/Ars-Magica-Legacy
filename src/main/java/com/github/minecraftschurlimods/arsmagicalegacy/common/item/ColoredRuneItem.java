package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.google.common.collect.Maps;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.Map;

import net.minecraft.world.item.Item.Properties;

public class ColoredRuneItem extends Item {
    private static final Map<DyeColor, ColoredRuneItem> ITEM_BY_COLOR = Maps.newEnumMap(DyeColor.class);
    private final DyeColor dyeColor;

    public ColoredRuneItem(Properties pProperties, DyeColor dyeColor) {
        super(pProperties);
        this.dyeColor = dyeColor;
        ITEM_BY_COLOR.put(dyeColor, this);
    }

    /**
     * @param pColor The color to get the rune item for.
     * @return The rune item of the given color.
     */
    public static ColoredRuneItem byColor(DyeColor pColor) {
        return ITEM_BY_COLOR.get(pColor);
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
}
