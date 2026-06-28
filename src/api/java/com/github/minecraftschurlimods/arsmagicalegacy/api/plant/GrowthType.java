package com.github.minecraftschurlimods.arsmagicalegacy.api.plant;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

/// Holds a [Plant]'s growth logic.
public interface GrowthType {
    Codec<GrowthType> CODEC = Codec.lazyInitialized(() -> AMRegistries.GROWTH_TYPES.byNameCodec().dispatch(GrowthType::codec, Function.identity()));

    /// @return The registered [MapCodec].
    MapCodec<? extends GrowthType> codec();

    /// @param context The [GrowthContext] to use.
    /// @return Whether the plant can currently be grown or not.
    boolean canGrow(GrowthContext context);

    /// Grows the plant, if possible.
    ///
    /// @param context The [GrowthContext] to use.
    void grow(GrowthContext context);

    /// @param context The [GrowthContext] to use.
    /// @return Whether the plant can currently be harvested or not.
    boolean canHarvest(GrowthContext context);

    /// Harvests the plant, if possible.
    ///
    /// @param context The [GrowthContext] to use.
    /// @param replant Whether the plant should be replanted or not, if possible.
    /// @return A list of [ItemStack], representing the drops of the plant.
    List<ItemStack> harvest(GrowthContext context, boolean replant);
}
