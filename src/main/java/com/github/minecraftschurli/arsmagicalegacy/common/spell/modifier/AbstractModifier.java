package com.github.minecraftschurli.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

public abstract class AbstractModifier extends ForgeRegistryEntry<ISpellPart> implements ISpellModifier {
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IForgeRegistryEntry<?> other)) return false;
        return Objects.equals(this.getRegistryName(), other.getRegistryName());
    }
}
