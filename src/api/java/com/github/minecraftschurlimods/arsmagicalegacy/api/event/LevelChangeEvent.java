package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/// Event that fires when the player's magic level changes.
///
/// This event is not cancelable. This event is fired on the main event bus.
@SuppressWarnings("unused")
public class LevelChangeEvent extends PlayerEvent {
    private final int oldLevel;
    private final int newLevel;

    public LevelChangeEvent(Player player, int oldLevel, int newLevel) {
        super(player);
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    /// @return The old level of the player.
    public int getOldLevel() {
        return oldLevel;
    }

    /// @return The new level of the player.
    public int getNewLevel() {
        return newLevel;
    }
}
