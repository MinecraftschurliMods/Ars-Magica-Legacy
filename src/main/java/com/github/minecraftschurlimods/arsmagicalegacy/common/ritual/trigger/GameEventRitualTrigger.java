package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
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

public record GameEventRitualTrigger(HolderSet<GameEvent> event) implements IRitualTrigger {
    public static final Codec<GameEventRitualTrigger> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registry.GAME_EVENT_REGISTRY).fieldOf("event").forGetter(GameEventRitualTrigger::event)
    ).apply(instance, GameEventRitualTrigger::new));

    public GameEventRitualTrigger(GameEvent gameEvent) {
        this(gameEvent.builtInRegistryHolder());
    }

    public GameEventRitualTrigger(TagKey<GameEvent> gameEventTag) {
        this(RegistryAccess.BUILTIN.get().registryOrThrow(Registry.GAME_EVENT_REGISTRY).getOrCreateTag(gameEventTag));
    }

    public GameEventRitualTrigger(ResourceKey<GameEvent> gameEvent) {
        this(RegistryAccess.BUILTIN.get().registryOrThrow(Registry.GAME_EVENT_REGISTRY).getOrCreateHolder(gameEvent));
    }

    @SafeVarargs
    public GameEventRitualTrigger(Holder<GameEvent>... holders) {
        this(HolderSet.direct(holders));
    }

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((VanillaGameEvent evt) -> {
            BlockPos pos = evt.getEventPosition();
            Level level = evt.getLevel();
            if (event().contains(evt.getVanillaEvent().builtInRegistryHolder()) && level instanceof ServerLevel serverLevel) {
                for (Player player : serverLevel.getEntitiesOfClass(Player.class, AABB.ofSize(Vec3.atCenterOf(pos), 5, 5, 5))) {
                    if (ritual.perform(player, serverLevel, pos, new IContext.MapContext(Map.of("event", evt.getVanillaEvent())))) {
                        return;
                    }
                }
            }
        });
    }

    @Override
    public boolean trigger(final Player player, final ServerLevel level, final BlockPos pos, final IContext ctx) {
        GameEvent evt = ctx.get("event", GameEvent.class);
        assert evt != null;
        return event().contains(evt.builtInRegistryHolder());
    }

    @Override
    public Codec<? extends IRitualTrigger> codec() {
        return CODEC;
    }
}
