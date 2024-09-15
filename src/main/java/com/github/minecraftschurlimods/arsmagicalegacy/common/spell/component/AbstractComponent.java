package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public abstract class AbstractComponent implements ISpellComponent {
    private final Set<ISpellPartStat> stats;

    protected AbstractComponent(ISpellPartStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public Set<ISpellPartStat> getStatsUsed() {
        return stats;
    }

    /**
     * Helper class that no-ops the overload of invoke used with entities. Use this as the superclass if the component only affects blocks.
     */
    public static abstract class OnBlock extends AbstractComponent {
        protected OnBlock(ISpellPartStat... stats) {
            super(stats);
        }

        @Override
        public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
            return SpellCastResult.EFFECT_FAILED;
        }
    }

    /**
     * Helper class that no-ops the overload of invoke used with blocks. Use this as the superclass if the component only affects entities.
     */
    public static abstract class OnEntity extends AbstractComponent {
        protected OnEntity(ISpellPartStat... stats) {
            super(stats);
        }

        @Override
        public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
            return SpellCastResult.EFFECT_FAILED;
        }
    }

    /**
     * Helper class that forwards both overloads of invoke to a new overload taking a generic HitResult. Use this as the superclass if the component affects neither blocks nor entities directly (for example weather components).
     */
    public static abstract class OnTarget extends AbstractComponent {
        protected OnTarget(ISpellPartStat... stats) {
            super(stats);
        }

        @Override
        public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
            return invoke(spell, caster, directEntity, level, modifiers, (HitResult) target, index, ticksUsed);
        }

        @Override
        public SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
            return invoke(spell, caster, directEntity, level, modifiers, (HitResult) target, index, ticksUsed);
        }

        public abstract SpellCastResult invoke(ISpell spell, LivingEntity caster, @Nullable Entity directEntity, Level level, List<ISpellModifier> modifiers, HitResult target, int index, int ticksUsed);
    }
}
