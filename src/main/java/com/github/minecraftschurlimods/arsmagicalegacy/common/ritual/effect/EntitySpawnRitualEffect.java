package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

import java.util.Optional;

public record EntitySpawnRitualEffect(EntityType<?> entityType, Optional<CustomData> spawnData, Optional<Component> customName, boolean usePlayer) implements RitualEffect {
    public static final MapCodec<EntitySpawnRitualEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(EntitySpawnRitualEffect::entityType),
            CustomData.CODEC.optionalFieldOf("spawn_data").forGetter(EntitySpawnRitualEffect::spawnData),
            ComponentSerialization.CODEC.optionalFieldOf("custom_name").forGetter(EntitySpawnRitualEffect::customName),
            Codec.BOOL.optionalFieldOf("use_player", false).forGetter(EntitySpawnRitualEffect::usePlayer)
    ).apply(inst, EntitySpawnRitualEffect::new));

    public static Builder builder(EntityType<?> entityType) {
        return new Builder(entityType);
    }

    public static EntitySpawnRitualEffect simple(EntityType<?> entityType) {
        return new Builder(entityType).build();
    }

    @Override
    public boolean performEffect(Player player, ServerLevel level, BlockPos pos) {
        ItemStack itemStack = new ItemStack(Items.STICK);
        spawnData().ifPresent(data -> itemStack.set(DataComponents.ENTITY_DATA, data));
        customName().ifPresent(name -> itemStack.set(DataComponents.CUSTOM_NAME, name));
        return entityType.spawn(level, itemStack, usePlayer ? player : null, pos, MobSpawnType.TRIGGERED, false, false) != null;
    }

    @Override
    public MapCodec<? extends RitualEffect> codec() {
        return CODEC;
    }

    public static final class Builder {
        private final EntityType<?> entityType;
        private Optional<CustomData> spawnData = Optional.empty();
        private Optional<Component> customName = Optional.empty();
        private boolean usePlayer = false;

        Builder(EntityType<?> entityType) {
            this.entityType = entityType;
        }

        public Builder withSpawnData(Optional<CustomData> spawnData) {
            this.spawnData = spawnData;
            return this;
        }

        public Builder withCustomName(Optional<Component> customName) {
            this.customName = customName;
            return this;
        }

        public Builder usePlayer(boolean usePlayer) {
            this.usePlayer = usePlayer;
            return this;
        }

        public EntitySpawnRitualEffect build() {
            return new EntitySpawnRitualEffect(entityType, spawnData, customName, usePlayer);
        }
    }
}
