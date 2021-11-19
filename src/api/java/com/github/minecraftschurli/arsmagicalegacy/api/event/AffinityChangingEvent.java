package com.github.minecraftschurli.arsmagicalegacy.api.event;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Event fired before applying the affinity shift. Can be cancelled.
 */
@Cancelable
public class AffinityChangingEvent extends PlayerEvent {

    /**
     * The Affinity being shifted.
     */
    public final IAffinity affinity;

    /**
     * The amount the Affinity is shifted. Can be modified.
     */
    public float shift;

    public AffinityChangingEvent(Player player, IAffinity affinity, float shift) {
        super(player);
        this.affinity = affinity;
        this.shift = shift;
    }
}
