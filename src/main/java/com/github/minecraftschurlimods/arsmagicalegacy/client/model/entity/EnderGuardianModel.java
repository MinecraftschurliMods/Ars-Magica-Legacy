package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
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

public class EnderGuardianModel extends EntityModel<EnderGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_guardian"), "main");
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart topRib;
    private final ModelPart middleRib;
    private final ModelPart bottomRib;
    private final ModelPart backbone1;
    private final ModelPart backbone2;
    private final ModelPart backbone3;
    private final ModelPart backbone4;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart rightInnerWing;
    private final ModelPart rightOuterWing;
    private final ModelPart rightWing;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart leftInnerWing;
    private final ModelPart leftOuterWing;
    private final ModelPart leftWing;

    public EnderGuardianModel(ModelPart root) {
        head = root.getChild("head");
        neck = root.getChild("neck");
        body = root.getChild("body");
        tail = root.getChild("tail");
        topRib = root.getChild("top_rib");
        middleRib = root.getChild("middle_rib");
        bottomRib = root.getChild("bottom_rib");
        backbone1 = root.getChild("backbone1");
        backbone2 = root.getChild("backbone2");
        backbone3 = root.getChild("backbone3");
        backbone4 = root.getChild("backbone4");
        rightArm = root.getChild("right_arm");
        rightHand = root.getChild("right_hand");
        rightInnerWing = root.getChild("right_inner_wing");
        rightOuterWing = root.getChild("right_outer_wing");
        rightWing = root.getChild("right_wing");
        leftArm = root.getChild("left_arm");
        leftHand = root.getChild("left_hand");
        leftInnerWing = root.getChild("left_inner_wing");
        leftOuterWing = root.getChild("left_outer_wing");
        leftWing = root.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        ModelUtil.addCube(partdefinition, "head", 0, 0, -4, -8, -4, 8, 8, 8, 0, -6, 0);
        ModelUtil.addCube(partdefinition, "neck", 0, 36, -6, -1, -1, 12, 2, 2, 0, -4.999f, 0);
        ModelUtil.addCube(partdefinition, "body", 0, 17, -2, -7, -2, 4, 14, 4, 0, 1, 0);
        ModelUtil.addCube(partdefinition, "tail", 0, 46, -1, -8, 3, 2, 16, 2, 0, 15.5f, 1, 0.5236f, 0, 0);
        ModelUtil.addCube(partdefinition, "top_rib", 0, 41, -4, -1, -1, 8, 2, 2, 0, -2, 0);
        ModelUtil.addCube(partdefinition, "middle_rib", 0, 41, -4, -1, -1, 8, 2, 2, 0, 1, 0);
        ModelUtil.addCube(partdefinition, "bottom_rib", 0, 41, -4, -1, -1, 8, 2, 2, 0, 4, 0);
        ModelUtil.addCube(partdefinition, "backbone1", 0, 93, -1, -1, 2, 2, 2, 1, 0, -4, 0);
        ModelUtil.addCube(partdefinition, "backbone2", 0, 93, -1, -1, 2, 2, 2, 1, 0, -1, 0);
        ModelUtil.addCube(partdefinition, "backbone3", 0, 93, -1, -1, 2, 2, 2, 1, 0, 2, 0);
        ModelUtil.addCube(partdefinition, "backbone4", 0, 93, -1, -1, 2, 2, 2, 1, 0, 5, 0);
        ModelUtil.addCube(partdefinition, "right_arm", 0, 65, -8, -1, -1.5f, 2, 11, 3, 0, -5, 0);
        ModelUtil.addCube(partdefinition, "right_hand", 0, 80, -8, 10, -1, 2, 10, 2, 0, -5, 0);
        ModelUtil.addCube(partdefinition, "right_inner_wing", 0, 97, 3.5f, 0, -2, 10, 2, 2, 0, -6, 0, 3.1416f, -0.3054f, 3.1416f);
        ModelUtil.addCube(partdefinition, "right_outer_wing", 0, 102, -1, -1, -1, 14, 2, 2, -11.5f, -4.5f, 5, 0, 1.5708f, 0.3491f);
        ModelUtil.addCube(partdefinition, "right_wing", 0, 107, -13.5f, 1, 0, 15, 20, 0, -11.5f, -4.5f, 5, 0, -1.5708f, 0.3491f, true);
        ModelUtil.addCube(partdefinition, "left_arm", 11, 65, 6, -1, -1.5f, 2, 11, 3, 0, -5, 0);
        ModelUtil.addCube(partdefinition, "left_hand", 9, 80, 6, 10, -1, 2, 10, 2, 0, -5, 0);
        ModelUtil.addCube(partdefinition, "left_inner_wing", 0, 97, -13.5f, 0, -2, 10, 2, 2, 0, -6, 0, 3.1416f, 0.3054f, -3.1416f);
        ModelUtil.addCube(partdefinition, "left_outer_wing", 0, 102, -13, -1, -1, 14, 2, 2, 11.5f, -4.5f, 5, 0, -1.5708f, -0.3491f);
        ModelUtil.addCube(partdefinition, "left_wing", 0, 107, -1.5f, 1, 0, 15, 20, 0, 11.5f, -4.5f, 5, 0, 1.5708f, -0.3491f);
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(EnderGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * (float) Math.PI / 180f;
        head.yRot = netHeadYaw * (float) Math.PI / 180f;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        head.y = -6 + y;
        neck.y = -4.999f + y;
        body.y = 1 + y;
        tail.y = 15.5f + y;
        topRib.y = -2 + y;
        middleRib.y = 1 + y;
        bottomRib.y = 4 + y;
        backbone1.y = -4 + y;
        backbone2.y = -1 + y;
        backbone3.y = 2 + y;
        backbone4.y = 5 + y;
        rightArm.y = -5 + y;
        rightHand.y = -5 + y;
        rightInnerWing.y = -6 + y;
        rightOuterWing.y = -4.5f + y;
        rightWing.y = -4.5f + y;
        leftArm.y = -5 + y;
        leftHand.y = -5 + y;
        leftInnerWing.y = -6 + y;
        leftOuterWing.y = -4.5f + y;
        leftWing.y = -4.5f + y;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        rightArm.zRot = swing;
        rightHand.zRot = swing;
        leftArm.zRot = -swing;
        leftHand.zRot = -swing;
        float rot = Mth.cos((float) ((entity.getWingFlapTime() % 360) * Math.PI / 180));
        rightOuterWing.yRot = -(float) (3 * Math.PI / 4) - rot;
        rightWing.yRot = (float) (Math.PI / 4) - rot;
        leftOuterWing.yRot = (float) (3 * Math.PI / 4) + rot;
        leftWing.yRot = -(float) (Math.PI / 4) + rot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        neck.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        topRib.render(poseStack, buffer, packedLight, packedOverlay);
        middleRib.render(poseStack, buffer, packedLight, packedOverlay);
        bottomRib.render(poseStack, buffer, packedLight, packedOverlay);
        backbone1.render(poseStack, buffer, packedLight, packedOverlay);
        backbone2.render(poseStack, buffer, packedLight, packedOverlay);
        backbone3.render(poseStack, buffer, packedLight, packedOverlay);
        backbone4.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        rightHand.render(poseStack, buffer, packedLight, packedOverlay);
        rightInnerWing.render(poseStack, buffer, packedLight, packedOverlay);
        rightOuterWing.render(poseStack, buffer, packedLight, packedOverlay);
        rightWing.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftHand.render(poseStack, buffer, packedLight, packedOverlay);
        leftInnerWing.render(poseStack, buffer, packedLight, packedOverlay);
        leftOuterWing.render(poseStack, buffer, packedLight, packedOverlay);
        leftWing.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
