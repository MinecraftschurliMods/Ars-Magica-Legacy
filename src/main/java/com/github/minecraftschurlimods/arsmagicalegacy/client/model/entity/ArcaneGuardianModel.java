package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
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

public class ArcaneGuardianModel extends AMEntityModel<ArcaneGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_guardian"), "main");
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart hoodTop;
    private final ModelPart hoodRight;
    private final ModelPart hoodLeft;
    private final ModelPart hoodBack;
    private final ModelPart body;
    private final ModelPart rune;
    private final ModelPart rod;
    private final ModelPart rod1;
    private final ModelPart rod2;
    private final ModelPart rod3;
    private final ModelPart rod4;
    private final ModelPart upperRightArm;
    private final ModelPart lowerRightArm;
    private final ModelPart rightHand;
    private final ModelPart wand;
    private final ModelPart upperLeftArm;
    private final ModelPart lowerLeftArm;
    private final ModelPart leftHand;
    private final ModelPart book;
    private final ModelPart robeTop;
    private final ModelPart robeFront;
    private final ModelPart rightLeg;
    private final ModelPart robeBackRight;
    private final ModelPart robeRight;
    private final ModelPart leftLeg;
    private final ModelPart robeBackLeft;
    private final ModelPart robeLeft;

    public ArcaneGuardianModel(ModelPart root) {
        head = addPart(root, "head");
        neck = addPart(root, "neck");
        hoodTop = addPart(root, "hood_top");
        hoodRight = addPart(root, "hood_right");
        hoodLeft = addPart(root, "hood_left");
        hoodBack = addPart(root, "hood_back");
        body = addPart(root, "body");
        rune = addPart(root, "rune");
        rod = addPart(root, "rod");
        rod1 = addPart(root, "rod1");
        rod2 = addPart(root, "rod2");
        rod3 = addPart(root, "rod3");
        rod4 = addPart(root, "rod4");
        upperRightArm = addPart(root, "upper_right_arm");
        lowerRightArm = addPart(root, "lower_right_arm");
        rightHand = addPart(root, "right_hand");
        wand = addPart(root, "wand");
        upperLeftArm = addPart(root, "upper_left_arm");
        lowerLeftArm = addPart(root, "lower_left_arm");
        leftHand = addPart(root, "left_hand");
        book = addPart(root, "book");
        robeTop = addPart(root, "robe_top");
        robeFront = addPart(root, "robe_front");
        rightLeg = addPart(root, "right_leg");
        robeBackRight = addPart(root, "robe_back_right");
        robeRight = addPart(root, "robe_right");
        leftLeg = addPart(root, "left_leg");
        robeBackLeft = addPart(root, "robe_back_left");
        robeLeft = addPart(root, "robe_left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 88, 0, -3, -7, -3, 7, 7, 7, 0, -4.5f, 0);
        addCube(pd, "neck", 116, 8, -0.5f, -0.5f, -0.5f, 2, 1, 2, 0, -4, 0);
        addCube(pd, "hood_top", 88, 14, -4, -8, -3, 9, 1, 8, 0, -4.5f, 0);
        addCube(pd, "hood_right", 88, 23, 4, -7, -3, 1, 8, 8, 0, -4.5f, 0);
        addCube(pd, "hood_left", 106, 23, -4, -7, -3, 1, 8, 8, 0, -4.5f, 0);
        addCube(pd, "hood_back", 88, 39, -3, -7, 4, 7, 8, 1, 0, -4.5f, 0);
        addCube(pd, "body", 104, 39, -3.5f, -2, -1.5f, 8, 4, 4, 0, -1.5f, 0);
        addCube(pd, "rune", 0, 0, -21.5f, -22, 10.5f, 44, 44, 0, 0, 2, 0);
        addCube(pd, "rod", 61, 56, -0.5f, -3, -0.5f, 2, 6, 2, 0, 2.5f, 0);
        addCube(pd, "rod1", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, 0, 0, -(float) (Math.PI / 4));
        addCube(pd, "rod2", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, (float) (7 * Math.PI / 36), (float) (Math.PI / 6), (float) (11 * Math.PI / 36));
        addCube(pd, "rod3", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, (float) (Math.PI / 2), -(float) (Math.PI / 4), -(float) (Math.PI / 2));
        addCube(pd, "rod4", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, (float) (7 * Math.PI / 36), -(float) (Math.PI / 6), -(float) (11 * Math.PI / 36));
        addCube(pd, "upper_right_arm", 112, 55, -7.5f, -2.5f, -1.5f, 4, 5, 4, 0, -1, 0);
        addCube(pd, "lower_right_arm", 80, 55, -7.5f, 2.5f, -1.5f, 4, 5, 4, 0, -1, 0);
        addCube(pd, "right_hand", 116, 11, -3, -1, -8.5f, 2, 2, 1, -3.5f, -1, 0.5f, (float) (Math.PI / 2), 0, 0);
        addCube(pd, "wand", 90, 48, -2.5f, -0.5f, -14.5f, 1, 1, 6, -3.5f, -1.5f, 0.5f, (float) (Math.PI / 2), 0, 0);
        addCube(pd, "upper_left_arm", 112, 55, 0, -2.5f, -2, 4, 5, 4, 4.5f, -1, 0.5f, -(float) (Math.PI / 4), 0, 0);
        addCube(pd, "lower_left_arm", 96, 55, -2.5f, -2.0f, -2.0f, 4, 5, 4, 6.5f, 2.5355f, -3.0355f, 0, (float) (Math.PI / 4), (float) (Math.PI / 2));
        addCube(pd, "left_hand", 122, 11, -2, 3, -2.5f, 1, 2, 2, 4.5f, 0.5f, 2, -(float) (Math.PI / 4), 0, 0);
        addCube(pd, "book", 116, 0, -1.5f, -3, -3.5f, 4, 6, 2, 0, 1.5f, 0);
        addCube(pd, "robe_top", 104, 47, -3.5f, -2, -1.5f, 8, 4, 4, 0, 6.5f, 0);
        addCube(pd, "robe_front", 22, 44, -3.5f, -4.5f, -1.5f, 8, 9, 1, 0, 13, 0);
        addCube(pd, "right_leg", 0, 44, -1, 0, -1.1f, 2, 12, 2, -1.5f, 8.5f, 1.5f, (float) (Math.PI / 18), 0, 0);
        addCube(pd, "robe_back_right", 40, 44, -2, 0.4f, -0.6f, 4, 9, 1, -1.5f, 8, 2, (float) (Math.PI / 18), 0, 0);
        addCube(pd, "robe_right", 8, 44, -7.501f, -0.6f, -1.35f, 1, 9, 3, 4, 9, 0.5f, (float) (Math.PI / 36), 0, 0);
        addCube(pd, "left_leg", 0, 44, -1, 0, -1.1f, 2, 12, 2, 2.5f, 8.5f, 0.5f, (float) (Math.PI / 18), 0, 0);
        addCube(pd, "robe_back_left", 50, 44, 2, 0.4f, -0.6f, 4, 9, 1, -1.5f, 8, 2, (float) (Math.PI / 18), 0, 0);
        addCube(pd, "robe_left", 16, 44, -0.499f, -0.6f, -0.85f, 1, 9, 2, 4, 9, 0.5f, (float) (Math.PI / 36), 0, 0);
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(ArcaneGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setHeadRotations(headPitch, netHeadYaw, head, hoodTop, hoodRight, hoodLeft, hoodBack);
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        head.y = -4.5f + y;
        neck.y = -4 + y;
        hoodTop.y = -4.5f + y;
        hoodRight.y = -4.5f + y;
        hoodLeft.y = -4.5f + y;
        hoodBack.y = -4.5f + y;
        body.y = -1.5f + y;
        rod.y = 2.5f + y;
        rod1.y = 2.5f + y;
        rod2.y = 2.5f + y;
        rod3.y = 2.5f + y;
        rod4.y = 2.5f + y;
        upperRightArm.y = -1 + y;
        lowerRightArm.y = -1 + y;
        rightHand.y = -1 + y;
        wand.y = -1.5f + y;
        upperLeftArm.y = -1 + y;
        lowerLeftArm.y = 2.5355f + y;
        leftHand.y = 0.5f + y;
        book.y = 1.5f + y;
        robeTop.y = 6.5f + y;
        robeFront.y = 13 + y;
        rightLeg.y = 8.5f + y;
        robeBackRight.y = 8 + y;
        robeRight.y = 9 + y;
        leftLeg.y = 8.5f + y;
        robeBackLeft.y = 8 + y;
        robeLeft.y = 9 + y;
        rune.zRot = (float) ((ageInTicks % 360) * Math.PI / 180);
        float swing = Mth.cos((ageInTicks % 360) * 0.1f) * 0.05f + 0.05f;
        upperRightArm.zRot = swing;
        lowerRightArm.zRot = swing;
        rightHand.zRot = swing;
        wand.zRot = swing;
    }
}
