package com.github.minecraftschurlimods.arsmagicalegacy.client.model;

import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity.ModelEntityRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class AMEntityModel<T extends ModelEntityRenderer.State> extends EntityModel<T> {
    public AMEntityModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(T state) {
        super.setupAnim(state);
        root.xRot = AMUtil.wrapToRadians(state.xRot);
        root.yRot = AMUtil.wrapToRadians(-state.yRot);
    }
}
