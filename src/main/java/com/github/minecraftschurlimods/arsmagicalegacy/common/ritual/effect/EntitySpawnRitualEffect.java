package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 */
public record EntitySpawnRitualEffect(EntityType<?> entityType) implements RitualEffect {
    public static final Codec<EntitySpawnRitualEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(ForgeRegistries.ENTITIES.getCodec().fieldOf("entityType").forGetter(EntitySpawnRitualEffect::entityType)).apply(inst, EntitySpawnRitualEffect::new));

    @Override
    public boolean performEffect(final ServerLevel level, final BlockPos pos) {
        ArsMagicaLegacy.LOGGER.debug("Spawning entity: " + entityType.getDescription().getString());
        return entityType.spawn(level, null, null, null, pos, MobSpawnType.TRIGGERED, false, false) != null;
    }

    @Override
    public Codec<? extends RitualEffect> codec() {
        return CODEC;
    }
}
