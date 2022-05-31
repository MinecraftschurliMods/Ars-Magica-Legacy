package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
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

public class EarthGuardianModel extends EntityModel<EarthGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth_guardian"), "main");
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart body;
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;
    private final ModelPart rightShoulderPad;
    private final ModelPart rightShoulder;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart leftShoulderPad;
    private final ModelPart leftShoulder;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart rod;
    private final ModelPart rod1;
    private final ModelPart rod2;
    private final ModelPart rod3;
    private final ModelPart rod4;

    public EarthGuardianModel(ModelPart root) {
        head = root.getChild("head");
        neck = root.getChild("neck");
        body = root.getChild("body");
        core1 = root.getChild("core1");
        core2 = root.getChild("core2");
        core3 = root.getChild("core3");
        rightShoulderPad = root.getChild("right_shoulder_pad");
        rightShoulder = root.getChild("right_shoulder");
        rightArm = root.getChild("right_arm");
        rightHand = root.getChild("right_hand");
        leftShoulderPad = root.getChild("left_shoulder_pad");
        leftShoulder = root.getChild("left_shoulder");
        leftArm = root.getChild("left_arm");
        leftHand = root.getChild("left_hand");
        rod = root.getChild("rod");
        rod1 = root.getChild("rod1");
        rod2 = root.getChild("rod2");
        rod3 = root.getChild("rod3");
        rod4 = root.getChild("rod4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        ModelUtil.addCube(pd, "head", 0, 0, -4, -6, -4, 8, 6, 8, 0, -11, 0);
        ModelUtil.addCube(pd, "neck", 44, 25, -2, -1, -2, 4, 1, 4, 0, -10, 0);
        ModelUtil.addCube(pd, "body", 0, 14, -5, 0, -3, 10, 3, 6, 0, -10, 0);
        ModelUtil.addCube(pd, "core1", 32, 0, -3, -3, -3, 6, 6, 6, 0, -2, 0);
        ModelUtil.addCube(pd, "core2", 32, 0, -3, -3, -3, 6, 6, 6, 0, -2, 0);
        ModelUtil.addCube(pd, "core3", 32, 0, -3, -3, -3, 6, 6, 6, 0, -2, 0);
        ModelUtil.addCube(pd, "right_shoulder_pad", 0, 39, -15.5f, 0, -5, 6, 6, 10, 20.5f, -3, 0, 0, 0, (float) (7 * Math.PI / 36));
        ModelUtil.addCube(pd, "right_shoulder", 0, 23, -14, 0, -4, 8, 8, 8, 18.5f, -5, 0, 0, 0, (float) (7 * Math.PI / 36));
        ModelUtil.addCube(pd, "right_arm", 32, 12, 6.5f, 0, -2, 4, 8, 4, 0, -4, 0);
        ModelUtil.addCube(pd, "right_hand", 48, 12, 7, 0, -1.5f, 3, 10, 3, 0, 2, 0);
        ModelUtil.addCube(pd, "left_shoulder_pad", 0, 39, 9.5f, 0, -5, 6, 6, 10, -20.5f, -3, 0, 0, 0, -(float) (7 * Math.PI / 36));
        ModelUtil.addCube(pd, "left_shoulder", 0, 23, 6, 0, -4, 8, 8, 8, -18.5f, -5, 0, 0, 0, -(float) (7 * Math.PI / 36));
        ModelUtil.addCube(pd, "left_arm", 32, 12, -10.5f, 0, -2, 4, 8, 4, 0, -4, 0);
        ModelUtil.addCube(pd, "left_hand", 48, 12, -10, 0, -1.5f, 3, 10, 3, 0, 2, 0);
        ModelUtil.addCube(pd, "rod", 32, 24, -1, -8, -1, 2, 16, 2, 0, 12, 0);
        ModelUtil.addCube(pd, "rod1", 40, 24, 2, -5, -3, 1, 10, 1, 0, 12, 0);
        ModelUtil.addCube(pd, "rod2", 40, 24, 2, -5, 2, 1, 10, 1, 0, 12, 0);
        ModelUtil.addCube(pd, "rod3", 40, 24, -3, -5, 2, 1, 10, 1, 0, 12, 0);
        ModelUtil.addCube(pd, "rod4", 40, 24, -3, -5, -3, 1, 10, 1, 0, 12, 0);
        return LayerDefinition.create(md, 64, 64);
    }

    @Override
    public void setupAnim(EarthGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * (float) Math.PI / 180f;
        head.yRot = netHeadYaw * (float) Math.PI / 180f;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        head.y = -11 + y;
        neck.y = -10 + y;
        body.y = -10 + y;
        core1.y = -2 + y;
        core2.y = -2 + y;
        core3.y = -2 + y;
        rightShoulderPad.y = -3 + y;
        rightShoulder.y = -5 + y;
        rightArm.y = -4 + y;
        rightHand.y = 2 + y;
        leftShoulderPad.y = -3 + y;
        leftShoulder.y = -5 + y;
        leftArm.y = -4 + y;
        leftHand.y = 2 + y;
        rod.y = 12 + y;
        rod1.y = 12 + y;
        rod2.y = 12 + y;
        rod3.y = 12 + y;
        rod4.y = 12 + y;
        core1.xRot = ageInTicks % 360 / 6f;
        core2.yRot = (ageInTicks + 120) % 360 / 6f;
        core3.zRot = (ageInTicks + 240) % 360 / 6f;
        float rot = (float) ((ageInTicks % 360) * Math.PI / 180);
        rod1.yRot = rot;
        rod2.yRot = rot;
        rod3.yRot = rot;
        rod4.yRot = rot;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        leftHand.zRot = -swing;
        rightHand.zRot = swing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rightShoulderPad.render(poseStack, buffer, packedLight, packedOverlay);
        rightShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        rightHand.render(poseStack, buffer, packedLight, packedOverlay);
        leftShoulderPad.render(poseStack, buffer, packedLight, packedOverlay);
        leftShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftHand.render(poseStack, buffer, packedLight, packedOverlay);
        rod.render(poseStack, buffer, packedLight, packedOverlay);
        rod1.render(poseStack, buffer, packedLight, packedOverlay);
        rod2.render(poseStack, buffer, packedLight, packedOverlay);
        rod3.render(poseStack, buffer, packedLight, packedOverlay);
        rod4.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        neck.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        core1.render(poseStack, buffer, packedLight, packedOverlay);
        core2.render(poseStack, buffer, packedLight, packedOverlay);
        core3.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
