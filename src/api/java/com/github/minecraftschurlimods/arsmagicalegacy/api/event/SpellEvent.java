package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.util.List;

/**
 * The base class for all spell events.
 */
public abstract class SpellEvent extends LivingEvent {
    private final ISpell spell;

    public SpellEvent(LivingEntity entity, ISpell spell) {
        super(entity);
        this.spell = spell;
    }

    /**
     * @return The spell of the event.
     */
    public ISpell getSpell() {
        return spell;
    }

    /**
     * Event that fires before a spell is cast.
     */
    @Cancelable
    public static final class Cast extends SpellEvent {
        public Cast(LivingEntity entity, ISpell spell) {
            super(entity, spell);
        }
    }

    /**
     * Event that fires when mana cost is retrieved. Has Pre and Post sub events.
     */
    public static abstract sealed class ManaCost extends SpellEvent {
        public ManaCost(LivingEntity entity, ISpell spell) {
            super(entity, spell);
        }

        /**
         * Event to modify the base cost and the modifier for the mana cost.
         */
        public static final class Pre extends ManaCost {
            private final float base;
            private final float multiplier;
            private float modifiedBase;
            private float modifiedMultiplier;

            public Pre(LivingEntity entity, ISpell spell, float base, float multiplier) {
                super(entity, spell);
                this.base = base;
                this.multiplier = multiplier;
                this.modifiedBase = base;
            }

            /**
             * @return The base mana cost, containing all previous modifications.
             */
            public float getModifiedBase() {
                return modifiedBase;
            }

            /**
             * @return The unmodified base mana cost.
             */
            public float getBase() {
                return base;
            }

            /**
             * Sets the base mana cost.
             *
             * @param base The new value for the base mana cost.
             */
            public void setBase(float base) {
                this.modifiedBase = base;
            }

            /**
             * @return The mana cost multiplier, containing all previous modifications.
             */
            public float getModifiedMultiplier() {
                return modifiedMultiplier;
            }

            /**
             * @return The unmodified mana cost multiplier.
             */
            public float getMultiplier() {
                return multiplier;
            }

            /**
             * Sets the mana cost multiplier.
             *
             * @param multiplier The new value for the mana cost multiplier.
             */
            public void setMultiplier(float multiplier) {
                this.modifiedMultiplier = multiplier;
            }
        }

        /**
         * Event to modify the calculated mana cost.
         */
        public static final class Post extends ManaCost {
            private final float mana;
            private float modifiedMana;

            public Post(LivingEntity entity, ISpell spell, float mana) {
                super(entity, spell);
                this.mana = mana;
                this.modifiedMana = mana;
            }

            /**
             * @return The calculated mana cost, containing all previous modifications.
             */
            public float getModifiedMana() {
                return modifiedMana;
            }

            /**
             * @return The unmodified calculated mana cost.
             */
            public float getMana() {
                return mana;
            }

            /**
             * Sets the calculated mana cost.
             *
             * @param mana The new value for the calculated mana cost.
             */
            public void setMana(float mana) {
                this.modifiedMana = mana;
            }
        }
    }

    /**
     * Event to modify the burnout cost of the spell.
     */
    public static final class BurnoutCost extends SpellEvent {
        private final float burnout;
        private float modifiedBurnout;

        public BurnoutCost(LivingEntity entity, ISpell spell, float burnout) {
            super(entity, spell);
            this.burnout = burnout;
            this.modifiedBurnout = burnout;
        }

        /**
         * @return The burnout cost, containing all previous modifications.
         */
        public float getModifiedBurnout() {
            return modifiedBurnout;
        }

        /**
         * @return The unmodified burnout cost.
         */
        public float getBurnout() {
            return burnout;
        }

        /**
         * Sets the burnout cost.
         *
         * @param burnout The new value for the burnout cost.
         */
        public void setBurnout(float burnout) {
            this.modifiedBurnout = burnout;
        }
    }

    /**
     * Event to modify the reagents required by the spell.
     */
    public static final class ReagentCost extends SpellEvent {
        public final List<Either<Ingredient, ItemStack>> reagents;

        public ReagentCost(LivingEntity entity, ISpell spell, List<Either<Ingredient, ItemStack>> reagents) {
            super(entity, spell);
            this.reagents = reagents;
        }
    }
}
