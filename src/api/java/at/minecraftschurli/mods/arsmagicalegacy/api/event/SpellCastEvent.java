package at.minecraftschurli.mods.arsmagicalegacy.api.event;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;

/// Event that is fired when a spell is cast. Has [Pre] and [Post] sub events.
///
/// In order to perform additional functionality when a particular spell part is cast, use [SpellPartCastEvent].
///
/// This event is not cancelable. This event is fired on the main event bus.
@SuppressWarnings("unused")
public abstract class SpellCastEvent extends SpellEvent {
    private final double mana;
    private final double burnout;

    public SpellCastEvent(LivingEntity entity, Spell spell, double mana, double burnout) {
        super(entity, spell);
        this.mana = mana;
        this.burnout = burnout;
    }

    /// @return The mana cost of the spell. Final by this point, to modify, use [ManaBurnoutCostEvent].
    public double getMana() {
        return mana;
    }

    /// @return The burnout cost of the spell. Final by this point, to modify, use [ManaBurnoutCostEvent].
    public double getBurnout() {
        return burnout;
    }

    /// Fired immediately before the spell is cast. Use this event to modify
    /// whether to consume mana and burnout, and whether to award magic xp.
    ///
    /// This event is cancelable. If the event is canceled, the spell will not be cast.
    public static class Pre extends SpellCastEvent implements ICancellableEvent {
        private final boolean originalConsume;
        private final boolean originalAwardXp;
        private boolean consume;
        private boolean awardXp;
        private Component message = Component.empty();

        public Pre(LivingEntity entity, Spell spell, double mana, double burnout, boolean consume, boolean awardXp) {
            super(entity, spell, mana, burnout);
            originalConsume = consume;
            originalAwardXp = awardXp;
            this.consume = consume;
            this.awardXp = awardXp;
        }

        /// @return Whether to consume mana and burnout.
        public boolean isOriginalConsume() {
            return originalConsume;
        }

        /// @return Whether to award magic xp.
        public boolean isOriginalAwardXp() {
            return originalAwardXp;
        }

        /// @return Whether to consume mana and burnout. Potentially modified.
        public boolean isConsume() {
            return consume;
        }

        /// Modify whether to consume mana and burnout.
        ///
        /// @param consume Whether to consume mana and burnout.
        public void setConsume(boolean consume) {
            this.consume = consume;
        }

        /// @return Whether to award magic xp. Potentially modified.
        public boolean isAwardXp() {
            return awardXp;
        }

        /// Modify whether to award magic xp.
        ///
        /// @param awardXp Whether to award magic xp.
        public void setAwardXp(boolean awardXp) {
            this.awardXp = awardXp;
        }

        /// @deprecated Use [Pre#setCanceled(Component)] to cancel, and [Pre#setUncanceled()] to uncancel.
        @Deprecated
        @Override
        public void setCanceled(boolean canceled) {
            ICancellableEvent.super.setCanceled(canceled);
        }

        /// Mark the event as canceled.
        ///
        /// @param message The cancellation reason. This will be displayed to the player as a status message.
        public void setCanceled(Component message) {
            ICancellableEvent.super.setCanceled(true);
            this.message = message;
        }

        /// Mark the event as uncanceled.
        public void setUncanceled() {
            ICancellableEvent.super.setCanceled(false);
            this.message = Component.empty();
        }

        /// @return The cancellation reason. This will be displayed to the player as a status message.
        public Component getCancellationMessage() {
            return message;
        }
    }

    /// Fired after the spell is cast, mana and burnout have been modified (if applicable),
    /// and magic xp has been awarded (if applicable).
    public static class Post extends SpellCastEvent {
        private final boolean consume;
        private final boolean awardXp;

        public Post(LivingEntity entity, Spell spell, double mana, double burnout, boolean consume, boolean awardXp) {
            super(entity, spell, mana, burnout);
            this.consume = consume;
            this.awardXp = awardXp;
        }

        /// @return Whether to consume mana and burnout.
        public boolean isConsume() {
            return consume;
        }

        /// @return Whether to award magic xp.
        public boolean isAwardXp() {
            return awardXp;
        }
    }
}
