package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
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

public class AirGuardianModel extends EntityModel<AirGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "air_guardian"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;
    private final ModelPart rightShoulder;
    private final ModelPart rightArm;
    private final ModelPart leftShoulder;
    private final ModelPart leftArm;

    public AirGuardianModel(ModelPart root) {
        head = root.getChild("head");
        body = root.getChild("body");
        tail = root.getChild("tail");
        core1 = root.getChild("core1");
        core2 = root.getChild("core2");
        core3 = root.getChild("core3");
        rightShoulder = root.getChild("right_shoulder");
        rightArm = root.getChild("right_arm");
        leftShoulder = root.getChild("left_shoulder");
        leftArm = root.getChild("left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        ModelUtil.addCube(pd, "head", 0, 0, -3.5f, -7, -3.5f, 7, 7, 7, 0, -6, 0);
        ModelUtil.addCube(pd, "body", 40, 0, -3, 0, -3, 6, 8, 6, 0, -6, 0);
        ModelUtil.addCube(pd, "tail", 40, 14, -2, 0, -2, 4, 6, 4, 0, 2, 0);
        ModelUtil.addCube(pd, "core1", 0, 14, -3, -3, -3, 6, 6, 6, 0, 16, 0);
        ModelUtil.addCube(pd, "core2", 0, 14, -3, -3, -3, 6, 6, 6, 0, 16, 0);
        ModelUtil.addCube(pd, "core3", 0, 14, -3, -3, -3, 6, 6, 6, 0, 16, 0);
        ModelUtil.addCube(pd, "right_shoulder", 24, 14, -7, -2, -2, 4, 4, 4, 0, -4, 0);
        ModelUtil.addCube(pd, "right_arm", 28, 5, -6.5f, 2, -1.5f, 3, 6, 3, 0, -4, 0);
        ModelUtil.addCube(pd, "left_shoulder", 24, 22, 3, -2, -2, 4, 4, 4, 0, -4, 0);
        ModelUtil.addCube(pd, "left_arm", 28, 5, 3.5f, 2, -1.5f, 3, 6, 3, 0, -4, 0);
        return LayerDefinition.create(md, 64, 32);
    }

    @Override
    public void setupAnim(AirGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * (float) Math.PI / 180f;
        head.yRot = netHeadYaw * (float) Math.PI / 180f;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        head.y = -6 + y;
        body.y = -6 + y;
        tail.y = 2 + y;
        core1.y = 16 + y;
        core2.y = 16 + y;
        core3.y = 16 + y;
        rightShoulder.y = -4 + y;
        rightArm.y = -4 + y;
        leftShoulder.y = -4 + y;
        leftArm.y = -4 + y;
        core1.xRot = ageInTicks % 360 / 6f;
        core2.yRot = (ageInTicks + 120) % 360 / 6f;
        core3.zRot = (ageInTicks + 240) % 360 / 6f;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        rightShoulder.zRot = swing;
        rightArm.zRot = swing;
        leftShoulder.zRot = -swing;
        leftArm.zRot = -swing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        core1.render(poseStack, buffer, packedLight, packedOverlay);
        core2.render(poseStack, buffer, packedLight, packedOverlay);
        core3.render(poseStack, buffer, packedLight, packedOverlay);
        rightShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
