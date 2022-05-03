package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.VanillaGameEvent;

import java.util.Map;

/**
 *
 */
public record GameEventRitualTrigger(HolderSet<GameEvent> event) implements Ritual.RitualTrigger {
    public static final Codec<GameEventRitualTrigger> CODEC = RecordCodecBuilder.create(instance -> instance.group(RegistryCodecs.homogeneousList(Registry.GAME_EVENT_REGISTRY).fieldOf("event").forGetter(GameEventRitualTrigger::event)).apply(instance, GameEventRitualTrigger::new));

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((VanillaGameEvent evt) -> {
            BlockPos pos = evt.getEventPosition();
            Level level = evt.getLevel();
            if (level instanceof ServerLevel serverLevel) {
                Player player = serverLevel.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
                if (player != null) {
                    ritual.perform(player, serverLevel, pos, new Ritual.MapContext(Map.of("event", evt.getVanillaEvent())));
                }
            }
        });
    }

    @Override
    public boolean trigger(final ServerLevel level, final BlockPos pos, final Ritual.Context ctx) {
        GameEvent evt = ctx.get("event", GameEvent.class);
        assert evt != null;
        return event().contains(Registry.GAME_EVENT.createIntrusiveHolder(evt));
    }

    @Override
    public Codec<? extends Ritual.RitualTrigger> codec() {
        return CODEC;
    }
}
