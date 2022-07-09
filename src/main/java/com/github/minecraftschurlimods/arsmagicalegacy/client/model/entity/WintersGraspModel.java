package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WintersGrasp;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class WintersGraspModel extends AMEntityModel<WintersGrasp> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "winters_grasp"), "main");
    private final Set<ModelPart> parts;

    public WintersGraspModel(ModelPart root) {
        parts = addParts(root, "arm", "hand", "outer_fingers", "inner_fingers", "thumb");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "arm", 94, 0, -3, -15, -3, 6, 26, 6, 0, 0, 0, 90, 0, 0);
        addCube(pd, "hand", 82, 44, -3, 12, -2, 6, 1, 5, 0, 0, 0, 90, 0, 0);
        addCube(pd, "outer_fingers", 104, 44, 2, 11, -2, 1, 1, 5, 0, 0, 0, 90, 0, 0);
        addCube(pd, "inner_fingers", 104, 44, -3, 11, -2, 1, 1, 5, 0, 0, 0, 90, 0, 0);
        addCube(pd, "thumb", 82, 50, 0, 11, -3, 3, 2, 1, 0, 0, 0, 90, 0, 0);
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(WintersGrasp pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        float xRot = degToRad(pHeadPitch + 90);
        float yRot = degToRad(-pNetHeadYaw);
        for (ModelPart mp : parts) {
            mp.xRot = xRot;
            mp.yRot = yRot;
        }
    }
}
