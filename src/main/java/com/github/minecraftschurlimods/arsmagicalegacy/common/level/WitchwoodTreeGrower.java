package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

public class WitchwoodTreeGrower extends AbstractMegaTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pLargeHive) {
        return null;
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom) {
        return ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY).getOrCreateHolderOrThrow(ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(ArsMagicaAPI.MOD_ID, "witchwood_tree"))); // TODO: Change when MinecraftForge/MinecraftForge#8956 gets merged
    }
}
