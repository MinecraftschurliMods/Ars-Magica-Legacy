package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LifeGuardianModel extends EntityModel<LifeGuardian> {
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
        core1 = root.getChild("core1");
        core2 = root.getChild("core2");
        core3 = root.getChild("core3");
        core4 = root.getChild("core4");
        core5 = root.getChild("core5");
        core6 = root.getChild("core6");
        core7 = root.getChild("core7");
        core8 = root.getChild("core8");
        xRod = root.getChild("x_rod");
        zRod = root.getChild("z_rod");
        middleRodNorth = root.getChild("middle_rod_north");
        middleRodEast = root.getChild("middle_rod_east");
        middleRodSouth = root.getChild("middle_rod_south");
        middleRodWest = root.getChild("middle_rod_west");
        topRodNorth = root.getChild("top_rod_north");
        topRodEast = root.getChild("top_rod_east");
        topRodSouth = root.getChild("top_rod_south");
        topRodWest = root.getChild("top_rod_west");
        circleNorth = root.getChild("circle_north");
        circleEast = root.getChild("circle_east");
        circleSouth = root.getChild("circle_south");
        circleWest = root.getChild("circle_west");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        ModelUtil.addCube(partdefinition, "core1", 0, 0, -1.5f, -0.5f, -1.5f, 3, 1, 3, 0, 2, 0);
        ModelUtil.addCube(partdefinition, "core2", 0, 5, -3, -0.5f, -3, 6, 1, 6, 0, 3, 0);
        ModelUtil.addCube(partdefinition, "core3", 0, 13, -4, -1, -4, 8, 2, 8, 0, 4.5f, 0);
        ModelUtil.addCube(partdefinition, "core4", 0, 24, -5, -1.5f, -5, 10, 3, 10, 0, 7, 0);
        ModelUtil.addCube(partdefinition, "core5", 0, 38, -6, -3, -6, 12, 6, 12, 0, 11.5f, 0);
        ModelUtil.addCube(partdefinition, "core6", 0, 57, -5, -0.5f, -5, 10, 1, 10, 0, 15, 0);
        ModelUtil.addCube(partdefinition, "core7", 0, 69, -4, -0.5f, -4, 8, 1, 8, 0, 16, 0);
        ModelUtil.addCube(partdefinition, "core8", 0, 79, -3, -0.5f, -3, 6, 1, 6, 0, 17, 0);
        ModelUtil.addCube(partdefinition, "x_rod", 0, 104, -8, -1, -1, 16, 2, 2, 0, 19, 0, -0.7854f, 0, 0);
        ModelUtil.addCube(partdefinition, "z_rod", 0, 104, -8, -1, -1, 16, 2, 2, 0, 19, 0, -0.7854f, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "middle_rod_north", 0, 109, -1, -5.5f, -7.999f, 2, 11, 2, 0, 13, 0);
        ModelUtil.addCube(partdefinition, "middle_rod_east", 0, 109, 5.999f, -5.5f, -1, 2, 11, 2, 0, 13, 0);
        ModelUtil.addCube(partdefinition, "middle_rod_south", 0, 109, -1, -5.5f, 5.999f, 2, 11, 2, 0, 13, 0);
        ModelUtil.addCube(partdefinition, "middle_rod_west", 0, 109, -7.999f, -5.5f, -1, 2, 11, 2, 0, 13 , 0);
        ModelUtil.addCube(partdefinition, "top_rod_north", 36, 114, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -0.3927f, 0, 0);
        ModelUtil.addCube(partdefinition, "top_rod_east", 36, 114, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -0.3927f, 1.5708f, 0);
        ModelUtil.addCube(partdefinition, "top_rod_south", 36, 114, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -0.3927f, 3.1416f, 0, true);
        ModelUtil.addCube(partdefinition, "top_rod_west", 36, 114, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -0.3927f, -1.5708f, 0, true);
        ModelUtil.addCube(partdefinition, "circle_north", 41, 114, -7, -7, 0, 14, 14, 0, 0, 12, -10);
        ModelUtil.addCube(partdefinition, "circle_east", 41, 114, -7, -7, 0, 14, 14, 0, -10, 12, 0, 1.5708f, 0, 1.5708f);
        ModelUtil.addCube(partdefinition, "circle_south", 41, 114, -7, -7, 0, 14, 14, 0, 0, 12, 10, 3.1416f, 0, 3.1416f);
        ModelUtil.addCube(partdefinition, "circle_west", 41, 114, -7, -7, 0, 14, 14, 0, 10, 12, 0, -1.5708f, 0, 1.5708f);
        return LayerDefinition.create(meshdefinition, 128, 128);
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

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        core1.render(poseStack, buffer, packedLight, packedOverlay);
        core2.render(poseStack, buffer, packedLight, packedOverlay);
        core3.render(poseStack, buffer, packedLight, packedOverlay);
        core4.render(poseStack, buffer, packedLight, packedOverlay);
        core5.render(poseStack, buffer, packedLight, packedOverlay);
        core6.render(poseStack, buffer, packedLight, packedOverlay);
        core7.render(poseStack, buffer, packedLight, packedOverlay);
        core8.render(poseStack, buffer, packedLight, packedOverlay);
        xRod.render(poseStack, buffer, packedLight, packedOverlay);
        zRod.render(poseStack, buffer, packedLight, packedOverlay);
        middleRodNorth.render(poseStack, buffer, packedLight, packedOverlay);
        middleRodEast.render(poseStack, buffer, packedLight, packedOverlay);
        middleRodSouth.render(poseStack, buffer, packedLight, packedOverlay);
        middleRodWest.render(poseStack, buffer, packedLight, packedOverlay);
        topRodNorth.render(poseStack, buffer, packedLight, packedOverlay);
        topRodEast.render(poseStack, buffer, packedLight, packedOverlay);
        topRodSouth.render(poseStack, buffer, packedLight, packedOverlay);
        topRodWest.render(poseStack, buffer, packedLight, packedOverlay);
        circleNorth.render(poseStack, buffer, packedLight, packedOverlay);
        circleEast.render(poseStack, buffer, packedLight, packedOverlay);
        circleSouth.render(poseStack, buffer, packedLight, packedOverlay);
        circleWest.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
