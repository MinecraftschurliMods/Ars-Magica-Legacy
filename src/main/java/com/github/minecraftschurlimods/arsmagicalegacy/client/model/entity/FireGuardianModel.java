package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
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

public class FireGuardianModel extends AMEntityModel<FireGuardian> {
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
        noseFront = addPart(root, "nose_front");
        noseBack = addPart(root, "nose_back");
        head = addPart(root, "head");
        rightEarFront = addPart(root, "right_ear_front");
        rightEarBack = addPart(root, "right_ear_back");
        rightArm = addPart(root, "right_arm");
        rightHand = addPart(root, "right_hand");
        leftEarFront = addPart(root, "left_ear_front");
        leftEarBack = addPart(root, "left_ear_back");
        leftArm = addPart(root, "left_arm");
        leftHand = addPart(root, "left_hand");
        neck1 = addPart(root, "neck1");
        neck2 = addPart(root, "neck2");
        neck3 = addPart(root, "neck3");
        neck4 = addPart(root, "neck4");
        neck5 = addPart(root, "neck5");
        neck6 = addPart(root, "neck6");
        body1 = addPart(root, "body1");
        body2 = addPart(root, "body2");
        body3 = addPart(root, "body3");
        body4 = addPart(root, "body4");
        body5 = addPart(root, "body5");
        body6 = addPart(root, "body6");
        body7 = addPart(root, "body7");
        body8 = addPart(root, "body8");
        body9 = addPart(root, "body9");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "nose_front", 48, 20, -2.5f, -3.5f, -14, 5, 3, 2, 0, -18, 0);
        addCube(pd, "nose_back", 0, 46, -3.5f, -4, -12, 7, 4, 3, 0, -18, 0);
        addCube(pd, "head", 20, 34, -4, -5, -9, 8, 5, 8, 0, -18, 0);
        addCube(pd, "right_ear_front", 48, 25, 4, -3.5f, -6, 2, 3, 6, 0, -18, 0);
        addCube(pd, "right_ear_back", 52, 34, 4.5f, -3, 0, 1, 2, 5, 0, -18, 0);
        addCube(pd, "right_arm", 56, 2, 8, -1, 1, 2, 16, 2, 0, -13, 0);
        addCube(pd, "right_hand", 0, 33, 6, 15, 0, 6, 16, 4, 0, -13, 0);
        addCube(pd, "left_ear_front", 48, 25, -6, -3.5f, -6, 2, 3, 6, 0, -18, 0);
        addCube(pd, "left_ear_back", 52, 34, -5.5f, -3, 0, 1, 2, 5, 0, -18, 0);
        addCube(pd, "left_arm", 56, 2, -10, -1, 1, 2, 16, 2, 0, -13, 0);
        addCube(pd, "left_hand", 0, 33, -12, 15, 0, 6, 16, 4, 0, -13, 0);
        addCube(pd, "neck1", 20, 30, -1, -33, -15, 2, 1, 2, 0, 0, 30, (float) (5 * Math.PI / 24), 0, 0);
        addCube(pd, "neck2", 16, 28, -2, -32.25f, -16.25f, 4, 1, 4, 0, 0, 30, (float) (5 * Math.PI / 24), 0, 0);
        addCube(pd, "neck3", 12, 26, -3, -31.5f, -17, 6, 1, 6, 0, 0, 30, (float) (5 * Math.PI / 24), 0, 0);
        addCube(pd, "neck4", 8, 24, -4, -30.75f, -18.5f, 8, 1, 8, 0, 0, 30, (float) (73 * Math.PI / 360), 0, 0);
        addCube(pd, "neck5", 4, 22, -5, -29.5f, -21, 10, 1, 10, 0, 0, 30, (float) (17 * Math.PI / 90), 0, 0);
        addCube(pd, "neck6", 0, 20, -6, -27.75f, -24, 12, 1, 12, 0, 0, 30, (float) (Math.PI / 6), 0, 0);
        addCube(pd, "body1", 0, 0, -7, -26.25f, -25.75f, 14, 6, 14, 0, 0, 30, (float) (11 * Math.PI / 72), 0, 0);
        addCube(pd, "body2", 3, 3, -6.5f, -18.75f, -27.25f, 13, 5, 12, 0, 0, 30, (float) (7 * Math.PI / 72), 0, 0);
        addCube(pd, "body3", 7, 7, -5.5f, -14, -26.75f, 11, 3, 10, 0, 0, 30, (float) (Math.PI / 12), 0, 0);
        addCube(pd, "body4", 11, 9, -4.5f, -11.25f, -26.5f, 9, 3, 8, 0, 0, 30, (float) (5 * Math.PI / 72), 0, 0);
        addCube(pd, "body5", 15, 11, -3.5f, -7.5f, -26, 7, 3, 6, 0, 0, 30, (float) (Math.PI / 24), 0, 0);
        addCube(pd, "body6", 19, 12, -2.5f, -2.5f, -25, 5, 4, 4, 0, 0, 30);
        addCube(pd, "body7", 21, 12, -2, 3.25f, -24, 4, 5, 3, 0, 0, 30, -(float) (Math.PI / 36), 0, 0);
        addCube(pd, "body8", 24, 11, -1, 9.25f, -23, 2, 7, 2, 0, 0, 30, -(float) (Math.PI / 24), 0, 0);
        addCube(pd, "body9", 26, 12, -0.5f, 15.25f, -22.75f, 1, 7, 1, 0, 0, 30, -(float) (Math.PI / 24), 0, 0);
        return LayerDefinition.create(md, 64, 64);
    }

    @Override
    public void setupAnim(FireGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setHeadRotations(headPitch, netHeadYaw, noseFront, noseBack, head, rightEarFront, rightEarBack, leftEarFront, leftEarBack);
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
}
