package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public abstract class AffinityChangingEvent extends PlayerEvent {
    /**
     * The affinity being shifted.
     */
    public final IAffinity affinity;
    /**
     * Whether this event was caused by a command or not.
     */
    public final boolean commandSource;

    public AffinityChangingEvent(Player player, IAffinity affinity, boolean commandSource) {
        super(player);
        this.affinity = affinity;
        this.commandSource = commandSource;
    }

    /**
     * Event fired before applying an affinity shift.
     */
    @Cancelable
    public static final class Pre extends AffinityChangingEvent {
        /**
         * The amount by which the affinity is shifted. Can be modified.
         */
        public float shift;

        public Pre(Player player, IAffinity affinity, float shift, boolean commandSource) {
            super(player, affinity, commandSource);
            this.shift = shift;
        }
    }

    /**
     * Event fired after applying an affinity shift.
     */
    @Cancelable
    public static final class Post extends AffinityChangingEvent {
        /**
         * The amount by which the affinity has been shifted.
         */
        public final float shift;

        public Post(Player player, IAffinity affinity, float shift, boolean commandSource) {
            super(player, affinity, commandSource);
            this.shift = shift;
        }
    }
}
