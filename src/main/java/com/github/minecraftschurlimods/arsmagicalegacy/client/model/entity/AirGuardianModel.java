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
    private final ModelPart rightShoulder;
    private final ModelPart leftShoulder;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;

    public AirGuardianModel(ModelPart root) {
        rightShoulder = root.getChild("right_shoulder");
        leftShoulder = root.getChild("left_shoulder");
        rightArm = root.getChild("right_arm");
        leftArm = root.getChild("left_arm");
        core1 = root.getChild("core1");
        core2 = root.getChild("core2");
        core3 = root.getChild("core3");
        head = root.getChild("head");
        body = root.getChild("body");
        tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        ModelUtil.addCube(partdefinition, "right_shoulder", 0, 47, -7F, -2F, -2F, 4F, 4F, 4F, 0F, -4F, 0F);
        ModelUtil.addCube(partdefinition, "left_shoulder", 0, 56, 3F, -2F, -2F, 4F, 4F, 4F, 0F, -4F, 0F);
        ModelUtil.addCube(partdefinition, "right_arm", 17, 55, -6.5F, 2F, -1.5F, 3F, 6F, 3F, 0F, -4F, 0F, true);
        ModelUtil.addCube(partdefinition, "left_arm", 17, 55, 3.5F, 2F, -1.5F, 3F, 6F, 3F, 0F, -4F, 0F);
        ModelUtil.addCube(partdefinition, "core1", 21, 0, -3F, -3F, -3F, 6F, 6F, 6F, 0F, 16F, 0F);
        ModelUtil.addCube(partdefinition, "core2", 21, 0, -3F, -3F, -3F, 6F, 6F, 6F, 0F, 16F, 0F);
        ModelUtil.addCube(partdefinition, "core3", 21, 0, -3F, -3F, -3F, 6F, 6F, 6F, 0F, 16F, 0F);
        ModelUtil.addCube(partdefinition, "head", 17, 17, -3.5F, -7F, -3.5F, 7F, 7F, 7F, 0F, -6F, 0F);
        ModelUtil.addCube(partdefinition, "body", 0, 32, -3F, 0F, -3F, 6F, 8F, 6F, 0F, -6F, 0F);
        ModelUtil.addCube(partdefinition, "tail", 0, 21, -2F, 0F, -2F, 4F, 6F, 4F, 0F, 2F, 0F);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(AirGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch * (float) Math.PI / 180F;
        head.yRot = netHeadYaw * (float) Math.PI / 180F;
        float z = Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        rightShoulder.zRot = z;
        rightArm.zRot = z;
        leftShoulder.zRot = -z;
        leftArm.zRot = -z;
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45F) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45F));
        rightShoulder.y = -4 + y;
        leftShoulder.y = -4 + y;
        rightArm.y = -4 + y;
        leftArm.y = -4 + y;
        core1.y = 16 + y;
        core2.y = 16 + y;
        core3.y = 16 + y;
        head.y = -6 + y;
        body.y = -6 + y;
        tail.y = 2 + y;
        core1.xRot = ageInTicks % 360 / 6F;
        core2.yRot = (ageInTicks + 120) % 360 / 6F;
        core3.zRot = (ageInTicks + 240) % 360 / 6F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rightShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        leftShoulder.render(poseStack, buffer, packedLight, packedOverlay);
        rightArm.render(poseStack, buffer, packedLight, packedOverlay);
        leftArm.render(poseStack, buffer, packedLight, packedOverlay);
        core1.render(poseStack, buffer, packedLight, packedOverlay);
        core2.render(poseStack, buffer, packedLight, packedOverlay);
        core3.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
