package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * TODO
 */
public interface ISpellPart extends IForgeRegistryEntry<ISpellPart> {
    interface ISpellComponent extends ISpellPart {}
    interface ISpellModifier extends ISpellPart {}
    interface ISpellShape extends ISpellPart {
        boolean isContinuous();
    }
}
