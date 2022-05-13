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
    private final ModelPart leftShoulderPad;
    private final ModelPart leftShoulder;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart rightShoulderPad;
    private final ModelPart rightShoulder;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart rod1;
    private final ModelPart rod2;
    private final ModelPart rod3;
    private final ModelPart rod4;
    private final ModelPart centralRod;
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart body;
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;

    public EarthGuardianModel(ModelPart root) {
        leftShoulderPad = root.getChild("left_shoulder_pad");
        leftShoulder = root.getChild("left_shoulder");
        leftArm = root.getChild("left_arm");
        leftHand = root.getChild("left_hand");
        rightShoulderPad = root.getChild("right_shoulder_pad");
        rightShoulder = root.getChild("right_shoulder");
        rightArm = root.getChild("right_arm");
        rightHand = root.getChild("right_hand");
        rod1 = root.getChild("rod1");
        rod2 = root.getChild("rod2");
        rod3 = root.getChild("rod3");
        rod4 = root.getChild("rod4");
        centralRod = root.getChild("central_rod");
        head = root.getChild("head");
        neck = root.getChild("neck");
        body = root.getChild("body");
        core1 = root.getChild("core1");
        core2 = root.getChild("core2");
        core3 = root.getChild("core3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        ModelUtil.addCube(partdefinition, "left_shoulder_pad", 0, 48, 9.5F, 0F, -5F, 6F, 6F, 10F, -20.5F, -3F, 0F, 0F, 0F, -0.6109F);
        ModelUtil.addCube(partdefinition, "left_shoulder", 0, 31, 6F, 0F, -4F, 8F, 8F, 8F, -18.5F, -5F, 0F, 0F, 0F, -0.6109F);
        ModelUtil.addCube(partdefinition, "left_arm", 33, 18, -10.5F, 0F, -2F, 4F, 8F, 4F, 0F, -4F, 0F);
        ModelUtil.addCube(partdefinition, "left_hand", 50, 17, -10F, 0F, -1.5F, 3F, 10F, 3F, 0F, 2F, 0F);
        ModelUtil.addCube(partdefinition, "right_shoulder_pad", 0, 48, -15.5F, 0F, -5F, 6F, 6F, 10F, 20.5F, -3F, 0F, 0F, 0F, 0.6109F);
        ModelUtil.addCube(partdefinition, "right_shoulder", 0, 31, -14F, 0F, -4F, 8F, 8F, 8F, 18.5F, -5F, 0F, 0F, 0F, 0.6109F);
        ModelUtil.addCube(partdefinition, "right_arm", 33, 18, 6.5F, 0F, -2F, 4F, 8F, 4F, 0F, -4F, 0F);
        ModelUtil.addCube(partdefinition, "right_hand", 50, 17, 7F, 0F, -1.5F, 3F, 10F, 3F, 0F, 2F, 0F);
        ModelUtil.addCube(partdefinition, "rod1", 42, 31, 2F, -5F, -3F, 1F, 10F, 1F, 0F, 12F, 0F);
        ModelUtil.addCube(partdefinition, "rod2", 42, 31, 2F, -5F, 2F, 1F, 10F, 1F, 0F, 12F, 0F);
        ModelUtil.addCube(partdefinition, "rod3", 42, 31, -3F, -5F, 2F, 1F, 10F, 1F, 0F, 12F, 0F);
        ModelUtil.addCube(partdefinition, "rod4", 42, 31, -3F, -5F, -3F, 1F, 10F, 1F, 0F, 12F, 0F);
        ModelUtil.addCube(partdefinition, "central_rod", 33, 31, -1F, -8F, -1F, 2F, 16F, 2F, 0F, 12F, 0F);
        ModelUtil.addCube(partdefinition, "head", 0, 0, -4F, -6F, -4F, 8F, 6F, 8F, 0F, -11F, 0F);
        ModelUtil.addCube(partdefinition, "neck", 0, 15, -2F, -1F, -2F, 4F, 1F, 4F, 0F, -10F, 0F);
        ModelUtil.addCube(partdefinition, "body", 0, 21, -5F, 0F, -3F, 10F, 3F, 6F, 0F, -10F, 0F);
        ModelUtil.addCube(partdefinition, "core1", 33, 2, -3F, -3F, -3F, 6F, 6F, 6F, 0F, -2F, 0F);
        ModelUtil.addCube(partdefinition, "core2", 33, 2, -3F, -3F, -3F, 6F, 6F, 6F, 0F, -2F, 0F);
        ModelUtil.addCube(partdefinition, "core3", 33, 2, -3F, -3F, -3F, 6F, 6F, 6F, 0F, -2F, 0F);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(EarthGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * (float) Math.PI / 180F;
        head.yRot = netHeadYaw * (float) Math.PI / 180F;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45F) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45F));
        leftShoulderPad.y = -3 + y;
        leftShoulder.y = -5 + y;
        leftArm.y = -4 + y;
        leftHand.y = 2 + y;
        rightShoulderPad.y = -3 + y;
        rightShoulder.y = -5 + y;
        rightArm.y = -4 + y;
        rightHand.y = 2 + y;
        rod1.y = 12 + y;
        rod2.y = 12 + y;
        rod3.y = 12 + y;
        rod4.y = 12 + y;
        centralRod.y = 12 + y;
        head.y = -11 + y;
        neck.y = -10 + y;
        body.y = -10 + y;
        core1.y = -2 + y;
        core2.y = -2 + y;
        core3.y = -2 + y;
        core1.xRot = ageInTicks % 360 / 6F;
        core2.yRot = (ageInTicks + 120) % 360 / 6F;
        core3.zRot = (ageInTicks + 240) % 360 / 6F;
        float rot = (float) ((ageInTicks % 360) * Math.PI / 180);
        rod1.yRot = rot;
        rod2.yRot = rot;
        rod3.yRot = rot;
        rod4.yRot = rot;
        float z = Mth.cos(ageInTicks * 0.1F) * 0.05F + 0.05F;
        leftHand.zRot = -z;
        rightHand.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leftShoulderPad.render(poseStack, buffer, packedLight, packedOverlay);
        leftShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftHand.render(poseStack, buffer, packedLight, packedOverlay);
        rightShoulderPad.render(poseStack, buffer, packedLight, packedOverlay);
        rightShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        rightHand.render(poseStack, buffer, packedLight, packedOverlay);
        rod1.render(poseStack, buffer, packedLight, packedOverlay);
        rod2.render(poseStack, buffer, packedLight, packedOverlay);
        rod3.render(poseStack, buffer, packedLight, packedOverlay);
        rod4.render(poseStack, buffer, packedLight, packedOverlay);
        centralRod.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        neck.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        core1.render(poseStack, buffer, packedLight, packedOverlay);
        core2.render(poseStack, buffer, packedLight, packedOverlay);
        core3.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
