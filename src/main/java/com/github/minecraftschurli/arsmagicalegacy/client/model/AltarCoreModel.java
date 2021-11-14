package com.github.minecraftschurli.arsmagicalegacy.client.model;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AltarCoreModel extends BakedModelWrapper<BakedModel> {
    public  static final ResourceLocation         OVERLAY_LOC = new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/altar_core_overlay");
    private static final Lazy<TextureAtlasSprite> OVERLAY     = Lazy.concurrentOf(
            () -> Minecraft.getInstance()
                           .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                           .apply(OVERLAY_LOC));


    public AltarCoreModel(BakedModel originalModel) {
        super(originalModel);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state,
                                    @Nullable Direction side,
                                    @NotNull Random rand,
                                    @NotNull IModelData extraData) {
        if (state != null && state.hasProperty(AltarCoreBlock.FORMED) && state.getValue(AltarCoreBlock.FORMED)) {
            BlockState camoState = extraData.getData(AltarCoreBlockEntity.CAMO_STATE);
            if (camoState != null) {
                List<BakedQuad> quads = new ArrayList<>(Minecraft.getInstance().getBlockRenderer().getBlockModel(camoState).getQuads(camoState, side, rand, EmptyModelData.INSTANCE));
                if (!quads.isEmpty() && side == Direction.DOWN) {
                    quads.add(new RetexturedBakedQuad(quads.get(0), OVERLAY.get()));
                }
                return quads;
            }
        }
        return super.getQuads(state, side, rand, extraData);
    }
}
