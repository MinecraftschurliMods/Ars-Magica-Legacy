package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LifeGuardianModel extends AMEntityModel<LifeGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "life_guardian"), "main");
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;
    private final ModelPart core4;
    private final ModelPart core5;
    private final ModelPart core6;
    private final ModelPart core7;
    private final ModelPart core8;
    private final ModelPart xRod;
    private final ModelPart zRod;
    private final ModelPart middleRodNorth;
    private final ModelPart middleRodEast;
    private final ModelPart middleRodSouth;
    private final ModelPart middleRodWest;
    private final ModelPart topRodNorth;
    private final ModelPart topRodEast;
    private final ModelPart topRodSouth;
    private final ModelPart topRodWest;
    private final ModelPart circleNorth;
    private final ModelPart circleEast;
    private final ModelPart circleSouth;
    private final ModelPart circleWest;

    public LifeGuardianModel(ModelPart root) {
        core1 = addPart(root, "core1");
        core2 = addPart(root, "core2");
        core3 = addPart(root, "core3");
        core4 = addPart(root, "core4");
        core5 = addPart(root, "core5");
        core6 = addPart(root, "core6");
        core7 = addPart(root, "core7");
        core8 = addPart(root, "core8");
        xRod = addPart(root, "x_rod");
        zRod = addPart(root, "z_rod");
        middleRodNorth = addPart(root, "middle_rod_north");
        middleRodEast = addPart(root, "middle_rod_east");
        middleRodSouth = addPart(root, "middle_rod_south");
        middleRodWest = addPart(root, "middle_rod_west");
        topRodNorth = addPart(root, "top_rod_north");
        topRodEast = addPart(root, "top_rod_east");
        topRodSouth = addPart(root, "top_rod_south");
        topRodWest = addPart(root, "top_rod_west");
        circleNorth = addPart(root, "circle_north");
        circleEast = addPart(root, "circle_east");
        circleSouth = addPart(root, "circle_south");
        circleWest = addPart(root, "circle_west");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "core1", 0, 0, -1.5f, -0.5f, -1.5f, 3, 1, 3, 0, 2, 0);
        addCube(pd, "core2", 0, 4, -3, -0.5f, -3, 6, 1, 6, 0, 3, 0);
        addCube(pd, "core3", 0, 11, -4, -1, -4, 8, 2, 8, 0, 4.5f, 0);
        addCube(pd, "core4", 0, 21, -5, -1.5f, -5, 10, 3, 10, 0, 7, 0);
        addCube(pd, "core5", 40, 0, -6, -3, -6, 12, 6, 12, 0, 11.5f, 0);
        addCube(pd, "core6", 0, 34, -5, -0.5f, -5, 10, 1, 10, 0, 15, 0);
        addCube(pd, "core7", 0, 45, -4, -0.5f, -4, 8, 1, 8, 0, 16, 0);
        addCube(pd, "core8", 0, 54, -3, -0.5f, -3, 6, 1, 6, 0, 17, 0);
        addCube(pd, "x_rod", 40, 32, -8, -1, -1, 16, 2, 2, 0, 19, 0, -(float) (Math.PI / 4), 0, 0);
        addCube(pd, "z_rod", 40, 32, -8, -1, -1, 16, 2, 2, 0, 19, 0, -(float) (Math.PI / 4), -(float) (Math.PI / 2), 0);
        addCube(pd, "middle_rod_north", 40, 36, -1, -5.5f, -7.999f, 2, 11, 2, 0, 13, 0);
        addCube(pd, "middle_rod_east", 40, 36, 5.999f, -5.5f, -1, 2, 11, 2, 0, 13, 0);
        addCube(pd, "middle_rod_south", 40, 36, -1, -5.5f, 5.999f, 2, 11, 2, 0, 13, 0);
        addCube(pd, "middle_rod_west", 40, 36, -7.999f, -5.5f, -1, 2, 11, 2, 0, 13 , 0);
        addCube(pd, "top_rod_north", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -(float) (Math.PI / 8), 0, 0);
        addCube(pd, "top_rod_east", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -(float) (Math.PI / 8), (float) (Math.PI / 2), 0);
        addCube(pd, "top_rod_south", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -(float) (Math.PI / 8), (float) Math.PI, 0);
        addCube(pd, "top_rod_west", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -(float) (Math.PI / 8), -(float) (Math.PI / 2), 0);
        addCube(pd, "circle_north", 40, 18, -7, -7, 0, 14, 14, 0, 0, 12, -10);
        addCube(pd, "circle_east", 40, 18, -7, -7, 0, 14, 14, 0, -10, 12, 0, (float) (Math.PI / 2), 0, (float) (Math.PI / 2));
        addCube(pd, "circle_south", 40, 18, -7, -7, 0, 14, 14, 0, 0, 12, 10, (float) Math.PI, 0, (float) Math.PI);
        addCube(pd, "circle_west", 40, 18, -7, -7, 0, 14, 14, 0, 10, 12, 0, -(float) (Math.PI / 2), 0, (float) (Math.PI / 2));
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(LifeGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        core1.y = 2 + y;
        core2.y = 3 + y;
        core3.y = 4.5f + y;
        core4.y = 7 + y;
        core5.y = 11.5f + y;
        core6.y = 15 + y;
        core7.y = 16 + y;
        core8.y = 17 + y;
        xRod.y = 19 + y;
        zRod.y = 19 + y;
        middleRodNorth.y = 13 + y;
        middleRodEast.y = 13 + y;
        middleRodSouth.y = 13 + y;
        middleRodWest.y = 13 + y;
        topRodNorth.y = 22 + y;
        topRodEast.y = 22 + y;
        topRodSouth.y = 22 + y;
        topRodWest.y = 22 + y;
        circleNorth.y = 12 + y;
        circleEast.y = 12 + y;
        circleSouth.y = 12 + y;
        circleWest.y = 12 + y;
        float rot = (float) ((ageInTicks % 360) * Math.PI / 180);
        circleNorth.zRot = rot;
        circleEast.yRot = -rot;
        circleSouth.zRot = -rot;
        circleWest.yRot = rot;
    }
}
