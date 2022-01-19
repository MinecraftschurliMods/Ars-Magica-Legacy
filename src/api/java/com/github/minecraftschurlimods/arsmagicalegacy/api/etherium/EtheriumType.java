package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

/**
 * The etherium types.
 */
public enum EtheriumType implements ITranslatable {
    LIGHT(0x79a5ed), NEUTRAL(0x2effab), DARK(0x800000);

    private final int color;

    EtheriumType(final int color) {
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

    public int getColor() {
        return color;
    }
}
