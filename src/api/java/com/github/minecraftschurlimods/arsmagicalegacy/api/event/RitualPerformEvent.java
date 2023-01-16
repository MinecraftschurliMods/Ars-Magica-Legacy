package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Event that fires when a player triggers a ritual.
 */
public sealed class RitualPerformEvent extends PlayerEvent {
    /**
     * The ritual being performed.
     */
    public final Ritual ritual;
    /**
     * The level the ritual is being performed in.
     */
    public final ServerLevel level;
    /**
     * The position the ritual is being performed at.
     */
    public final BlockPos pos;
    /**
     * The ritual context for the ritual.
     */
    public final IContext context;

    private RitualPerformEvent(Player player, Ritual ritual, ServerLevel level, BlockPos pos, IContext context) {
        super(player);
        this.ritual = ritual;
        this.level = level;
        this.pos = pos;
        this.context = context;
    }

    /**
     * Fired before any ritual conditions are checked. If the event is canceled, the ritual will fail.
     */
    @Cancelable
    public static final class Pre extends RitualPerformEvent {
        public Pre(Player player, Ritual ritual, ServerLevel level, BlockPos pos, IContext context) {
            super(player, ritual, level, pos, context);
        }
    }

    /**
     * Fired after the ritual was successfully executed.
     */
    public static final class Post extends RitualPerformEvent {
        public Post(Player player, Ritual ritual, ServerLevel level, BlockPos pos, IContext context) {
            super(player, ritual, level, pos, context);
        }
    }
}
