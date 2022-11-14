package com.github.minecraftschurlimods.arsmagicalegacy.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.BakedModelWrapper;

public class SpellBookModel extends BakedModelWrapper<BakedModel> {
    private ItemTransforms.TransformType cameraTransformType;

    public SpellBookModel(final BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public BakedModel handlePerspective(final ItemTransforms.TransformType cameraTransformType, final PoseStack poseStack) {
        this.cameraTransformType = cameraTransformType;
        return ForgeHooksClient.handlePerspective(this, cameraTransformType, poseStack);
    }

    @Override
    public boolean doesHandlePerspectives() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return isHand();
    }

    private boolean isHand() {
        return cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
    }
}
