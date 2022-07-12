package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.BakedModelWrapper;

public class SpellBookModel extends BakedModelWrapper<BakedModel> {
    public SpellBookModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        return new TransformModel(cameraTransformType);
    }

    private class TransformModel extends BakedModelWrapper<BakedModel> {
        private final ItemTransforms.TransformType cameraTransformType;

        public TransformModel(ItemTransforms.TransformType cameraTransformType) {
            super(SpellBookModel.this);
            this.cameraTransformType = cameraTransformType;
        }

        @Override
        public boolean isCustomRenderer() {
            return isHand();
        }

        private boolean isHand() {
            return cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
        }
    }
}
