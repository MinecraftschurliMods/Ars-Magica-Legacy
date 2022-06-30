package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WaterGuardianModel extends AMBossEntityModel<WaterGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_guardian"), "main");
    private final ModelPart innerOrb1;
    private final ModelPart outerOrb1;
    private final ModelPart innerOrb2;
    private final ModelPart outerOrb2;
    private final ModelPart innerOrb3;
    private final ModelPart outerOrb3;
    private final ModelPart innerOrb4;
    private final ModelPart outerOrb4;
    private final ModelPart rod1;
    private final ModelPart rod2;
    private final ModelPart rod3;
    private final ModelPart rod4;
    private final ModelPart northernTentacle;
    private final ModelPart easternTentacle;
    private final ModelPart southernTentacle;
    private final ModelPart westernTentacle;
    private final ModelPart outerTentacle1;
    private final ModelPart outerTentacle2;
    private final ModelPart outerTentacle3;
    private final ModelPart outerTentacle4;

    public WaterGuardianModel(ModelPart root) {
        innerOrb1 = addPart(root, "inner_orb1");
        outerOrb1 = addPart(root, "outer_orb1");
        innerOrb2 = addPart(root, "inner_orb2");
        outerOrb2 = addPart(root, "outer_orb2");
        innerOrb3 = addPart(root, "inner_orb3");
        outerOrb3 = addPart(root, "outer_orb3");
        innerOrb4 = addPart(root, "inner_orb4");
        outerOrb4 = addPart(root, "outer_orb4");
        rod1 = addPart(root, "rod1");
        rod2 = addPart(root, "rod2");
        rod3 = addPart(root, "rod3");
        rod4 = addPart(root, "rod4");
        northernTentacle = addPart(root, "northern_tentacle");
        easternTentacle = addPart(root, "eastern_tentacle");
        southernTentacle = addPart(root, "southern_tentacle");
        westernTentacle = addPart(root, "western_tentacle");
        outerTentacle1 = addPart(root, "outer_tentacle1");
        outerTentacle2 = addPart(root, "outer_tentacle2");
        outerTentacle3 = addPart(root, "outer_tentacle3");
        outerTentacle4 = addPart(root, "outer_tentacle4");
        addParts(root, "top_head", "middle_head", "bottom_head", "northern_outer_body", "eastern_outer_body", "southern_outer_body", "western_outer_body", "northern_inner_body", "eastern_inner_body", "southern_inner_body", "western_inner_body");
        addRotatingCube(root, 4, "core");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "top_head", 0, 11, -4, -0.5f, -4, 8, 1, 8, 0, -0.5f, 0);
        addCube(pd, "middle_head", 0, 0, -5, -0.5f, -5, 10, 1, 10, 0, 0.5f, 0);
        addCube(pd, "bottom_head", 0, 11, -4, -0.5f, -4, 8, 1, 8, 0, 2.5f, 0);
        addCube(pd, "core1", 44, 22, -2, -2, -2, 4, 4, 4, 0, 5, 0);
        addCube(pd, "core2", 44, 22, -2, -2, -2, 4, 4, 4, 0, 5, 0);
        addCube(pd, "core3", 44, 22, -2, -2, -2, 4, 4, 4, 0, 5, 0);
        addCube(pd, "northern_outer_body", 0, 20, -5, -5, 4, 10, 5, 1, 0, 6, 0);
        addCube(pd, "eastern_outer_body", 40, 0, 4, -5, -4, 1, 5, 8, 0, 6, 0);
        addCube(pd, "southern_outer_body", 0, 20, -5, -5, -5, 10, 5, 1, 0, 6, 0);
        addCube(pd, "western_outer_body", 40, 0, -5, -5, -4, 1, 5, 8, 0, 6, 0);
        addCube(pd, "northern_inner_body", 0, 26, -4, -3, 3, 8, 3, 1, 0, 6, 0);
        addCube(pd, "eastern_inner_body", 44, 13, 3, -3, -3, 1, 3, 6, 0, 6, 0);
        addCube(pd, "southern_inner_body", 0, 26, -4, -3, -4, 8, 3, 1, 0, 6, 0);
        addCube(pd, "western_inner_body", 44, 13, -4, -3, -3, 1, 3, 6, 0, 6, 0);
        addCube(pd, "inner_orb1", 32, 16, -11, 8.5f, -0.5f, 2, 1, 1, 0, -6.5f, 0, 0, -90, 0);
        addCube(pd, "outer_orb1", 32, 13, -1, -10.5f, -10, 2, 1, 2, 0, -6.5f, 0, 90, 0, 0);
        addCube(pd, "inner_orb2", 32, 16, -11, 8.5f, -0.5f, 2, 1, 1, 0, -6.5f, 0, 0, -180, 0);
        addCube(pd, "outer_orb2", 32, 13, -1, -10.5f, -10, 2, 1, 2, 0, -6.5f, 0, 90, -90, 0);
        addCube(pd, "inner_orb3", 32, 16, -11, 8.5f, -0.5f, 2, 1, 1, 0, -6.5f, 0, 0, 90, 0);
        addCube(pd, "outer_orb3", 32, 13, -1, -10.5f, -10, 2, 1, 2, 0, -6.5f, 0, 90, -180, 0);
        addCube(pd, "inner_orb4", 32, 16, -11, 8.5f, -0.5f, 2, 1, 1, 0, -6.5f, 0, 0, 0, 0);
        addCube(pd, "outer_orb4", 32, 13, -1, -10.5f, -10, 2, 1, 2, 0, -6.5f, 0, 90, 90, 0);
        addCube(pd, "rod1", 22, 20, 9.5f, -0.5f, 4, 1, 1, 10, 0, 12, 0, 90, 135, 0);
        addCube(pd, "rod2", 22, 20, 9.5f, -0.5f, 4, 1, 1, 10, 0, 12, 0, 90, -135, 0);
        addCube(pd, "rod3", 22, 20, 9.5f, -0.5f, 4, 1, 1, 10, 0, 12, 0, 90, -45, 0);
        addCube(pd, "rod4", 22, 20, 9.5f, -0.5f, 4, 1, 1, 10, 0, 12, 0, 90, 45, 0);
        addCube(pd, "northern_tentacle", 60, 15, -0.5f, 0, -4, 1, 10, 1, 0, 6, 0);
        addCube(pd, "eastern_tentacle", 60, 15, -4, 0, -0.5f, 1, 10, 1, 0, 6, 0);
        addCube(pd, "southern_tentacle", 60, 15, -0.5f, 0, 3, 1, 10, 1, 0, 6, 0);
        addCube(pd, "western_tentacle", 60, 15, 3, 0, -0.5f, 1, 10, 1, 0, 6, 0);
        addCube(pd, "outer_tentacle1", 60, 0, -4, 0, -4, 1, 14, 1, 0, 6, 0);
        addCube(pd, "outer_tentacle2", 60, 0, -4, 0, 3, 1, 14, 1, 0, 6, 0);
        addCube(pd, "outer_tentacle3", 60, 0, 3, 0, 3, 1, 14, 1, 0, 6, 0);
        addCube(pd, "outer_tentacle4", 60, 0, 3, 0, -4, 1, 14, 1, 0, 6, 0);
        return LayerDefinition.create(md, 64, 32);
    }

    @Override
    public void setupAnim(WaterGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        float rot = (float) (((pAgeInTicks + pEntity.getSpinRotation()) % 360) * Math.PI / 180);
        innerOrb1.yRot = -(float) (Math.PI / 2) + rot;
        outerOrb1.yRot = rot;
        innerOrb2.yRot = -(float) Math.PI + rot;
        outerOrb2.yRot = -(float) (Math.PI / 2) + rot;
        innerOrb3.yRot = (float) (Math.PI / 2) + rot;
        outerOrb3.yRot = -(float) Math.PI + rot;
        innerOrb4.yRot = rot;
        outerOrb4.yRot = (float) (Math.PI / 2) + rot;
        rod1.yRot = (float) (3 * Math.PI / 4) + rot;
        rod2.yRot = -(float) (3 * Math.PI / 4) + rot;
        rod3.yRot = -(float) (Math.PI / 4) + rot;
        rod4.yRot = (float) (Math.PI / 4) + rot;
        float swing = Mth.sin(degToRad(pAgeInTicks % 360 * 4)) * 0.1f;
        northernTentacle.xRot = -swing;
        easternTentacle.zRot = swing;
        southernTentacle.xRot = swing;
        westernTentacle.zRot = -swing;
        outerTentacle1.xRot = swing;
        outerTentacle1.zRot = swing;
        outerTentacle2.xRot = swing;
        outerTentacle2.zRot = -swing;
        outerTentacle3.xRot = -swing;
        outerTentacle3.zRot = -swing;
        outerTentacle4.xRot = -swing;
        outerTentacle4.zRot = swing;
    }
}
