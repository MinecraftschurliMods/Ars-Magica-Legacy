package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.model.data.ModelData;
import org.jspecify.annotations.Nullable;

import java.util.List;

public final class AltarCoreModel extends DelegateBlockStateModel implements DynamicBlockStateModel {
    private final LoadingCache<BlockState, BlockStateModel> camoModelCache = CacheBuilder
        .newBuilder()
        .maximumSize(100)
        .build(CacheLoader.from(AltarCoreModel::getCamoModel));

    public AltarCoreModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        BlockState camo = getCamoState(level, pos, state);
        if (camo == null) {
            super.collectParts(level, pos, state, random, parts);
            return;
        }
        BlockStateModel camoModel = camoModelCache.getUnchecked(camo);
        camoModel.collectParts(level, pos, camo, random, parts);
        super.collectParts(level, pos, state, random, parts);
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        int flags = super.materialFlags(level, pos, state);
        BlockState camoState = getCamoState(level, pos, state);
        if (camoState != null) {
            flags |= camoModelCache.getUnchecked(camoState).materialFlags(level, pos, camoState);
        }
        return flags;
    }

    private @Nullable BlockState getCamoState(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        ModelData modelData = level.getModelData(pos);
        if (!state.hasProperty(AltarCoreBlock.FORMED) || !state.getValue(AltarCoreBlock.FORMED) || !modelData.has(AltarCoreBlockEntity.CAMO)) {
            return null;
        }
        return modelData.get(AltarCoreBlockEntity.CAMO);
    }

    private static BlockStateModel getCamoModel(BlockState camo) {
        return AMClientUtil.mc().getModelManager().getBlockStateModelSet().get(camo);
    }

    public record Unbaked(BlockStateModel.Unbaked parent) implements CustomUnbakedBlockStateModel {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BlockStateModel.Unbaked.CODEC.fieldOf("parent").forGetter(Unbaked::parent)
        ).apply(i, Unbaked::new));

        @Override
        public BlockStateModel bake(ModelBaker modelBakery) {
            return new AltarCoreModel(parent.bake(modelBakery));
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            parent.resolveDependencies(resolver);
        }

        @Override
        public MapCodec<Unbaked> codec() {
            return MAP_CODEC;
        }
    }
}
