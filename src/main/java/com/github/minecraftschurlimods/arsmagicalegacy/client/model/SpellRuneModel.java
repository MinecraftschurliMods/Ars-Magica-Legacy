package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class SpellRuneModel extends BakedModelWrapper<BakedModel> {
    public SpellRuneModel(ResourceLocation id, BakedModel originalModel) {
        super(originalModel);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand, IModelData extraData) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return List.of();
        if (player.isCreative() || player.hasEffect(AMMobEffects.TRUE_SIGHT.get()))
            return super.getQuads(state, side, rand, extraData);
        return List.of();
    }
}
