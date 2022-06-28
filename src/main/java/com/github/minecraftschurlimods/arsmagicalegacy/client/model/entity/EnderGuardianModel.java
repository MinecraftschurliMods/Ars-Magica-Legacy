package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
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

public class EnderGuardianModel extends AMEntityModel<EnderGuardian> {
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
        head = addPart(root, "head");
        neck = addPart(root, "neck");
        body = addPart(root, "body");
        tail = addPart(root, "tail");
        topRib = addPart(root, "top_rib");
        middleRib = addPart(root, "middle_rib");
        bottomRib = addPart(root, "bottom_rib");
        backbone1 = addPart(root, "backbone1");
        backbone2 = addPart(root, "backbone2");
        backbone3 = addPart(root, "backbone3");
        backbone4 = addPart(root, "backbone4");
        rightArm = addPart(root, "right_arm");
        rightHand = addPart(root, "right_hand");
        rightInnerWing = addPart(root, "right_inner_wing");
        rightOuterWing = addPart(root, "right_outer_wing");
        rightWing = addPart(root, "right_wing");
        leftArm = addPart(root, "left_arm");
        leftHand = addPart(root, "left_hand");
        leftInnerWing = addPart(root, "left_inner_wing");
        leftOuterWing = addPart(root, "left_outer_wing");
        leftWing = addPart(root, "left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 0, 0, -4, -8, -4, 8, 8, 8, 0, -6, 0);
        addCube(pd, "neck", 0, 16, -6, -1, -1, 12, 2, 2, 0, -4.999f, 0);
        addCube(pd, "body", 0, 24, -2, -7, -2, 4, 14, 4, 0, 1, 0);
        addCube(pd, "tail", 16, 24, -1, -8, 3, 2, 16, 2, 0, 15.5f, 1, (float) (Math.PI / 6), 0, 0);
        addCube(pd, "top_rib", 0, 20, -4, -1, -1, 8, 2, 2, 0, -2, 0);
        addCube(pd, "middle_rib", 0, 20, -4, -1, -1, 8, 2, 2, 0, 1, 0);
        addCube(pd, "bottom_rib", 0, 20, -4, -1, -1, 8, 2, 2, 0, 4, 0);
        addCube(pd, "backbone1", 20, 20, -1, -1, 2, 2, 2, 1, 0, -4, 0);
        addCube(pd, "backbone2", 20, 20, -1, -1, 2, 2, 2, 1, 0, -1, 0);
        addCube(pd, "backbone3", 20, 20, -1, -1, 2, 2, 2, 1, 0, 2, 0);
        addCube(pd, "backbone4", 20, 20, -1, -1, 2, 2, 2, 1, 0, 5, 0);
        addCube(pd, "right_arm", 32, 0, -8, -1, -1.5f, 2, 11, 3, 0, -5, 0);
        addCube(pd, "right_hand", 32, 14, -8, 10, -1, 2, 10, 2, 0, -5, 0);
        addCube(pd, "right_inner_wing", 0, 42, 3.5f, 0, -2, 10, 2, 2, 0, -6, 0, (float) Math.PI, -(float) (7 * Math.PI / 72), (float) Math.PI);
        addCube(pd, "right_outer_wing", 0, 46, -1, -1, -1, 14, 2, 2, -11.5f, -4.5f, 5, 0, (float) (Math.PI / 2), (float) (Math.PI / 9));
        AMEntityModel.addMirroredCube(pd, "right_wing", 32, 26, -13.5f, 1, 0, 15, 20, 0, -11.5f, -4.5f, 5, 0, -(float) (Math.PI / 2), (float) (Math.PI / 9));
        addCube(pd, "left_arm", 42, 0, 6, -1, -1.5f, 2, 11, 3, 0, -5, 0);
        addCube(pd, "left_hand", 40, 14, 6, 10, -1, 2, 10, 2, 0, -5, 0);
        addCube(pd, "left_inner_wing", 0, 42, -13.5f, 0, -2, 10, 2, 2, 0, -6, 0, (float) Math.PI, (float) (7 * Math.PI / 72), -(float) Math.PI);
        addCube(pd, "left_outer_wing", 0, 46, -13, -1, -1, 14, 2, 2, 11.5f, -4.5f, 5, 0, -(float) (Math.PI / 2), -(float) (Math.PI / 9));
        addCube(pd, "left_wing", 32, 26, -1.5f, 1, 0, 15, 20, 0, 11.5f, -4.5f, 5, 0, (float) (Math.PI / 2), -(float) (Math.PI / 9));
        return LayerDefinition.create(md, 64, 64);
    }

    @Override
    public void setupAnim(EnderGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setHeadRotations(headPitch, netHeadYaw, head);
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
}
