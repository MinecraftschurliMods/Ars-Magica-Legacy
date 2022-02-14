package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Event fired before applying an affinity shift.
 */
@Cancelable
public class AffinityChangingEvent extends PlayerEvent {
    /**
     * The affinity being shifted.
     */
    public final IAffinity affinity;

    /**
     * The amount by which the affinity is shifted. Can be modified.
     */
    public float shift;

    public AffinityChangingEvent(Player player, IAffinity affinity, float shift) {
        super(player);
        this.affinity = affinity;
        this.shift = shift;
    }
}
