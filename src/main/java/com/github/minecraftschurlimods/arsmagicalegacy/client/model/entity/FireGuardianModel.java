package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
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

public class FireGuardianModel extends EntityModel<FireGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire_guardian"), "main");
    private final ModelPart noseFront;
    private final ModelPart noseBack;
    private final ModelPart head;
    private final ModelPart rightEarFront;
    private final ModelPart rightEarBack;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart leftEarFront;
    private final ModelPart leftEarBack;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart neck3;
    private final ModelPart neck4;
    private final ModelPart neck5;
    private final ModelPart neck6;
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart body3;
    private final ModelPart body4;
    private final ModelPart body5;
    private final ModelPart body6;
    private final ModelPart body7;
    private final ModelPart body8;
    private final ModelPart body9;

    public FireGuardianModel(ModelPart root) {
        noseFront = root.getChild("nose_front");
        noseBack = root.getChild("nose_back");
        head = root.getChild("head");
        rightEarFront = root.getChild("right_ear_front");
        rightEarBack = root.getChild("right_ear_back");
        rightArm = root.getChild("right_arm");
        rightHand = root.getChild("right_hand");
        leftEarFront = root.getChild("left_ear_front");
        leftEarBack = root.getChild("left_ear_back");
        leftArm = root.getChild("left_arm");
        leftHand = root.getChild("left_hand");
        neck1 = root.getChild("neck1");
        neck2 = root.getChild("neck2");
        neck3 = root.getChild("neck3");
        neck4 = root.getChild("neck4");
        neck5 = root.getChild("neck5");
        neck6 = root.getChild("neck6");
        body1 = root.getChild("body1");
        body2 = root.getChild("body2");
        body3 = root.getChild("body3");
        body4 = root.getChild("body4");
        body5 = root.getChild("body5");
        body6 = root.getChild("body6");
        body7 = root.getChild("body7");
        body8 = root.getChild("body8");
        body9 = root.getChild("body9");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        ModelUtil.addCube(partdefinition, "nose_front", 63, 29, -2.5f, -3.5f, -14, 5, 3, 2, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "nose_back", 14, 16, -3.5f, -4, -12, 7, 4, 3, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "head", 63, 43, -4, -5, -9, 8, 5, 8, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "right_ear_front", 63, 65, 4, -3.5f, -6, 2, 3, 6, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "right_ear_back", 63, 57, 4.5f, -3, 0, 1, 2, 5, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "right_arm", 33, 110, 8, -1, 1, 2, 16, 2, 0, -13, 0);
        ModelUtil.addCube(partdefinition, "right_hand", 42, 108, 6, 15, 0, 6, 16, 4, 0, -13, 0);
        ModelUtil.addCube(partdefinition, "left_ear_front", 63, 65, -6, -3.5f, -6, 2, 3, 6, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "left_ear_back", 63, 57, -5.5f, -3, 0, 1, 2, 5, 0, -18, 0);
        ModelUtil.addCube(partdefinition, "left_arm", 33, 110, -10, -1, 1, 2, 16, 2, 0, -13, 0, true);
        ModelUtil.addCube(partdefinition, "left_hand", 42, 108, -12, 15, 0, 6, 16, 4, 0, -13, 0, true);
        ModelUtil.addCube(partdefinition, "neck1", 83, 125, -1, -33, -15f, 2, 1, 2, 0, 0, 30, 0.6545f, 0, 0);
        ModelUtil.addCube(partdefinition, "neck2", 74, 123, -2, -32.25f, -16.25f, 4, 1, 4, 0, 0, 30, 0.6545f, 0, 0);
        ModelUtil.addCube(partdefinition, "neck3", 75, 121, -3, -31.5f, -17, 6, 1, 6, 0, 0, 30, 0.6545f, 0, 0);
        ModelUtil.addCube(partdefinition, "neck4", 71, 119, -4, -30.75f, -18.5f, 8, 1, 8, 0, 0, 30, 0.637f, 0, 0);
        ModelUtil.addCube(partdefinition, "neck5", 67, 117, -5, -29.5f, -21, 10, 1, 10, 0, 0, 30, 0.5934f, 0, 0);
        ModelUtil.addCube(partdefinition, "neck6", 63, 115, -6, -27.75f, -24, 12, 1, 12, 0, 0, 30, 0.5236f, 0, 0);
        ModelUtil.addCube(partdefinition, "body1", 0, 3, -7, -26.25f, -25.75f, 14, 6, 14, 0, 0, 30, 0.48f, 0, 0);
        ModelUtil.addCube(partdefinition, "body2", 3, 6, -6.5f, -18.75f, -27.25f, 13, 5, 12, 0, 0, 30, 0.3054f, 0, 0);
        ModelUtil.addCube(partdefinition, "body3", 8, 10, -5.5f, -14, -26.75f, 11, 3, 10, 0, 0, 30, 0.2618f, 0, 0);
        ModelUtil.addCube(partdefinition, "body4", 11, 12, -4.5f, -11.25f, -26.5f, 9, 3, 8, 0, 0, 30, 0.2182f, 0, 0);
        ModelUtil.addCube(partdefinition, "body5", 15, 14, -3.5f, -7.5f, -26, 7, 3, 6, 0, 0, 30, 0.1309f, 0, 0);
        ModelUtil.addCube(partdefinition, "body6", 18, 15, -2.5f, -2.5f, -25, 5, 4, 4, 0, 0, 30);
        ModelUtil.addCube(partdefinition, "body7", 20, 15, -2, 3.25f, -24, 4, 5, 3, 0, 0, 30, -0.0873f, 0, 0);
        ModelUtil.addCube(partdefinition, "body8", 23, 14, -1, 9.25f, -23, 2, 7, 2, 0, 0, 30, -0.1309f, 0, 0);
        ModelUtil.addCube(partdefinition, "body9", 24, 15, -0.5f, 15.25f, -22.75f, 1, 7, 1, 0, 0, 30, -0.1309f, 0, 0);
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(FireGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        noseFront.xRot = headPitch * (float) Math.PI / 180f;
        noseFront.yRot = netHeadYaw * (float) Math.PI / 180f;
        noseBack.xRot = headPitch * (float) Math.PI / 180f;
        noseBack.yRot = netHeadYaw * (float) Math.PI / 180f;
        head.xRot = headPitch * (float) Math.PI / 180f;
        head.yRot = netHeadYaw * (float) Math.PI / 180f;
        rightEarFront.xRot = headPitch * (float) Math.PI / 180f;
        rightEarFront.yRot = netHeadYaw * (float) Math.PI / 180f;
        rightEarBack.xRot = headPitch * (float) Math.PI / 180f;
        rightEarBack.yRot = netHeadYaw * (float) Math.PI / 180f;
        leftEarFront.xRot = headPitch * (float) Math.PI / 180f;
        leftEarFront.yRot = netHeadYaw * (float) Math.PI / 180f;
        leftEarBack.xRot = headPitch * (float) Math.PI / 180f;
        leftEarBack.yRot = netHeadYaw * (float) Math.PI / 180f;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        noseFront.y = -18 + y;
        noseBack.y = -18 + y;
        head.y = -18 + y;
        rightEarFront.y = -18 + y;
        rightEarBack.y = -18 + y;
        rightArm.y = -13 + y;
        rightHand.y = -13 + y;
        leftEarFront.y = -18 + y;
        leftEarBack.y = -18 + y;
        leftArm.y = -13 + y;
        leftHand.y = -13 + y;
        neck1.y = y;
        neck2.y = y;
        neck3.y = y;
        neck4.y = y;
        neck5.y = y;
        neck6.y = y;
        body1.y = y;
        body2.y = y;
        body3.y = y;
        body4.y = y;
        body5.y = y;
        body6.y = y;
        body7.y = y;
        body8.y = y;
        body9.y = y;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        rightArm.zRot = -swing;
        rightHand.zRot = -swing;
        leftArm.zRot = swing;
        leftHand.zRot = swing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        noseFront.render(poseStack, buffer, packedLight, packedOverlay);
        noseBack.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        rightEarFront.render(poseStack, buffer, packedLight, packedOverlay);
        rightEarBack.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        rightHand.render(poseStack, buffer, packedLight, packedOverlay);
        leftEarFront.render(poseStack, buffer, packedLight, packedOverlay);
        leftEarBack.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftHand.render(poseStack, buffer, packedLight, packedOverlay);
        neck1.render(poseStack, buffer, packedLight, packedOverlay);
        neck2.render(poseStack, buffer, packedLight, packedOverlay);
        neck3.render(poseStack, buffer, packedLight, packedOverlay);
        neck4.render(poseStack, buffer, packedLight, packedOverlay);
        neck5.render(poseStack, buffer, packedLight, packedOverlay);
        neck6.render(poseStack, buffer, packedLight, packedOverlay);
        body1.render(poseStack, buffer, packedLight, packedOverlay);
        body2.render(poseStack, buffer, packedLight, packedOverlay);
        body3.render(poseStack, buffer, packedLight, packedOverlay);
        body4.render(poseStack, buffer, packedLight, packedOverlay);
        body5.render(poseStack, buffer, packedLight, packedOverlay);
        body6.render(poseStack, buffer, packedLight, packedOverlay);
        body7.render(poseStack, buffer, packedLight, packedOverlay);
        body8.render(poseStack, buffer, packedLight, packedOverlay);
        body9.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
