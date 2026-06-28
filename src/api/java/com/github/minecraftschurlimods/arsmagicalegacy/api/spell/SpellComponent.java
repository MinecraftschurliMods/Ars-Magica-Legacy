package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.google.common.collect.Sets;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Set;

/// Represents a spell component. Components are part of the [SpellGrammar].
///
/// Extend this class directly for target-independent effects, e.g. time or weather components. Extend one of the inner subclasses for target-dependent effects instead.
public abstract non-sealed class SpellComponent extends SpellPart {
    private final Set<SpellStat> stats;

    /// @param stats A vararg of [SpellStat]s used by the component.
    public SpellComponent(SpellStat... stats) {
        this.stats = Sets.newHashSet(stats);
        this.stats.add(SpellStat.COLOR);
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
        return true;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    @Override
    public Set<SpellStat> getStats() {
        return stats;
    }

    /// Casts the part.
    ///
    /// @param modifiers The [SpellModifier]s to consider.
    /// @param context   The [SpellCastContext] to use.
    /// @return A [SpellComponentCastResult] representing the result of the cast.
    public abstract SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context);

    /// Spawns particles for the part. May only be called on the client.
    ///
    /// @param modifiers The [SpellModifier]s to consider.
    /// @param context   The [SpellCastContext] to use.
    @SuppressWarnings("DataFlowIssue")
    public void spawnParticles(List<SpellModifier> modifiers, SpellCastContext context) {
        if (!context.isHitResultNullOrMiss()) {
            ArsMagicaApi.spellHelper().spawnParticles(AMRegistries.SPELL_PARTS.wrapAsHolder(this).getKey().identifier().withPrefix("component/"), modifiers, context);
        }
    }

    /// Represents a spell component that only affects blocks.
    public static abstract class CastBlock extends SpellComponent {
        /// @param stats A vararg of [SpellStat]s used by the component.
        public CastBlock(SpellStat... stats) {
            super(stats);
        }

        @Override
        public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
            return context.hitResult() instanceof BlockHitResult blockHitResult ? castBlock(modifiers, context, blockHitResult) : SpellComponentCastResult.pass(context.spell());
        }

        @Override
        public void spawnParticles(List<SpellModifier> modifiers, SpellCastContext context) {
            if (context.hitResult() instanceof BlockHitResult) {
                super.spawnParticles(modifiers, context);
            }
        }

        /// Casts this part on a block.
        ///
        /// @param modifiers The [SpellModifier]s to consider.
        /// @param context   The [SpellCastContext] to use.
        /// @param hitResult The [BlockHitResult] of the spell cast. This is provided for convenience and will be the cast version of [SpellCastContext#hitResult()].
        /// @return A [SpellComponentCastResult] representing the result of the cast.
        public abstract SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult);
    }

    /// Represents a spell component that only affects entities.
    public static abstract class CastEntity extends SpellComponent {
        /// @param stats A vararg of [SpellStat]s used by the component.
        public CastEntity(SpellStat... stats) {
            super(stats);
        }

        @Override
        public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
            return context.hitResult() instanceof EntityHitResult entityHitResult ? castEntity(modifiers, context, entityHitResult) : SpellComponentCastResult.pass(context.spell());
        }

        @Override
        public void spawnParticles(List<SpellModifier> modifiers, SpellCastContext context) {
            if (context.hitResult() instanceof EntityHitResult) {
                super.spawnParticles(modifiers, context);
            }
        }

        /// Casts this part on an entity.
        ///
        /// @param modifiers The [SpellModifier]s to consider.
        /// @param context   The [SpellCastContext] to use.
        /// @param hitResult The [EntityHitResult] of the spell cast. This is provided for convenience and will be the cast version of [SpellCastContext#hitResult()].
        /// @return A [SpellComponentCastResult] representing the result of the cast.
        public abstract SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult);
    }

    /// Represents a spell component that affects both blocks and entities, with distinct effects on each.
    public static abstract class CastBoth extends SpellComponent {
        /// @param stats A vararg of [SpellStat]s used by the component.
        public CastBoth(SpellStat... stats) {
            super(stats);
        }

        @Override
        public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
            return switch (context.hitResult()) {
                case BlockHitResult blockHitResult -> castBlock(modifiers, context, blockHitResult);
                case EntityHitResult entityHitResult -> castEntity(modifiers, context, entityHitResult);
                case null, default -> SpellComponentCastResult.pass(context.spell());
            };
        }

        /// Casts this part on a block.
        ///
        /// @param modifiers The [SpellModifier]s to consider.
        /// @param context   The [SpellCastContext] to use.
        /// @param hitResult The [BlockHitResult] of the spell cast. This is provided for convenience and will be the cast version of [SpellCastContext#hitResult()].
        /// @return A [SpellComponentCastResult] representing the result of the cast.
        public abstract SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult);

        /// Casts this part on an entity.
        ///
        /// @param modifiers The [SpellModifier]s to consider.
        /// @param context   The [SpellCastContext] to use.
        /// @param hitResult The [EntityHitResult] of the spell cast. This is provided for convenience and will be the cast version of [SpellCastContext#hitResult()].
        /// @return A [SpellComponentCastResult] representing the result of the cast.
        public abstract SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult);
    }
}
