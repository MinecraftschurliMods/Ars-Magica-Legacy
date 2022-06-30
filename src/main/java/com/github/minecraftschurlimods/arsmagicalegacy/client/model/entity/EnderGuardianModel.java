package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EnderGuardianModel extends AMBossEntityModel<EnderGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_guardian"), "main");
    private final ModelPart rightOuterWing;
    private final ModelPart rightWing;
    private final ModelPart leftOuterWing;
    private final ModelPart leftWing;

    public EnderGuardianModel(ModelPart root) {
        rightOuterWing = addPart(root, "right_outer_wing");
        rightWing = addPart(root, "right_wing");
        leftOuterWing = addPart(root, "left_outer_wing");
        leftWing = addPart(root, "left_wing");
        addParts(root, "neck", "body", "tail", "top_rib", "middle_rib", "bottom_rib", "backbone1", "backbone2", "backbone3", "backbone4", "right_inner_wing", "left_inner_wing");
        addHeadPart(root, "head");
        addPositiveSwingingParts(root, "right_arm", "right_hand");
        addNegativeSwingingParts(root, "left_arm", "left_hand");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 0, 0, -4, -8, -4, 8, 8, 8, 0, -6, 0);
        addCube(pd, "neck", 0, 16, -6, -1, -1, 12, 2, 2, 0, -4.999f, 0);
        addCube(pd, "body", 0, 24, -2, -7, -2, 4, 14, 4, 0, 1, 0);
        addCube(pd, "tail", 16, 24, -1, -8, 3, 2, 16, 2, 0, 15.5f, 1, 30, 0, 0);
        addCube(pd, "top_rib", 0, 20, -4, -1, -1, 8, 2, 2, 0, -2, 0);
        addCube(pd, "middle_rib", 0, 20, -4, -1, -1, 8, 2, 2, 0, 1, 0);
        addCube(pd, "bottom_rib", 0, 20, -4, -1, -1, 8, 2, 2, 0, 4, 0);
        addCube(pd, "backbone1", 20, 20, -1, -1, 2, 2, 2, 1, 0, -4, 0);
        addCube(pd, "backbone2", 20, 20, -1, -1, 2, 2, 2, 1, 0, -1, 0);
        addCube(pd, "backbone3", 20, 20, -1, -1, 2, 2, 2, 1, 0, 2, 0);
        addCube(pd, "backbone4", 20, 20, -1, -1, 2, 2, 2, 1, 0, 5, 0);
        addCube(pd, "right_arm", 32, 0, -8, -1, -1.5f, 2, 11, 3, 0, -5, 0);
        addCube(pd, "right_hand", 32, 14, -8, 10, -1, 2, 10, 2, 0, -5, 0);
        addCube(pd, "right_inner_wing", 0, 42, 3.5f, 0, -2, 10, 2, 2, 0, -6, 0, 180, -17.5f, 180);
        addCube(pd, "right_outer_wing", 0, 46, -1, -1, -1, 14, 2, 2, -11.5f, -4.5f, 5, 0, 90, 20);
        addMirroredCube(pd, "right_wing", 32, 26, -13.5f, 1, 0, 15, 20, 0, -11.5f, -4.5f, 5, 0, -90, 20);
        addCube(pd, "left_arm", 42, 0, 6, -1, -1.5f, 2, 11, 3, 0, -5, 0);
        addCube(pd, "left_hand", 40, 14, 6, 10, -1, 2, 10, 2, 0, -5, 0);
        addCube(pd, "left_inner_wing", 0, 42, -13.5f, 0, -2, 10, 2, 2, 0, -6, 0, 180, 17.5f, -180);
        addCube(pd, "left_outer_wing", 0, 46, -13, -1, -1, 14, 2, 2, 11.5f, -4.5f, 5, 0, -90, -20);
        addCube(pd, "left_wing", 32, 26, -1.5f, 1, 0, 15, 20, 0, 11.5f, -4.5f, 5, 0, 90, -20);
        return LayerDefinition.create(md, 64, 64);
    }

    @Override
    public void setupAnim(EnderGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        float rot = Mth.cos(degToRad(pEntity.getWingFlapTime() + pAgeInTicks - pEntity.tickCount));
        rightOuterWing.yRot = -degToRad(135) - rot;
        rightWing.yRot = degToRad(45) - rot;
        leftOuterWing.yRot = degToRad(135) + rot;
        leftWing.yRot = -degToRad(45) + rot;
    }
}
