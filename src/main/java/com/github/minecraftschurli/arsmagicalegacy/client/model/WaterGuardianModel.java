package com.github.minecraftschurli.arsmagicalegacy.client.model;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaterGuardian;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class WaterGuardianModel<T extends WaterGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "water_guardian"), "main");
    private final ModelPart head;
    private final ModelPart orb_north;
    private final ModelPart orb_south;
    private final ModelPart orb_east;
    private final ModelPart orb_west;
    private final ModelPart rods;
    private final ModelPart tentacles;

    public WaterGuardianModel(ModelPart root) {
        this.head = root.getChild("head");
        this.orb_north = root.getChild("orb_north");
        this.orb_south = root.getChild("orb_south");
        this.orb_east = root.getChild("orb_east");
        this.orb_west = root.getChild("orb_west");
        this.rods = root.getChild("rods");
        this.tentacles = root.getChild("tentacles");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(19, 32).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(19, 32).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(23, 42).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition head_bottom = head.addOrReplaceChild("head_bottom", CubeListBuilder.create().texOffs(10, 56).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(4.0F, -5.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-5.0F, -5.0F, 4.0F, 10.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(27, 62).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 54).addBox(3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 62).addBox(-4.0F, -3.0F, 3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 54).addBox(-4.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 54).addBox(3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 62).addBox(-4.0F, -1.0F, 3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_bottom_inner_east_bottom_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_east_bottom_r1", CubeListBuilder.create().texOffs(27, 54).addBox(0.0F, -4.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 54).addBox(0.0F, 3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition head_bottom_inner_east_middle_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_east_middle_r1", CubeListBuilder.create().texOffs(27, 54).addBox(3.0F, 1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(27, 54).addBox(-4.0F, 1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition head_bottom_inner_south_bottom_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_south_bottom_r1", CubeListBuilder.create().texOffs(27, 62).addBox(-4.0F, 0.0F, 3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition head_bottom_inner_south_middle_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_south_middle_r1", CubeListBuilder.create().texOffs(27, 62).addBox(-4.0F, -4.0F, 1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition head_bottom_inner_north_bottom_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_north_bottom_r1", CubeListBuilder.create().texOffs(27, 62).addBox(-4.0F, 0.0F, 3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition head_bottom_inner_north_middle_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_north_middle_r1", CubeListBuilder.create().texOffs(27, 62).addBox(-4.0F, -4.0F, 1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition orb_north = partdefinition.addOrReplaceChild("orb_north", CubeListBuilder.create(), PartPose.offset(0.0F, 29.0F, 0.0F));

        PartDefinition orb_north_inner_r1 = orb_north.addOrReplaceChild("orb_north_inner_r1", CubeListBuilder.create().texOffs(0, 19).addBox(9.0F, -9.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition orb_north_outer_r1 = orb_north.addOrReplaceChild("orb_north_outer_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, 9.5F, 8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 1.5708F, 0.0F, 3.1416F));

        PartDefinition orb_south = partdefinition.addOrReplaceChild("orb_south", CubeListBuilder.create(), PartPose.offset(0.0F, 29.0F, -1.0F));

        PartDefinition orb_south_inner_r1 = orb_south.addOrReplaceChild("orb_south_inner_r1", CubeListBuilder.create().texOffs(0, 19).addBox(9.5F, -9.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition orb_south_outer_r1 = orb_south.addOrReplaceChild("orb_south_outer_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, 10.0F, 8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition orb_east = partdefinition.addOrReplaceChild("orb_east", CubeListBuilder.create(), PartPose.offset(-10.0F, 29.0F, -11.0F));

        PartDefinition orb_east_inner_r1 = orb_east.addOrReplaceChild("orb_east_inner_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-1.5F, -9.5F, -11.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition orb_east_outer_r1 = orb_east.addOrReplaceChild("orb_east_outer_r1", CubeListBuilder.create().texOffs(0, 15).addBox(10.0F, -1.0F, 8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition orb_west = partdefinition.addOrReplaceChild("orb_west", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, 29.0F, -8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition orb_south_inner_r2 = orb_west.addOrReplaceChild("orb_south_inner_r2", CubeListBuilder.create().texOffs(0, 19).addBox(9.5F, -9.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -5.0F, -11.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition orb_south_outer_r2 = orb_west.addOrReplaceChild("orb_south_outer_r2", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, 10.0F, 8.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -5.0F, -11.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rod_northeast_r1 = rods.addOrReplaceChild("rod_northeast_r1", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, -2.3562F));

        PartDefinition rod_southeast_r1 = rods.addOrReplaceChild("rod_southeast_r1", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 2.3562F));

        PartDefinition rod_southwest_r1 = rods.addOrReplaceChild("rod_southwest_r1", CubeListBuilder.create().texOffs(0, 0).addBox(9.5F, -0.5F, 4.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.7854F));

        PartDefinition rod_northwest_r1 = rods.addOrReplaceChild("rod_northwest_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -0.5F, -14.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, -2.3562F));

        PartDefinition tentacles = partdefinition.addOrReplaceChild("tentacles", CubeListBuilder.create().texOffs(0, 49).addBox(3.0F, -6.0F, -4.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(-4.0F, -6.0F, -4.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(3.0F, -6.0F, 3.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 49).addBox(-4.0F, -6.0F, 3.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 53).addBox(-0.5F, -6.0F, -4.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 53).addBox(-4.0F, -6.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 53).addBox(-0.5F, -6.0F, 3.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 53).addBox(3.0F, -6.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        orb_north.render(poseStack, buffer, packedLight, packedOverlay);
        orb_south.render(poseStack, buffer, packedLight, packedOverlay);
        orb_east.render(poseStack, buffer, packedLight, packedOverlay);
        orb_west.render(poseStack, buffer, packedLight, packedOverlay);
        rods.render(poseStack, buffer, packedLight, packedOverlay);
        tentacles.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
