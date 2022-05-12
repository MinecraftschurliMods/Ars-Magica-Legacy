package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
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
import net.minecraft.util.Mth;

public class WaterGuardianModel extends EntityModel<WaterGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_guardian"), "main");
    private final ModelPart upperHead;
    private final ModelPart middleHead;
    private final ModelPart lowerHead;
    private final ModelPart core;
    private final ModelPart northernOuterBody;
    private final ModelPart easternOuterBody;
    private final ModelPart southernOuterBody;
    private final ModelPart westernOuterBody;
    private final ModelPart topNorthernInnerBody;
    private final ModelPart middleNorthernInnerBody;
    private final ModelPart bottomNorthernInnerBody;
    private final ModelPart topEasternInnerBody;
    private final ModelPart middleEasternInnerBody;
    private final ModelPart bottomEasternInnerBody;
    private final ModelPart topSouthernInnerBody;
    private final ModelPart middleSouthernInnerBody;
    private final ModelPart bottomSouthernInnerBody;
    private final ModelPart topWesternInnerBody;
    private final ModelPart middleWesternInnerBody;
    private final ModelPart bottomWesternInnerBody;
    private final ModelPart innerNorthernOrb;
    private final ModelPart outerNorthernOrb;
    private final ModelPart innerEasternOrb;
    private final ModelPart outerEasternOrb;
    private final ModelPart innerSouthernOrb;
    private final ModelPart outerSouthernOrb;
    private final ModelPart innerWesternOrb;
    private final ModelPart outerWesternOrb;
    private final ModelPart northEasternRod;
    private final ModelPart southEasternRod;
    private final ModelPart southWesternRod;
    private final ModelPart northWesternRod;
    private final ModelPart northEasternTentacle;
    private final ModelPart southEasternTentacle;
    private final ModelPart southWesternTentacle;
    private final ModelPart northWesternTentacle;
    private final ModelPart northernTentacle;
    private final ModelPart easternTentacle;
    private final ModelPart southernTentacle;
    private final ModelPart westernTentacle;

    public WaterGuardianModel(ModelPart root) {
        upperHead = root.getChild("upper_head");
        middleHead = root.getChild("middle_head");
        lowerHead = root.getChild("lower_head");
        core = root.getChild("core");
        northernOuterBody = root.getChild("northern_outer_body");
        easternOuterBody = root.getChild("eastern_outer_body");
        southernOuterBody = root.getChild("southern_outer_body");
        westernOuterBody = root.getChild("western_outer_body");
        topNorthernInnerBody = root.getChild("top_northern_inner_body");
        middleNorthernInnerBody = root.getChild("middle_northern_inner_body");
        bottomNorthernInnerBody = root.getChild("bottom_northern_inner_body");
        topEasternInnerBody = root.getChild("top_eastern_inner_body");
        middleEasternInnerBody = root.getChild("middle_eastern_inner_body");
        bottomEasternInnerBody = root.getChild("bottom_eastern_inner_body");
        topSouthernInnerBody = root.getChild("top_southern_inner_body");
        middleSouthernInnerBody = root.getChild("middle_southern_inner_body");
        bottomSouthernInnerBody = root.getChild("bottom_southern_inner_body");
        topWesternInnerBody = root.getChild("top_western_inner_body");
        middleWesternInnerBody = root.getChild("top_western_inner_body");
        bottomWesternInnerBody = root.getChild("top_western_inner_body");
        innerNorthernOrb = root.getChild("inner_northern_orb");
        outerNorthernOrb = root.getChild("outer_northern_orb");
        innerEasternOrb = root.getChild("inner_eastern_orb");
        outerEasternOrb = root.getChild("outer_eastern_orb");
        innerSouthernOrb = root.getChild("inner_southern_orb");
        outerSouthernOrb = root.getChild("outer_southern_orb");
        innerWesternOrb = root.getChild("inner_western_orb");
        outerWesternOrb = root.getChild("outer_western_orb");
        northEasternRod = root.getChild("north_eastern_rod");
        southEasternRod = root.getChild("south_eastern_rod");
        southWesternRod = root.getChild("south_western_rod");
        northWesternRod = root.getChild("north_western_rod");
        northEasternTentacle = root.getChild("north_eastern_tentacle");
        southEasternTentacle = root.getChild("south_eastern_tentacle");
        southWesternTentacle = root.getChild("south_western_tentacle");
        northWesternTentacle = root.getChild("north_western_tentacle");
        northernTentacle = root.getChild("northern_tentacle");
        easternTentacle = root.getChild("eastern_tentacle");
        southernTentacle = root.getChild("southern_tentacle");
        westernTentacle = root.getChild("western_tentacle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        CubeDeformation deformation = new CubeDeformation(0F);
        partdefinition.addOrReplaceChild("upper_head", CubeListBuilder.create().texOffs(19, 32).addBox(-4F, -0.5F, -4F, 8F, 1F, 8F, deformation), PartPose.offset(0F, -0.5F, 0F));
        partdefinition.addOrReplaceChild("middle_head", CubeListBuilder.create().texOffs(23, 42).addBox(-5F, -0.5F, -5F, 10F, 1F, 10F, deformation), PartPose.offset(0F, 0.5F, 0F));
        partdefinition.addOrReplaceChild("lower_head", CubeListBuilder.create().texOffs(19, 32).addBox(-4F, -0.5F, -4F, 8F, 1F, 8F, deformation), PartPose.offset(0F, 2.5F, 0F));
        partdefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(10, 56).addBox(-2F, -2F, -2F, 4F, 4F, 4F, deformation), PartPose.offset(0F, 5F, 0F));
        partdefinition.addOrReplaceChild("northern_outer_body", CubeListBuilder.create().texOffs(0, 42).addBox(-5F, -2.5F, 4F, 10F, 5F, 1F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("eastern_outer_body", CubeListBuilder.create().texOffs(0, 28).addBox(4F, -2.5F, -4F, 1F, 5F, 8F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("southern_outer_body", CubeListBuilder.create().texOffs(0, 42).addBox(-5F, -2.5F, -5F, 10F, 5F, 1F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("western_outer_body", CubeListBuilder.create().texOffs(0, 28).addBox(-5F, -2.5F, -4F, 1F, 5F, 8F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("top_northern_inner_body", CubeListBuilder.create().texOffs(27, 62).addBox(-4F, -0.5F, 3F, 8F, 1F, 1F, deformation), PartPose.offset(0F, 5.5F, 0F));
        partdefinition.addOrReplaceChild("middle_northern_inner_body", CubeListBuilder.create().texOffs(27, 62).addBox(-4F, -0.5F, 3F, 8F, 1F, 1F, deformation), PartPose.offset(0F, 4.5F, 0F));
        partdefinition.addOrReplaceChild("bottom_northern_inner_body", CubeListBuilder.create().texOffs(27, 62).addBox(-4F, -0.5F, 3F, 8F, 1F, 1F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("top_eastern_inner_body", CubeListBuilder.create().texOffs(27, 54).addBox(3F, -0.5F, -3F, 1F, 1F, 6F, deformation), PartPose.offset(0F, 5.5F, 0F));
        partdefinition.addOrReplaceChild("middle_eastern_inner_body", CubeListBuilder.create().texOffs(27, 54).addBox(3F, -0.5F, -3F, 1F, 1F, 6F, deformation), PartPose.offset(0F, 4.5F, 0F));
        partdefinition.addOrReplaceChild("bottom_eastern_inner_body", CubeListBuilder.create().texOffs(27, 54).addBox(3F, -0.5F, -3F, 1F, 1F, 6F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("top_southern_inner_body", CubeListBuilder.create().texOffs(27, 62).addBox(-4F, -0.5F, -4F, 8F, 1F, 1F, deformation), PartPose.offset(0F, 5.5F, 0F));
        partdefinition.addOrReplaceChild("middle_southern_inner_body", CubeListBuilder.create().texOffs(27, 62).addBox(-4F, -0.5F, -4F, 8F, 1F, 1F, deformation), PartPose.offset(0F, 4.5F, 0F));
        partdefinition.addOrReplaceChild("bottom_southern_inner_body", CubeListBuilder.create().texOffs(27, 62).addBox(-4F, -0.5F, -4F, 8F, 1F, 1F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("top_western_inner_body", CubeListBuilder.create().texOffs(27, 54).addBox(-4F, -0.5F, -3F, 1F, 1F, 6F, deformation), PartPose.offset(0F, 5.5F, 0F));
        partdefinition.addOrReplaceChild("middle_western_inner_body", CubeListBuilder.create().texOffs(27, 54).addBox(-4F, -0.5F, -3F, 1F, 1F, 6F, deformation), PartPose.offset(0F, 4.5F, 0F));
        partdefinition.addOrReplaceChild("bottom_western_inner_body", CubeListBuilder.create().texOffs(27, 54).addBox(-4F, -0.5F, -3F, 1F, 1F, 6F, deformation), PartPose.offset(0F, 3.5F, 0F));
        partdefinition.addOrReplaceChild("inner_northern_orb", CubeListBuilder.create().texOffs(0, 19).addBox(-11F, 8.5F, -0.5F, 2F, 1F, 1F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, 0F, -(float) (Math.PI / 2), 0F));
        partdefinition.addOrReplaceChild("outer_northern_orb", CubeListBuilder.create().texOffs(0, 15).addBox(-1F, -10.5F, -10F, 2F, 1F, 2F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, (float) (Math.PI / 2), 0F, 0F));
        partdefinition.addOrReplaceChild("inner_eastern_orb", CubeListBuilder.create().texOffs(0, 19).addBox(-11F, 8.5F, -0.5F, 2F, 1F, 1F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, 0F, -(float) Math.PI, 0F));
        partdefinition.addOrReplaceChild("outer_eastern_orb", CubeListBuilder.create().texOffs(0, 15).addBox(-1F, -10.5F, -10F, 2F, 1F, 2F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, (float) (Math.PI / 2), -(float) (Math.PI / 2), 0F));
        partdefinition.addOrReplaceChild("inner_southern_orb", CubeListBuilder.create().texOffs(0, 19).addBox(-11F, 8.5F, -0.5F, 2F, 1F, 1F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, 0F, (float) (Math.PI / 2), 0F));
        partdefinition.addOrReplaceChild("outer_southern_orb", CubeListBuilder.create().texOffs(0, 15).addBox(-1F, -10.5F, -10F, 2F, 1F, 2F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, (float) (Math.PI / 2), -(float) Math.PI, 0F));
        partdefinition.addOrReplaceChild("inner_western_orb", CubeListBuilder.create().texOffs(0, 19).addBox(-11F, 8.5F, -0.5F, 2F, 1F, 1F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, 0F, 0F, 0F));
        partdefinition.addOrReplaceChild("outer_western_orb", CubeListBuilder.create().texOffs(0, 15).addBox(-1F, -10.5F, -10F, 2F, 1F, 2F, deformation), PartPose.offsetAndRotation(0F, -6.5F, 0F, (float) (Math.PI / 2), (float) (Math.PI / 2), 0F));
        partdefinition.addOrReplaceChild("north_eastern_rod", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, deformation), PartPose.offsetAndRotation(0F, 12F, 0F, (float) (Math.PI / 2), (float) (3 * Math.PI / 4), 0F));
        partdefinition.addOrReplaceChild("south_eastern_rod", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, deformation), PartPose.offsetAndRotation(0F, 12F, 0F, (float) (Math.PI / 2), -(float) (3 * Math.PI / 4), 0F));
        partdefinition.addOrReplaceChild("south_western_rod", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, deformation), PartPose.offsetAndRotation(0F, 12F, 0F, (float) (Math.PI / 2), -(float) (Math.PI / 4), 0F));
        partdefinition.addOrReplaceChild("north_western_rod", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, deformation), PartPose.offsetAndRotation(0F, 12F, 0F, (float) (Math.PI / 2), (float) (Math.PI / 4), 0F));
        partdefinition.addOrReplaceChild("north_eastern_tentacle", CubeListBuilder.create().texOffs(0, 49).addBox(-4F, 0F, -4F, 1F, 14F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("south_eastern_tentacle", CubeListBuilder.create().texOffs(0, 49).addBox(-4F, 0F, 3F, 1F, 14F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("south_western_tentacle", CubeListBuilder.create().texOffs(0, 49).addBox(3F, 0F, 3F, 1F, 14F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("north_western_tentacle", CubeListBuilder.create().texOffs(0, 49).addBox(3F, 0F, -4F, 1F, 14F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("northern_tentacle", CubeListBuilder.create().texOffs(5, 53).addBox(-0.5F, 0F, -4F, 1F, 10F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("eastern_tentacle", CubeListBuilder.create().texOffs(5, 53).addBox(-4F, 0F, -0.5F, 1F, 10F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("southern_tentacle", CubeListBuilder.create().texOffs(5, 53).addBox(-0.5F, 0F, 3F, 1F, 10F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        partdefinition.addOrReplaceChild("western_tentacle", CubeListBuilder.create().texOffs(5, 53).addBox(3F, 0F, -0.5F, 1F, 10F, 1F, deformation), PartPose.offset(0F, 6F, 0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(WaterGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45F) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45F));
        upperHead.y = -0.5F + y;
        middleHead.y = 0.5F + y;
        lowerHead.y = 2.5F + y;
        core.y = 5 + y;
        northernOuterBody.y = 3.5F + y;
        easternOuterBody.y = 3.5F + y;
        southernOuterBody.y = 3.5F + y;
        westernOuterBody.y = 3.5F + y;
        topNorthernInnerBody.y = 5.5F + y;
        middleNorthernInnerBody.y = 4.5F + y;
        bottomNorthernInnerBody.y = 3.5F + y;
        topEasternInnerBody.y = 5.5F + y;
        middleEasternInnerBody.y = 4.5F + y;
        bottomEasternInnerBody.y = 3.5F + y;
        topSouthernInnerBody.y = 5.5F + y;
        middleSouthernInnerBody.y = 4.5F + y;
        bottomSouthernInnerBody.y = 3.5F + y;
        topWesternInnerBody.y = 5.5F + y;
        middleWesternInnerBody.y = 4.5F + y;
        bottomWesternInnerBody.y = 3.5F + y;
        innerNorthernOrb.y = -6.5F + y;
        outerNorthernOrb.y = -6.5F + y;
        innerEasternOrb.y = -6.5F + y;
        outerEasternOrb.y = -6.5F + y;
        innerSouthernOrb.y = -6.5F + y;
        outerSouthernOrb.y = -6.5F + y;
        innerWesternOrb.y = -6.5F + y;
        outerWesternOrb.y = -6.5F + y;
        northEasternRod.y = 12F + y;
        southEasternRod.y = 12F + y;
        southWesternRod.y = 12F + y;
        northWesternRod.y = 12F + y;
        northEasternTentacle.y = 6F + y;
        southEasternTentacle.y = 6F + y;
        southWesternTentacle.y = 6F + y;
        northWesternTentacle.y = 6F + y;
        northernTentacle.y = 6F + y;
        easternTentacle.y = 6F + y;
        southernTentacle.y = 6F + y;
        westernTentacle.y = 6F + y;
        float rot = (float) (((ageInTicks + entity.getSpinRotation()) % 360) * Math.PI / 180);
        innerNorthernOrb.yRot = -(float) (Math.PI / 2) + rot;
        outerNorthernOrb.yRot = rot;
        innerEasternOrb.yRot = -(float) Math.PI + rot;
        outerEasternOrb.yRot = -(float) (Math.PI / 2) + rot;
        innerSouthernOrb.yRot = (float) (Math.PI / 2) + rot;
        outerSouthernOrb.yRot = -(float) Math.PI + rot;
        innerWesternOrb.yRot = rot;
        outerWesternOrb.yRot = (float) (Math.PI / 2) + rot;
        northEasternRod.yRot = (float) (3 * Math.PI / 4) + rot;
        southEasternRod.yRot = -(float) (3 * Math.PI / 4) + rot;
        southWesternRod.yRot = -(float) (Math.PI / 4) + rot;
        northWesternRod.yRot = (float) (Math.PI / 4) + rot;
        float swing = Mth.sin(ageInTicks % 360 * (float) Math.PI / 45F) * 0.1F;
        northEasternTentacle.xRot = swing / 2F;
        northEasternTentacle.zRot = swing / 2F;
        southWesternTentacle.xRot = -swing / 2F;
        southWesternTentacle.zRot = -swing / 2F;
        southEasternTentacle.xRot = swing / 2F;
        southEasternTentacle.zRot = -swing / 2F;
        northWesternTentacle.xRot = -swing / 2F;
        northWesternTentacle.zRot = swing / 2F;
        northernTentacle.xRot = -swing;
        easternTentacle.zRot = swing;
        southernTentacle.xRot = swing;
        westernTentacle.zRot = -swing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        upperHead.render(poseStack, buffer, packedLight, packedOverlay);
        middleHead.render(poseStack, buffer, packedLight, packedOverlay);
        lowerHead.render(poseStack, buffer, packedLight, packedOverlay);
        core.render(poseStack, buffer, packedLight, packedOverlay);
        northernOuterBody.render(poseStack, buffer, packedLight, packedOverlay);
        easternOuterBody.render(poseStack, buffer, packedLight, packedOverlay);
        southernOuterBody.render(poseStack, buffer, packedLight, packedOverlay);
        westernOuterBody.render(poseStack, buffer, packedLight, packedOverlay);
        topNorthernInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        middleNorthernInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        bottomNorthernInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        topEasternInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        middleEasternInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        bottomEasternInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        topSouthernInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        middleSouthernInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        bottomSouthernInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        topWesternInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        middleWesternInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        bottomWesternInnerBody.render(poseStack, buffer, packedLight, packedOverlay);
        innerNorthernOrb.render(poseStack, buffer, packedLight, packedOverlay);
        outerNorthernOrb.render(poseStack, buffer, packedLight, packedOverlay);
        innerEasternOrb.render(poseStack, buffer, packedLight, packedOverlay);
        outerEasternOrb.render(poseStack, buffer, packedLight, packedOverlay);
        innerSouthernOrb.render(poseStack, buffer, packedLight, packedOverlay);
        outerSouthernOrb.render(poseStack, buffer, packedLight, packedOverlay);
        innerWesternOrb.render(poseStack, buffer, packedLight, packedOverlay);
        outerWesternOrb.render(poseStack, buffer, packedLight, packedOverlay);
        northEasternRod.render(poseStack, buffer, packedLight, packedOverlay);
        southEasternRod.render(poseStack, buffer, packedLight, packedOverlay);
        southWesternRod.render(poseStack, buffer, packedLight, packedOverlay);
        northWesternRod.render(poseStack, buffer, packedLight, packedOverlay);
        northEasternTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        southEasternTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        southWesternTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        northWesternTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        northernTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        easternTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        southernTentacle.render(poseStack, buffer, packedLight, packedOverlay);
        westernTentacle.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
