package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import java.util.Map;
import java.util.Set;

/// Represents a spell modifier. Spell modifiers cannot be cast, instead they are queried for their presence by other spell parts.
public non-sealed class SpellModifier extends SpellPart {
    protected final Map<SpellStat, SpellStatModifier> modifiers;

    /// @param modifiers A map of [SpellStat]s and [SpellStatModifier]s that this modifier represents.
    public SpellModifier(Map<SpellStat, SpellStatModifier> modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return true;
    }

    @Override
    public Set<SpellStat> getStats() {
        return modifiers.keySet();
    }

    /// @param stat The [SpellStat] to test for.
    /// @return The associated [SpellStatModifier].
    public SpellStatModifier getModifier(SpellStat stat) {
        return modifiers.get(stat);
    }
}
