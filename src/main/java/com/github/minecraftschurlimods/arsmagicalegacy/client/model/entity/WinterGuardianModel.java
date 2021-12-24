package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WinterGuardian;
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


public class WinterGuardianModel<T extends WinterGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "winter_guardian"), "main");
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart arm;
    private final ModelPart lower_body;

    public WinterGuardianModel(ModelPart root) {
        this.head = root.getChild("head");
        this.torso = root.getChild("torso");
        this.arm = root.getChild("arm");
        this.lower_body = root.getChild("lower_body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(79, 31).addBox(-6.0F, -39.0F, -9.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(83, 20).addBox(-6.0F, -35.0F, -7.0F, 10.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(79, 31).addBox(-6.0F, -33.0F, -9.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(89, 9).addBox(1.0F, -35.0F, -8.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(89, 9).addBox(-5.0F, -35.0F, -8.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 14.0F, 4.0F));

        PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition snow_bottom_r1 = torso.addOrReplaceChild("snow_bottom_r1", CubeListBuilder.create().texOffs(37, 48).addBox(0.25F, -8.37F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 1.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition ice_bottom_r1 = torso.addOrReplaceChild("ice_bottom_r1", CubeListBuilder.create().texOffs(33, 29).addBox(-19.35F, -19.85F, -10.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, -26.0F, 5.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition ice_top_r1 = torso.addOrReplaceChild("ice_top_r1", CubeListBuilder.create().texOffs(69, 46).addBox(-10.0F, 2.5F, -17.5F, 14.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -21.5F, 6.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition shoulder = torso.addOrReplaceChild("shoulder", CubeListBuilder.create().texOffs(112, 8).mirror().addBox(10.7279F, -40.2132F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 8).mirror().addBox(10.7279F, -40.2132F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 8).mirror().addBox(10.7279F, -40.2132F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 8).addBox(-14.7279F, -40.2132F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(112, 8).addBox(-14.7279F, -40.2132F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(112, 8).addBox(-14.7279F, -40.2132F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shoulder_right_r1 = shoulder.addOrReplaceChild("shoulder_right_r1", CubeListBuilder.create().texOffs(25, 6).addBox(-6.5F, -15.0F, -3.0F, 10.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -26.0F, -3.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition shoulder_left_r1 = shoulder.addOrReplaceChild("shoulder_left_r1", CubeListBuilder.create().texOffs(25, 6).addBox(3.5F, -5.0F, -3.0F, 10.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -26.0F, -3.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition arm = partdefinition.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(0, 6).addBox(17.0F, -34.0F, -3.0F, 6.0F, 26.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-23.0F, -34.0F, -3.0F, 6.0F, 26.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hand = arm.addOrReplaceChild("hand", CubeListBuilder.create().texOffs(120, 54).mirror().addBox(17.0F, -8.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(91, 2).mirror().addBox(22.0F, -8.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(91, 2).mirror().addBox(17.0F, -8.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(69, 2).mirror().addBox(17.0F, -7.0F, -2.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 54).addBox(-20.0F, -8.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 2).addBox(-18.0F, -8.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(91, 2).addBox(-23.0F, -8.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(69, 2).addBox(-23.0F, -7.0F, -2.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lower_body = partdefinition.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(120, 17).addBox(-1.5F, -1.5F, -9.065F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(120, 17).addBox(-1.5F, -1.5F, 7.935F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.065F));

        PartDefinition cube_east_r1 = lower_body.addOrReplaceChild("cube_east_r1", CubeListBuilder.create().texOffs(120, 17).addBox(-1.435F, -1.5F, -9.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_west_r1 = lower_body.addOrReplaceChild("cube_west_r1", CubeListBuilder.create().texOffs(120, 17).addBox(-1.565F, -1.5F, -9.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition core = lower_body.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 52).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -0.065F));

        PartDefinition rods = lower_body.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rod_northeast_r1 = rods.addOrReplaceChild("rod_northeast_r1", CubeListBuilder.create().texOffs(120, 22).addBox(-9.9541F, -6.0F, -0.9541F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(120, 22).addBox(8.0459F, -6.0F, -0.9541F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition rod_northwest_r1 = rods.addOrReplaceChild("rod_northwest_r1", CubeListBuilder.create().texOffs(120, 37).mirror().addBox(-1.0459F, -6.0F, -9.9541F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 37).mirror().addBox(-1.0459F, -6.0F, 8.0459F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, -3.1416F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        torso.render(poseStack, buffer, packedLight, packedOverlay);
        arm.render(poseStack, buffer, packedLight, packedOverlay);
        lower_body.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
