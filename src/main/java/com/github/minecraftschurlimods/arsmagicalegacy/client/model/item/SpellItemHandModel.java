package com.github.minecraftschurlimods.arsmagicalegacy.client.model.item;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SpellItemHandModel extends SimpleBakedModel {
    private static final Random RANDOM = new Random(42L);
    public final BakedModel originalModel;

    public SpellItemHandModel(List<BakedQuad> pUnculledFaces, Map<Direction, List<BakedQuad>> pCulledFaces, boolean pHasAmbientOcclusion, boolean pUsesBlockLight, boolean pIsGui3d, TextureAtlasSprite pParticleIcon, ItemTransforms pTransforms, ItemOverrides pOverrides, BakedModel originalModel) {
        super(pUnculledFaces, pCulledFaces, pHasAmbientOcclusion, pUsesBlockLight, pIsGui3d, pParticleIcon, pTransforms, pOverrides);
        this.originalModel = originalModel;
    }

    public SpellItemHandModel(BakedModel originalModel) {
        this(originalModel.getQuads(null, null, new Random(42L)), directionMap(originalModel), false, false, originalModel.isGui3d(), originalModel.getParticleIcon(), ItemTransforms.NO_TRANSFORMS, originalModel.getOverrides(), originalModel);
    }

    private static Map<Direction, List<BakedQuad>> directionMap(BakedModel model) {
        Map<Direction, List<BakedQuad>> map = Maps.newEnumMap(Direction.class);
        for (Direction d : Direction.values()) {
            RANDOM.setSeed(42L);
            map.put(d, model.getQuads(null, d, RANDOM));
        }
        return map;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        if (cameraTransformType.firstPerson()) {
            poseStack.translate(cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND ? 0.5f : 0, 0.75f, 0);
        }
        if (cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
            poseStack.translate(0.25f, 0.5f, 0.5f);
        }
        poseStack.scale(0.5f, 0.5f, 0);
        return this;
    }
}
