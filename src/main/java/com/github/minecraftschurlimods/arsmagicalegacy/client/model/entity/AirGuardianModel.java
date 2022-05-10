package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
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
    private final ModelPart main;
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
        main = root.getChild("main");
        tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        CubeDeformation deformation = new CubeDeformation(0F);
        partdefinition.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 47).addBox(-7F, -2F, -2F, 4F, 4F, 4F, deformation), PartPose.offset(0F, -4F, 0F));
        partdefinition.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(0, 56).addBox(3F, -2F, -2F, 4F, 4F, 4F, deformation), PartPose.offset(0F, -4F, 0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(17, 55).mirror().addBox(-6.5F, 2F, -1.5F, 3F, 6F, 3F, deformation).mirror(false), PartPose.offset(0F, -4F, 0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(17, 55).addBox(3.5F, 2F, -1.5F, 3F, 6F, 3F, deformation), PartPose.offset(0F, -4F, 0F));
        partdefinition.addOrReplaceChild("core1", CubeListBuilder.create().texOffs(21, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, deformation), PartPose.offset(0F, 16F, 0F));
        partdefinition.addOrReplaceChild("core2", CubeListBuilder.create().texOffs(21, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, deformation), PartPose.offset(0F, 16F, 0F));
        partdefinition.addOrReplaceChild("core3", CubeListBuilder.create().texOffs(21, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, deformation), PartPose.offset(0F, 16F, 0F));
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(17, 17).addBox(-3.5F, -7F, -3.5F, 7F, 7F, 7F, deformation), PartPose.offset(0F, -6F, 0F));
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 32).addBox(-3F, 0F, -3F, 6F, 8F, 6F, deformation), PartPose.offset(0F, -6F, 0F));
        partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 21).addBox(-2F, 0F, -2F, 4F, 6F, 4F, deformation), PartPose.offset(0F, 2F, 0F));
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
        main.y = -6 + y;
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
        main.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
