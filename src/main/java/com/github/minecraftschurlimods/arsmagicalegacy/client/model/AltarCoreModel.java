package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AltarCoreModel extends BakedModelWrapper<BakedModel> {
    public AltarCoreModel(BakedModel originalModel) {
        super(originalModel);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        if (state != null && state.hasProperty(AltarCoreBlock.FORMED) && state.getValue(AltarCoreBlock.FORMED)) {
            BlockState camoState = extraData.get(AltarCoreBlockEntity.CAMO_STATE);
            if (camoState != null) {
                BakedModel blockModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(camoState);
                List<BakedQuad> quads = new ArrayList<>(blockModel.getQuads(camoState, side, rand, ModelData.EMPTY, renderType));
                quads.addAll(super.getQuads(state, side, rand, extraData, renderType));
                return quads;
            }
        }
        return super.getQuads(state, side, rand, extraData, renderType);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        if (state.hasProperty(AltarCoreBlock.FORMED) && state.getValue(AltarCoreBlock.FORMED)) {
            BlockState camoState = data.get(AltarCoreBlockEntity.CAMO_STATE);
            if (camoState != null) {
                BakedModel blockModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(camoState);
                return ChunkRenderTypeSet.union(blockModel.getRenderTypes(camoState, rand, ModelData.EMPTY), super.getRenderTypes(state, rand, data));
            }
        }
        return super.getRenderTypes(state, rand, data);
    }
}
