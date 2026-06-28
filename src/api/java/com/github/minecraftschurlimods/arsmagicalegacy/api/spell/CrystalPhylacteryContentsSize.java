package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

/// Represents a size override for an [EntityType] when stored in a crystal phylactery.
/// If an [EntityType] is not in the [#DATA_MAP], its max HP is used.
/// If the [#size] is 0, the crystal phylactery will not support the [EntityType] at all.
///
/// @param size The size, i.e. the amount of kills needed, of the [EntityType] in a crystal phylactery.
@SuppressWarnings("deprecation")
public record CrystalPhylacteryContentsSize(int size) {
    public static final Codec<CrystalPhylacteryContentsSize> CODEC = ExtraCodecs.NON_NEGATIVE_INT.xmap(CrystalPhylacteryContentsSize::new, CrystalPhylacteryContentsSize::size);
    public static final DataMapType<EntityType<?>, CrystalPhylacteryContentsSize> DATA_MAP = DataMapType.builder(ArsMagicaApi.id("crystal_phylactery_storage_size"), Registries.ENTITY_TYPE, CODEC)
        .synced(CODEC, true)
        .build();

    /// @param type The [EntityType] to query.
    /// @return The size of the [EntityType] in a crystal phylactery.
    @SuppressWarnings("unchecked")
    public static int get(EntityType<?> type) {
        CrystalPhylacteryContentsSize data = type.builtInRegistryHolder().getData(DATA_MAP);
        if (data != null) return data.size();
        return DefaultAttributes.hasSupplier(type) ? (int) DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) type).getBaseValue(Attributes.MAX_HEALTH) : 0;
    }

    /// @param type The [EntityType] to query.
    /// @return Whether a size override for the given [EntityType] exists.
    public static boolean has(EntityType<?> type) {
        return type.builtInRegistryHolder().getData(DATA_MAP) != null;
    }
}
