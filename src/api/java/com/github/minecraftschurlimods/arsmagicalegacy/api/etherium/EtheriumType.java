package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enum for etherium type.
 */
public enum EtheriumType implements ITranslatable {
    LIGHT(0xff7fa7ef), NEUTRAL(0xff3fffbf), DARK(0xff800000);
    public static final Set<EtheriumType> ANY = EnumSet.allOf(EtheriumType.class);

    private final int color;

    EtheriumType(int color) {
        this.color = color;
    }

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, name().toLowerCase());
    }

    @Override
    public String getType() {
        return "etherium";
    }

    /**
     * @return This etherium type's color.
     */
    public int getColor() {
        return color;
    }
}
