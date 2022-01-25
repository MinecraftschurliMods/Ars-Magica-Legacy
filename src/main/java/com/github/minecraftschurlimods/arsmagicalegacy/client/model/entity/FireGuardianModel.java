package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
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

public class FireGuardianModel<T extends FireGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "fire_guardian"), "main");
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart arms;

    public FireGuardianModel(ModelPart root) {
        head = root.getChild("head");
        torso = root.getChild("torso");
        arms = root.getChild("arms");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(63, 43).addBox(-22.0F, -39.0F, 16.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, 18.0F, -26.0F));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(63, 29).addBox(-20.5F, -37.5F, 11.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 16).addBox(-21.5F, -38.0F, 13.0F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(63, 65).addBox(-14.0F, -37.5F, 19.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 57).addBox(-13.5F, -37.0F, 25.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(63, 65).addBox(-24.0F, -37.5F, 19.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 57).addBox(-23.5F, -37.0F, 25.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offsetAndRotation(-13.0F, 24.0F, 25.0F, 0.0436F, 0.0F, 0.0F));
        PartDefinition torso9_r1 = torso.addOrReplaceChild("torso9_r1", CubeListBuilder.create().texOffs(24, 15).addBox(11.5F, -3.723F, -22.6305F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 14).addBox(11.0F, -10.7059F, -22.9358F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition torso7_r1 = torso.addOrReplaceChild("torso7_r1", CubeListBuilder.create().texOffs(20, 15).addBox(10.0F, -15.7581F, -22.9587F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));
        PartDefinition torso6_r1 = torso.addOrReplaceChild("torso6_r1", CubeListBuilder.create().texOffs(18, 15).addBox(9.5F, -21.5F, -22.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
        PartDefinition torso5_r1 = torso.addOrReplaceChild("torso5_r1", CubeListBuilder.create().texOffs(15, 14).addBox(8.5F, -26.5559F, -20.0997F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
        PartDefinition torso4_r1 = torso.addOrReplaceChild("torso4_r1", CubeListBuilder.create().texOffs(11, 12).addBox(7.5F, -30.3893F, -18.1547F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        PartDefinition torso3_r1 = torso.addOrReplaceChild("torso3_r1", CubeListBuilder.create().texOffs(8, 10).addBox(6.5F, -33.0433F, -17.6885F, 11.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
        PartDefinition torso2_r1 = torso.addOrReplaceChild("torso2_r1", CubeListBuilder.create().texOffs(3, 6).addBox(5.5F, -37.909F, -17.2278F, 13.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
        PartDefinition torso1_r1 = torso.addOrReplaceChild("torso1_r1", CubeListBuilder.create().texOffs(0, 3).addBox(5.0F, -44.1753F, -11.4563F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
        PartDefinition neck6_r1 = torso.addOrReplaceChild("neck6_r1", CubeListBuilder.create().texOffs(63, 115).addBox(6.0F, -45.016F, -7.9375F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5061F, 0.0F, 0.0F));
        PartDefinition neck5_r1 = torso.addOrReplaceChild("neck5_r1", CubeListBuilder.create().texOffs(67, 117).addBox(7.0F, -45.7259F, -5.2202F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5498F, 0.0F, 0.0F));
        PartDefinition neck4_r1 = torso.addOrReplaceChild("neck4_r1", CubeListBuilder.create().texOffs(71, 119).addBox(8.0F, -46.5564F, -2.3294F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5934F, 0.0F, 0.0F));
        PartDefinition neck3_r1 = torso.addOrReplaceChild("neck3_r1", CubeListBuilder.create().texOffs(75, 121).addBox(9.0F, -47.3338F, -0.281F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(83, 125).addBox(11.0F, -48.8338F, 1.719F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));
        PartDefinition neck2_r1 = torso.addOrReplaceChild("neck2_r1", CubeListBuilder.create().texOffs(74, 123).addBox(10.0F, -47.7906F, 2.402F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6458F, 0.0F, 0.0F));
        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(33, 110).addBox(-4.0F, -22.0F, 1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 108).addBox(-6.0F, -6.0F, 0.0F, 6.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(33, 110).mirror().addBox(-22.0F, -22.0F, 1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 108).mirror().addBox(-24.0F, -6.0F, 0.0F, 6.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(11.0F, 10.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        torso.render(poseStack, buffer, packedLight, packedOverlay);
        arms.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
