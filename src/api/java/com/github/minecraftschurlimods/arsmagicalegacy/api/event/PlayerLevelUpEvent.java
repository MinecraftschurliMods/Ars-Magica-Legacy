package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

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
     * @return The level the player leveled up to.
     */
    public int getLevel() {
        return level;
    }
}
