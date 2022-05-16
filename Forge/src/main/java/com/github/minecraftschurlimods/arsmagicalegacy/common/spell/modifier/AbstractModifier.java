package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

public abstract class AbstractModifier implements ISpellModifier {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IForgeRegistryEntry<?> other)) return false;
        return Objects.equals(getRegistryName(), other.getRegistryName());
    }
}
