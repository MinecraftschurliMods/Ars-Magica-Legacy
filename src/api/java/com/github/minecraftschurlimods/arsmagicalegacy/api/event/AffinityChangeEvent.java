package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Collections;
import java.util.Map;

/// Event that fires when the player's affinity shifts change. Has [Pre] and [Post] sub events.
///
/// This event is not cancelable. This event is fired on the main event bus.
public abstract class AffinityChangeEvent extends PlayerEvent {
    private final boolean commandSource;

    public AffinityChangeEvent(Player player, boolean commandSource) {
        super(player);
        this.commandSource = commandSource;
    }

    /// @return Whether the affinity change comes from a command or not.
    public boolean isCommandSource() {
        return commandSource;
    }

    /// Fired immediately before the affinity is changed. Use this event to modify
    /// the affinity shifts, and whether to bypass locked affinities or not.
    ///
    /// This event is cancelable. If the event is canceled, no affinity shifting will occur.
    public static class Pre extends AffinityChangeEvent implements ICancellableEvent {
        private final Map<Holder<Affinity>, Double> originalAffinityShifts;
        private final boolean originalBypassLocks;
        private final Map<Holder<Affinity>, Double> affinityShifts;
        private boolean bypassLocks;

        public Pre(Player player, Map<Holder<Affinity>, Double> affinityShifts, boolean bypassLocks, boolean commandSource) {
            super(player, commandSource);
            this.originalAffinityShifts = Collections.unmodifiableMap(affinityShifts);
            this.originalBypassLocks = bypassLocks;
            this.affinityShifts = affinityShifts;
            this.bypassLocks = bypassLocks;
        }

        /// @return An unmodifiable view of the original affinity shifts.
        public Map<Holder<Affinity>, Double> getOriginalAffinityShifts() {
            return originalAffinityShifts;
        }

        /// @return Whether locks should be bypassed originally.
        public boolean isOriginalBypassLocks() {
            return originalBypassLocks;
        }

        /// @return A modifiable map of affinity shifts. Use this map to apply your modifiers.
        public Map<Holder<Affinity>, Double> getAffinityShifts() {
            return affinityShifts;
        }

        /// @return Whether affinity locks should be bypassed.
        public boolean isBypassLocks() {
            return bypassLocks;
        }

        /// Modify whether affinity locks should be bypassed.
        ///
        /// @param bypassLocks Whether affinity locks should be bypassed.
        public void setBypassLocks(boolean bypassLocks) {
            this.bypassLocks = bypassLocks;
        }
    }

    /// Fired after the affinity shifts have been applied.
    public static class Post extends AffinityChangeEvent {
        private final Map<Holder<Affinity>, Double> affinityShifts;
        private final boolean bypassLocks;

        public Post(Player player, Map<Holder<Affinity>, Double> affinityShifts, boolean bypassLocks, boolean commandSource) {
            super(player, commandSource);
            this.affinityShifts = Collections.unmodifiableMap(affinityShifts);
            this.bypassLocks = bypassLocks;
        }

        /// @return An unmodifiable view of the affinity shifts.
        public Map<Holder<Affinity>, Double> getAffinityShifts() {
            return affinityShifts;
        }

        /// @return Whether affinity locks should be bypassed.
        public boolean isBypassLocks() {
            return bypassLocks;
        }
    }
}
