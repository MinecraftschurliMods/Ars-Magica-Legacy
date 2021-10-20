package com.github.minecraftschurli.arsmagicalegacy.api.spell;

public interface ISpellModifier extends ISpellPart {

    @Override
    default SpellPartType getType() {
        return SpellPartType.MODIFIER;
    }
}
