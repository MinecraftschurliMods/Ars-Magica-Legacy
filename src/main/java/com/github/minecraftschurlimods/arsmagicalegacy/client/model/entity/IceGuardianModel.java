package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
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

public class IceGuardianModel extends AMEntityModel<IceGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ice_guardian"), "main");
    private final ModelPart rightEye;
    private final ModelPart leftEye;
    private final ModelPart topHead;
    private final ModelPart middleHead;
    private final ModelPart bottomHead;
    private final ModelPart topBody;
    private final ModelPart middleBody;
    private final ModelPart bottomBody;
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;
    private final ModelPart cube1;
    private final ModelPart cube2;
    private final ModelPart cube3;
    private final ModelPart cube4;
    private final ModelPart rod1;
    private final ModelPart rod2;
    private final ModelPart rod3;
    private final ModelPart rod4;
    private final ModelPart rightShoulder;
    private final ModelPart rightCore1;
    private final ModelPart rightCore2;
    private final ModelPart rightCore3;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart rightOuterFingers;
    private final ModelPart rightInnerFingers;
    private final ModelPart rightThumb;
    private final ModelPart leftShoulder;
    private final ModelPart leftCore1;
    private final ModelPart leftCore2;
    private final ModelPart leftCore3;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart leftOuterFingers;
    private final ModelPart leftInnerFingers;
    private final ModelPart leftThumb;

    public IceGuardianModel(ModelPart root) {
        rightEye = addPart(root, "right_eye");
        leftEye = addPart(root, "left_eye");
        topHead = addPart(root, "top_head");
        middleHead = addPart(root, "middle_head");
        bottomHead = addPart(root, "bottom_head");
        topBody = addPart(root, "top_body");
        middleBody = addPart(root, "middle_body");
        bottomBody = addPart(root, "bottom_body");
        core1 = addPart(root, "core1");
        core2 = addPart(root, "core2");
        core3 = addPart(root, "core3");
        cube1 = addPart(root, "cube1");
        cube2 = addPart(root, "cube2");
        cube3 = addPart(root, "cube3");
        cube4 = addPart(root, "cube4");
        rod1 = addPart(root, "rod1");
        rod2 = addPart(root, "rod2");
        rod3 = addPart(root, "rod3");
        rod4 = addPart(root, "rod4");
        rightShoulder = addPart(root, "right_shoulder");
        rightCore1 = addPart(root, "right_core1");
        rightCore2 = addPart(root, "right_core2");
        rightCore3 = addPart(root, "right_core3");
        rightArm = addPart(root, "right_arm");
        rightHand = addPart(root, "right_hand");
        rightOuterFingers = addPart(root, "right_outer_fingers");
        rightInnerFingers = addPart(root, "right_inner_fingers");
        rightThumb = addPart(root, "right_thumb");
        leftShoulder = addPart(root, "left_shoulder");
        leftCore1 = addPart(root, "left_core1");
        leftCore2 = addPart(root, "left_core2");
        leftCore3 = addPart(root, "left_core3");
        leftArm = addPart(root, "left_arm");
        leftHand = addPart(root, "left_hand");
        leftOuterFingers = addPart(root, "left_outer_fingers");
        leftInnerFingers = addPart(root, "left_inner_fingers");
        leftThumb = addPart(root, "left_thumb");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "right_eye", 0, 24, 2, -6, -4.5f, 2, 1, 9, 0, -15, 0);
        addCube(pd, "left_eye", 0, 24, -4, -6, -4.5f, 2, 1, 9, 0, -15, 0);
        addCube(pd, "top_head", 0, 0, -5, -10, -5, 10, 4, 10, 0, -15, 0);
        addCube(pd, "middle_head", 0, 14, -5, -6, -3, 10, 2, 8, 0, -15, 0);
        addCube(pd, "bottom_head", 0, 0, -5, -4, -5, 10, 4, 10, 0, -15, 0);
        addCube(pd, "top_body", 0, 34, -7, 2.5f, -5.5f, 14, 7, 11, 0, -9.5f, -6, (float) (Math.PI / 2), 0, 0);
        addCube(pd, "middle_body", 50, 0, -3, -3, -5, 8, 8, 10, 0, -4, 0, 0, 0, (float) (Math.PI / 4));
        addCube(pd, "bottom_body", 50, 40, -2, -6, -4, 8, 8, 8, 0, 4, 0, 0, 0, -(float) (Math.PI / 4));
        addCube(pd, "core1", 94, 32, -3, -3, -3, 6, 6, 6, 0, 15, 0);
        addCube(pd, "core2", 94, 32, -3, -3, -3, 6, 6, 6, 0, 15, 0);
        addCube(pd, "core3", 94, 32, -3, -3, -3, 6, 6, 6, 0, 15, 0);
        addCube(pd, "cube1", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0);
        addCube(pd, "cube2", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0, 0, (float) (Math.PI / 2), 0);
        addCube(pd, "cube3", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0, 0, (float) Math.PI, 0);
        addCube(pd, "cube4", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0, 0, -(float) (Math.PI / 2), 0);
        addCube(pd, "rod1", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, -(float) (Math.PI / 4), 0);
        addCube(pd, "rod2", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, (float) (Math.PI / 4), 0);
        addCube(pd, "rod3", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, (float) (3 * Math.PI / 4), 0);
        addCube(pd, "rod4", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, -(float) (3 * Math.PI / 4), 0);
        addCube(pd, "right_shoulder", 50, 18, -5, -5, -6, 10, 10, 12, -7, -8, 0, 0, 0, (float) (Math.PI / 4));
        AMEntityModel.addMirroredCube(pd, "right_core1", 22, 24, -2, -2, -2, 4, 4, 4, 13, -14.5f, 0);
        AMEntityModel.addMirroredCube(pd, "right_core2", 22, 24, -2, -2, -2, 4, 4, 4, 13, -14.5f, 0);
        AMEntityModel.addMirroredCube(pd, "right_core3", 22, 24, -2, -2, -2, 4, 4, 4, 13, -14.5f, 0);
        addCube(pd, "right_arm", 94, 0, 17, 0, -3, 6, 26, 6, 0, -10, 0);
        AMEntityModel.addMirroredCube(pd, "right_hand", 82, 44, 17, 27, -2, 6, 1, 5, 0, -10, 0);
        AMEntityModel.addMirroredCube(pd, "right_outer_fingers", 104, 44, 22, 26, -2, 1, 1, 5, 0, -10, 0);
        AMEntityModel.addMirroredCube(pd, "right_inner_fingers", 104, 44, 17, 26, -2, 1, 1, 5, 0, -10, 0);
        AMEntityModel.addMirroredCube(pd, "right_thumb", 82, 50, 17, 26, -3, 3, 2, 1, 0, -10, 0);
        addCube(pd, "left_shoulder", 50, 18, -5, -5, -6, 10, 10, 12, 7, -8, 0, 0, 0, -(float) (Math.PI / 4));
        addCube(pd, "left_core1", 22, 24, -2, -2, -2, 4, 4, 4, -13, -14.5f, 0);
        addCube(pd, "left_core2", 22, 24, -2, -2, -2, 4, 4, 4, -13, -14.5f, 0);
        addCube(pd, "left_core3", 22, 24, -2, -2, -2, 4, 4, 4, -13, -14.5f, 0);
        addCube(pd, "left_arm", 94, 0, -23, 0, -3, 6, 26, 6, 0, -10, 0);
        addCube(pd, "left_hand", 82, 44, -23, 27, -2, 6, 1, 5, 0, -10, 0);
        addCube(pd, "left_outer_fingers", 104, 44, -23, 26, -2, 1, 1, 5, 0, -10, 0);
        addCube(pd, "left_inner_fingers", 104, 44, -18, 26, -2, 1, 1, 5, 0, -10, 0);
        addCube(pd, "left_thumb", 82, 50, -20, 26, -3, 3, 2, 1, 0, -10, 0);
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(IceGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setHeadRotations(headPitch, netHeadYaw, rightEye, leftEye, topHead, middleHead, bottomHead);
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        rightEye.y = -15 + y;
        leftEye.y = -15 + y;
        topHead.y = -15 + y;
        middleHead.y = -15 + y;
        bottomHead.y = -15 + y;
        topBody.y = -9.5f + y;
        middleBody.y = -4 + y;
        bottomBody.y = 4 + y;
        core1.y = 15 + y;
        core2.y = 15 + y;
        core3.y = 15 + y;
        cube1.y = 15 + y;
        cube2.y = 15 + y;
        cube3.y = 15 + y;
        cube4.y = 15 + y;
        rod1.y = 15 + y;
        rod2.y = 15 + y;
        rod3.y = 15 + y;
        rod4.y = 15 + y;
        rightShoulder.y = -8 + y;
        rightCore1.y = -14.5f + y;
        rightCore2.y = -14.5f + y;
        rightCore3.y = -14.5f + y;
        rightArm.y = -10 + y;
        rightHand.y = -10 + y;
        rightOuterFingers.y = -10 + y;
        rightInnerFingers.y = -10 + y;
        rightThumb.y = -10 + y;
        leftShoulder.y = -8 + y;
        leftCore1.y = -14.5f + y;
        leftCore2.y = -14.5f + y;
        leftCore3.y = -14.5f + y;
        leftArm.y = -10 + y;
        leftHand.y = -10 + y;
        leftOuterFingers.y = -10 + y;
        leftInnerFingers.y = -10 + y;
        leftThumb.y = -10 + y;
        core1.xRot = ageInTicks % 360 / 6f;
        core2.yRot = (ageInTicks + 120) % 360 / 6f;
        core3.zRot = (ageInTicks + 240) % 360 / 6f;
        rightCore1.xRot = ageInTicks % 360 / 4f;
        rightCore2.yRot = (ageInTicks + 120) % 360 / 4f;
        rightCore3.zRot = (ageInTicks + 240) % 360 / 4f;
        leftCore1.xRot = ageInTicks % 360 / 4f;
        leftCore2.yRot = (ageInTicks + 120) % 360 / 4f;
        leftCore3.zRot = (ageInTicks + 240) % 360 / 4f;
        float rot = (float) ((ageInTicks % 360) * Math.PI / 180);
        cube1.yRot = rot;
        cube2.yRot = -(float) (Math.PI / 2) + rot;
        cube3.yRot = -(float) Math.PI + rot;
        cube4.yRot = (float) (Math.PI / 2) + rot;
        rod1.yRot = (float) (3 * Math.PI / 4) + rot;
        rod2.yRot = -(float) (3 * Math.PI / 4) + rot;
        rod3.yRot = -(float) (Math.PI / 4) + rot;
        rod4.yRot = (float) (Math.PI / 4) + rot;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        rightArm.zRot = -swing;
        rightHand.zRot = -swing;
        rightOuterFingers.zRot = -swing;
        rightInnerFingers.zRot = -swing;
        rightThumb.zRot = -swing;
        leftArm.zRot = swing;
        leftHand.zRot = swing;
        leftOuterFingers.zRot = swing;
        leftInnerFingers.zRot = swing;
        leftThumb.zRot = swing;
        boolean hasRight = entity.hasRightArm();
        rightArm.visible = hasRight;
        rightHand.visible = hasRight;
        rightOuterFingers.visible = hasRight;
        rightInnerFingers.visible = hasRight;
        rightThumb.visible = hasRight;
        boolean hasLeft = entity.hasLeftArm();
        leftArm.visible = hasLeft;
        leftHand.visible = hasLeft;
        leftOuterFingers.visible = hasLeft;
        leftInnerFingers.visible = hasLeft;
        leftThumb.visible = hasLeft;
    }
}
