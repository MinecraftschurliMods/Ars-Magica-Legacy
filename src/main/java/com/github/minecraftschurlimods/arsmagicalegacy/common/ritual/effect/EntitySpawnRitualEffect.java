package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualEffect;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

public record EntitySpawnRitualEffect(EntityType<?> entityType, Optional<CompoundTag> spawnData, Optional<Component> customName, boolean usePlayer) implements RitualEffect {
    public static final Codec<EntitySpawnRitualEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ForgeRegistries.ENTITIES.getCodec().fieldOf("entity_type").forGetter(EntitySpawnRitualEffect::entityType),
            CompoundTag.CODEC.optionalFieldOf("spawn_data").forGetter(EntitySpawnRitualEffect::spawnData),
            CodecHelper.COMPONENT.optionalFieldOf("custom_name").forGetter(EntitySpawnRitualEffect::customName),
            Codec.BOOL.optionalFieldOf("use_player", false).forGetter(EntitySpawnRitualEffect::usePlayer)
    ).apply(inst, EntitySpawnRitualEffect::new));

    @Override
    public boolean performEffect(final Player player, final ServerLevel level, final BlockPos pos) {
        return entityType.spawn(level, spawnData().orElse(null), customName().orElse(null), usePlayer() ? player : null, pos, MobSpawnType.TRIGGERED, false, false) != null;
    }

    @Override
    public Codec<? extends RitualEffect> codec() {
        return CODEC;
    }
}
