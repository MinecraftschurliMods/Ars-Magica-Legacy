package at.minecraftschurli.mods.arsmagicalegacy.client.model;

import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.ModelEntityRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
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
