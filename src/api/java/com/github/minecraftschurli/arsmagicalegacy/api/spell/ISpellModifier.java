package com.github.minecraftschurli.arsmagicalegacy.api.spell;

/**
 * Base interface for a spell modifier. (Basically just a marker)
 */
public interface ISpellModifier extends ISpellPart {
    @Override
    default SpellPartType getType() {
        return SpellPartType.MODIFIER;
    }
}
