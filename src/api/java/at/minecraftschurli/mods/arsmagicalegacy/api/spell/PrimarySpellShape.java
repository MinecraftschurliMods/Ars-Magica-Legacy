package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import java.util.List;
import java.util.Set;

/// Represents a primary spell shape. Primary shapes must be at the start of a [SpellShapeGroup].
public abstract non-sealed class PrimarySpellShape extends SpellPart {
    private final Set<SpellStat> stats;

    /// @param stats A vararg of [SpellStat]s used by the shape.
    public PrimarySpellShape(SpellStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public final boolean isPrimaryShape() {
        return true;
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
        return false;
    }

    @Override
    public Set<SpellStat> getStats() {
        return stats;
    }

    /// @return Whether this part is continuous, i.e., can be cast by holding down the spell.
    public boolean isContinuous() {
        return false;
    }

    /// Casts this part. Note that [SpellCastContext#directEntity()] and [SpellCastContext#hitResult()] are guaranteed to return null here.
    ///
    /// @param modifiers The [SpellModifier]s to consider.
    /// @param context   The [SpellCastContext] to use.
    /// @return A [SpellCastResult] representing the result of the cast.
    /// @see SpellHelper#castPrimary(SpellCastContext)
    public abstract SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context);
}
