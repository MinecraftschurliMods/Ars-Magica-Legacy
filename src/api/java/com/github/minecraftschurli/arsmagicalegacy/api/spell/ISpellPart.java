package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * TODO
 */
public interface ISpellPart extends IForgeRegistryEntry<ISpellPart> {
    SpellPartType getType();

    enum SpellPartType {
        COMPONENT, MODIFIER, SHAPE
    }
}
