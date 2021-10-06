package com.github.minecraftschurli.arsmagicalegacy.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * Event that fires when the player levels up its magic.
 */
public final class PlayerLevelUpEvent extends PlayerEvent {
    private final int level;

    public PlayerLevelUpEvent(Player player, int level) {
        super(player);
        this.level = level;
    }

    /**
     * Get the level the player leveled up to.
     *
     * @return the level the player leveled up to
     */
    public int getLevel() {
        return level;
    }
}
