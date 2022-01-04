package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

/**
 * The etherium types.
 */
public enum EtheriumType implements ITranslatable {
    LIGHT, NEUTRAL, DARK;

    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, name().toLowerCase());
    }

    @Override
    public String getType() {
        return "etherium";
    }
}
