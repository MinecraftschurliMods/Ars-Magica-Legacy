package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class NatureScytheModel extends AMEntityModel<NatureScythe> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature_scythe"), "main");
    private final ModelPart rod;
    private final ModelPart head;
    private final ModelPart blade1;
    private final ModelPart blade2;
    private final ModelPart blade3;

    public NatureScytheModel(ModelPart root) {
        rod = addPart(root, "rod");
        head = addPart(root, "head");
        blade1 = addPart(root, "blade1");
        blade2 = addPart(root, "blade2");
        blade3 = addPart(root, "blade3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "rod", 0, 73, -1, -15.5f, -10, 2, 2, 36, 0, 0, 0);
        addCube(pd, "head", 84, 73, -1.5f, -20.5f, -13, 3, 12, 3, 0, 0, 0);
        addCube(pd, "blade1", 76, 73, -0.5f, -22.5f, -12.5f, 1, 30, 3, 0, 0, 0);
        addCube(pd, "blade2", 96, 73, -0.501f, 0, -14, 1, 12, 2, 0, 0, 0, 30, 0, 0);
        addCube(pd, "blade3", 102, 73, -0.5f, 2.5f, -17.5f, 1, 8, 1, 0, 0, 0, 60, 0, 0);
        return LayerDefinition.create(md, 128, 128);
    }

    @Override
    public void setupAnim(NatureScythe pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        float xRot = degToRad(pHeadPitch + pAgeInTicks * 36);
        float yRot = degToRad(-pNetHeadYaw);
        rod.xRot = xRot;
        head.xRot = xRot;
        blade1.xRot = xRot;
        blade2.xRot = xRot + degToRad(30);
        blade3.xRot = xRot + degToRad(60);
        rod.yRot = yRot;
        head.yRot = yRot;
        blade1.yRot = yRot;
        blade2.yRot = yRot;
        blade3.yRot = yRot;
    }
}
