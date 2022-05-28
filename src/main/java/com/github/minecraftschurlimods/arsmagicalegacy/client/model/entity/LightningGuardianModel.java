package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
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

public class LightningGuardianModel extends EntityModel<LightningGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_guardian"), "main");
    private final ModelPart head;
    private final ModelPart chest;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart shoulder;
    private final ModelPart chestFront;
    private final ModelPart topChestBack;
    private final ModelPart bottomChestBack;
    private final ModelPart topRightChest;
    private final ModelPart middleRightChest;
    private final ModelPart bottomRightChest;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart topRightHandFront;
    private final ModelPart topRightHandInside;
    private final ModelPart topRightHandBack;
    private final ModelPart bottomRightHandFront;
    private final ModelPart bottomRightHandInside;
    private final ModelPart bottomRightHandBack;
    private final ModelPart topLeftChest;
    private final ModelPart middleLeftChest;
    private final ModelPart bottomLeftChest;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart topLeftHandFront;
    private final ModelPart topLeftHandInside;
    private final ModelPart topLeftHandBack;
    private final ModelPart bottomLeftHandFront;
    private final ModelPart bottomLeftHandInside;
    private final ModelPart bottomLeftHandBack;

    public LightningGuardianModel(ModelPart root) {
        head = root.getChild("head");
        chest = root.getChild("chest");
        body = root.getChild("body");
        tail = root.getChild("tail");
        shoulder = root.getChild("shoulder");
        chestFront = root.getChild("chest_front");
        topChestBack = root.getChild("top_chest_back");
        bottomChestBack = root.getChild("bottom_chest_back");
        topRightChest = root.getChild("top_right_chest");
        middleRightChest = root.getChild("middle_right_chest");
        bottomRightChest = root.getChild("bottom_right_chest");
        rightArm = root.getChild("right_arm");
        rightHand = root.getChild("right_hand");
        topRightHandFront = root.getChild("top_right_hand_front");
        topRightHandInside = root.getChild("top_right_hand_inside");
        topRightHandBack = root.getChild("top_right_hand_back");
        bottomRightHandFront = root.getChild("bottom_right_hand_front");
        bottomRightHandInside = root.getChild("bottom_right_hand_inside");
        bottomRightHandBack = root.getChild("bottom_right_hand_back");
        topLeftChest = root.getChild("top_left_chest");
        middleLeftChest = root.getChild("middle_left_chest");
        bottomLeftChest = root.getChild("bottom_left_chest");
        leftArm = root.getChild("left_arm");
        leftHand = root.getChild("left_hand");
        topLeftHandFront = root.getChild("top_left_hand_front");
        topLeftHandInside = root.getChild("top_left_hand_inside");
        topLeftHandBack = root.getChild("top_left_hand_back");
        bottomLeftHandFront = root.getChild("bottom_left_hand_front");
        bottomLeftHandInside = root.getChild("bottom_left_hand_inside");
        bottomLeftHandBack = root.getChild("bottom_left_hand_back");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        ModelUtil.addCube(partdefinition, "head", 0, 20, -1.5f, -3, -1.5f, 3, 3, 3, 0, 7, 0);
        ModelUtil.addCube(partdefinition, "chest", 0, 26, -3.5f, -2, -2.5f, 7, 4, 5, 0, 9, 0);
        ModelUtil.addCube(partdefinition, "body", 12, 20, -2, -2, -1, 4, 4, 2, 0, 13, 0, true);
        ModelUtil.addCube(partdefinition, "tail", 0, 39, -1, -3, -0.5f, 2, 6, 1, 0, 18, 0);
        ModelUtil.addCube(partdefinition, "shoulder", 0, 35, -4, -1, -1, 8, 2, 2, 0, 7.999f, 0);
        ModelUtil.addCube(partdefinition, "chest_front", 30, 0, -3.5f, -2.5f, 2.501f, 7, 5, 0, 0, 9.5f, 0);
        ModelUtil.addCube(partdefinition, "top_chest_back", 15, 0, -2.5f, -1, -2.5f, 5, 2, 0, 0, 4, 0);
        ModelUtil.addCube(partdefinition, "bottom_chest_back", 15, 3, -3.5f, -3.5f, -2.501f, 7, 7, 0, 0, 8.5f, 0);
        ModelUtil.addCube(partdefinition, "top_right_chest", 74, 3, -1.5f, -0.5f, 0, 3, 1, 0, 3.5f, 5.5f, -1, 0, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "middle_right_chest", 56, 5, -2.5f, -0.5f, 0, 5, 1, 0, 3.5f, 6.5f, 0, 0, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "bottom_right_chest", 0, 14, -2.5f, -1.5f, 0, 5, 3, 0, 3.501f, 10.5f, 0, 0, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "right_arm", 0, 46, 0, -1, -1, 2, 13, 2, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "right_hand", 0, 0, 2, 7, -1, 1, 4, 2, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_right_hand_front", 80, 12, -1, 7, 1, 4, 1, 1, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_right_hand_inside", 36, 16, -1, 7, -1, 1, 1, 2, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_right_hand_back", 56, 12, -1, 7, -2, 4, 1, 1, 4, 8, 0, true);
        ModelUtil.addCube(partdefinition, "bottom_right_hand_front", 92, 12, -1, 10, 1, 4, 1, 1, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "bottom_right_hand_inside", 20, 16, -1, 10, -1, 1, 1, 2, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "bottom_right_hand_back", 32, 12, -1, 10, -2, 4, 1, 1, 4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_left_chest", 67, 3, -1.5f, -0.5f, 0, 3, 1, 0, -3.5f, 5.5f, -1, 0, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "middle_left_chest", 45, 5, -2.5f, -0.5f, 0, 5, 1, 0, -3.5f, 6.5f, 0, 0, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "bottom_left_chest", 0, 14, -2.5f, -1.5f, 0, 5, 3, 0, -3.501f, 10.5f, 0, 0, -1.5708f, 0);
        ModelUtil.addCube(partdefinition, "left_arm", 8, 46, -2, -1, -1, 2, 13, 2, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "left_hand", 7, 0, -3, 7, -1, 1, 4, 2, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_left_hand_front", 68, 12, -3, 7, 1, 4, 1, 1, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_left_hand_inside", 28, 16, 0, 7, -1, 1, 1, 2, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "top_left_hand_back", 20, 12, -3, 7, -2, 4, 1, 1, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "bottom_left_hand_front", 104, 12, -3, 10, 1, 4, 1, 1, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "bottom_left_hand_inside", 44, 16, 0, 10, -1, 1, 1, 2, -4, 8, 0);
        ModelUtil.addCube(partdefinition, "bottom_left_hand_back", 44, 12, -3, 10, -2, 4, 1, 1, -4, 8, 0, true);
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(LightningGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * (float) Math.PI / 180f;
        head.yRot = netHeadYaw * (float) Math.PI / 180f;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        head.y = 7 + y;
        chest.y = 9 + y;
        body.y = 13 + y;
        tail.y = 18 + y;
        shoulder.y = 7.999f + y;
        chestFront.y = 9.5f + y;
        topChestBack.y = 4 + y;
        bottomChestBack.y = 8.5f + y;
        topRightChest.y = 5.5f + y;
        middleRightChest.y = 6.5f + y;
        bottomRightChest.y = 10.5f + y;
        rightArm.y = 8 + y;
        rightHand.y = 8 + y;
        topRightHandFront.y = 8 + y;
        topRightHandInside.y = 8 + y;
        topRightHandBack.y = 8 + y;
        bottomRightHandFront.y = 8 + y;
        bottomRightHandInside.y = 8 + y;
        bottomRightHandBack.y = 8 + y;
        topLeftChest.y = 5.5f + y;
        middleLeftChest.y = 6.5f + y;
        bottomLeftChest.y = 10.5f + y;
        leftArm.y = 8 + y;
        leftHand.y = 8 + y;
        topLeftHandFront.y = 8 + y;
        topLeftHandInside.y = 8 + y;
        topLeftHandBack.y = 8 + y;
        bottomLeftHandFront.y = 8 + y;
        bottomLeftHandInside.y = 8 + y;
        bottomLeftHandBack.y = 8 + y;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        rightArm.zRot = -swing;
        rightHand.zRot = -swing;
        topRightHandFront.zRot = -swing;
        topRightHandInside.zRot = -swing;
        topRightHandBack.zRot = -swing;
        bottomRightHandFront.zRot = -swing;
        bottomRightHandInside.zRot = -swing;
        bottomRightHandBack.zRot = -swing;
        leftArm.zRot = swing;
        leftHand.zRot = swing;
        topLeftHandFront.zRot = swing;
        topLeftHandInside.zRot = swing;
        topLeftHandBack.zRot = swing;
        bottomLeftHandFront.zRot = swing;
        bottomLeftHandInside.zRot = swing;
        bottomLeftHandBack.zRot = swing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        chest.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        shoulder.render(poseStack, buffer, packedLight, packedOverlay);
        chestFront.render(poseStack, buffer, packedLight, packedOverlay);
        topChestBack.render(poseStack, buffer, packedLight, packedOverlay);
        bottomChestBack.render(poseStack, buffer, packedLight, packedOverlay);
        topRightChest.render(poseStack, buffer, packedLight, packedOverlay);
        middleRightChest.render(poseStack, buffer, packedLight, packedOverlay);
        bottomRightChest.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        rightHand.render(poseStack, buffer, packedLight, packedOverlay);
        topRightHandFront.render(poseStack, buffer, packedLight, packedOverlay);
        topRightHandInside.render(poseStack, buffer, packedLight, packedOverlay);
        topRightHandBack.render(poseStack, buffer, packedLight, packedOverlay);
        bottomRightHandFront.render(poseStack, buffer, packedLight, packedOverlay);
        bottomRightHandInside.render(poseStack, buffer, packedLight, packedOverlay);
        bottomRightHandBack.render(poseStack, buffer, packedLight, packedOverlay);
        topLeftChest.render(poseStack, buffer, packedLight, packedOverlay);
        middleLeftChest.render(poseStack, buffer, packedLight, packedOverlay);
        bottomLeftChest.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftHand.render(poseStack, buffer, packedLight, packedOverlay);
        topLeftHandFront.render(poseStack, buffer, packedLight, packedOverlay);
        topLeftHandInside.render(poseStack, buffer, packedLight, packedOverlay);
        topLeftHandBack.render(poseStack, buffer, packedLight, packedOverlay);
        bottomLeftHandFront.render(poseStack, buffer, packedLight, packedOverlay);
        bottomLeftHandInside.render(poseStack, buffer, packedLight, packedOverlay);
        bottomLeftHandBack.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
