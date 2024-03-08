package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public abstract class AffinityChangingEvent extends PlayerEvent {
    /**
     * The affinity being shifted.
     */
    public final Affinity affinity;
    /**
     * Whether this event was caused by a command or not.
     */
    public final boolean commandSource;

    public AffinityChangingEvent(Player player, Affinity affinity, boolean commandSource) {
        super(player);
        this.affinity = affinity;
        this.commandSource = commandSource;
    }

    /**
     * Event fired before applying an affinity shift.
     */
    public static final class Pre extends AffinityChangingEvent implements ICancellableEvent {
        /**
         * The amount by which the affinity is shifted. Can be modified.
         */
        public float shift;

        public Pre(Player player, Affinity affinity, float shift, boolean commandSource) {
            super(player, affinity, commandSource);
            this.shift = shift;
        }
    }

    /**
     * Event fired after applying an affinity shift.
     */
    public static final class Post extends AffinityChangingEvent {
        /**
         * The new amount after the affinity has been shifted.
         */
        public final float amount;

        public Post(Player player, Affinity affinity, float amount, boolean commandSource) {
            super(player, affinity, commandSource);
            this.amount = amount;
        }
    }
}
