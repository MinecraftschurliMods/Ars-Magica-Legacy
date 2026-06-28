package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jspecify.annotations.Nullable;

public record SpawnEntityRitualEffect(EntityType<?> entityType) implements RitualEffect {
    public static final MapCodec<SpawnEntityRitualEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(SpawnEntityRitualEffect::entityType)
    ).apply(inst, SpawnEntityRitualEffect::new));

    @Override
    public MapCodec<? extends RitualEffect> codec() {
        return CODEC;
    }

    @Override
    public void perform(@Nullable Player player, Level level, Vec3 vec) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Entity entity = entityType.create(level, EntitySpawnReason.MOB_SUMMONED);
        if (entity == null) return;
        entity.setPos(vec);
        if (entity instanceof Mob mob) {
            EventHooks.finalizeMobSpawn(mob, serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
        }
        level.addFreshEntity(entity);
    }
}
