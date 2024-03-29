package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

public record EntitySpawnRitualEffect(EntityType<?> entityType, Optional<CompoundTag> spawnData, Optional<Component> customName, boolean usePlayer) implements RitualEffect {
    public static final Codec<EntitySpawnRitualEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("entity_type").forGetter(EntitySpawnRitualEffect::entityType),
            CompoundTag.CODEC.optionalFieldOf("spawn_data").forGetter(EntitySpawnRitualEffect::spawnData),
            CodecHelper.COMPONENT.optionalFieldOf("custom_name").forGetter(EntitySpawnRitualEffect::customName),
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
        spawnData().ifPresent(itemStack::setTag);
        customName().ifPresent(itemStack::setHoverName);
        return entityType.spawn(level, itemStack, usePlayer ? player : null, pos, MobSpawnType.TRIGGERED, false, false) != null;
    }

    @Override
    public Codec<? extends RitualEffect> codec() {
        return CODEC;
    }

    public static final class Builder {
        private final EntityType<?> entityType;
        private Optional<CompoundTag> spawnData = Optional.empty();
        private Optional<Component> customName = Optional.empty();
        private boolean usePlayer = false;

        Builder(EntityType<?> entityType) {
            this.entityType = entityType;
        }

        public Builder withSpawnData(Optional<CompoundTag> spawnData) {
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
