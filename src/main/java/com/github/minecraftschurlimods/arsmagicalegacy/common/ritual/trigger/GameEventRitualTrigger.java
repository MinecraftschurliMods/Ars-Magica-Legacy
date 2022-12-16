package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.VanillaGameEvent;

import java.util.Map;

public record GameEventRitualTrigger(HolderSet<GameEvent> event) implements RitualTrigger {
    public static final Codec<GameEventRitualTrigger> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.GAME_EVENT).fieldOf("event").forGetter(GameEventRitualTrigger::event)
    ).apply(instance, GameEventRitualTrigger::new));

    public static GameEventRitualTrigger simple(GameEvent gameEvent) {
        return any(gameEvent.builtInRegistryHolder());
    }

    public static GameEventRitualTrigger simple(HolderGetter<GameEvent> holderGetter, ResourceKey<GameEvent> gameEvent) {
        return GameEventRitualTrigger.any(holderGetter.getOrThrow(gameEvent));
    }

    public static GameEventRitualTrigger tag(HolderGetter<GameEvent> holderGetter, TagKey<GameEvent> gameEventTag) {
        return new GameEventRitualTrigger(holderGetter.getOrThrow(gameEventTag));
    }

    @SafeVarargs
    public static GameEventRitualTrigger any(Holder<GameEvent>... holders) {
        return new GameEventRitualTrigger(HolderSet.direct(holders));
    }

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((VanillaGameEvent evt) -> {
            Vec3 pos = evt.getEventPosition();
            Level level = evt.getLevel();
            if (event().contains(evt.getVanillaEvent().builtInRegistryHolder()) && level instanceof ServerLevel serverLevel) {
                for (Player player : serverLevel.getEntitiesOfClass(Player.class, AABB.ofSize(pos, 5, 5, 5))) {
                    if (ritual.perform(player, serverLevel, new BlockPos(pos), new Context.MapContext(Map.of("event", evt.getVanillaEvent())))) {
                        return;
                    }
                }
            }
        });
    }

    @Override
    public boolean trigger(final Player player, final ServerLevel level, final BlockPos pos, final Context ctx) {
        GameEvent evt = ctx.get("event", GameEvent.class);
        assert evt != null;
        return event().contains(evt.builtInRegistryHolder());
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
