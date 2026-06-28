package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import java.util.List;
import java.util.Set;

/// Represents a secondary spell shape. Secondary shapes must be in a [SpellShapeGroup], with a primary shape before it.
public abstract non-sealed class SecondarySpellShape extends SpellPart {
    private final Set<SpellStat> stats;

    /// @param stats A vararg of [SpellStat]s used by the shape.
    public SecondarySpellShape(SpellStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return true;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    @Override
    public Set<SpellStat> getStats() {
        return stats;
    }

    /// Casts this part.
    ///
    /// @param modifiers The [SpellModifier]s to consider.
    /// @param context   The [SpellCastContext]s to use.
    /// @return A [SpellCastResult] representing the result of the cast.
    /// @see SpellHelper#castSecondary(SpellCastContext)
    public abstract SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context);
}
