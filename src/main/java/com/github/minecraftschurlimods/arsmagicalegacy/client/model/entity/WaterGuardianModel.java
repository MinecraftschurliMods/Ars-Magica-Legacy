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

public class WaterGuardianModel<T extends WaterGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_guardian"), "main");
    private final ModelPart head;
    private final ModelPart orb_north;
    private final ModelPart orb_south;
    private final ModelPart orb_east;
    private final ModelPart orb_west;
    private final ModelPart rods;
    private final ModelPart tentacles;

    public WaterGuardianModel(ModelPart root) {
        head = root.getChild("head");
        orb_north = root.getChild("orb_north");
        orb_south = root.getChild("orb_south");
        orb_east = root.getChild("orb_east");
        orb_west = root.getChild("orb_west");
        rods = root.getChild("rods");
        tentacles = root.getChild("tentacles");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(19, 32).addBox(-4F, -17F, -4F, 8F, 1F, 8F, new CubeDeformation(0F))
                .texOffs(19, 32).addBox(-4F, -14F, -4F, 8F, 1F, 8F, new CubeDeformation(0F))
                .texOffs(23, 42).addBox(-5F, -16F, -5F, 10F, 1F, 10F, new CubeDeformation(0F)), PartPose.offset(0F, 18F, 0F));
        PartDefinition head_bottom = head.addOrReplaceChild("head_bottom", CubeListBuilder.create()
                .texOffs(10, 56).addBox(-2F, -13F, -2F, 4F, 4F, 4F, new CubeDeformation(0F))
                .texOffs(0, 42).addBox(-5F, -15F, -5F, 10F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 28).addBox(4F, -15F, -4F, 1F, 5F, 8F, new CubeDeformation(0F))
                .texOffs(0, 42).addBox(-5F, -15F, 4F, 10F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 28).addBox(-5F, -15F, -4F, 1F, 5F, 8F, new CubeDeformation(0F))
                .texOffs(27, 62).addBox(-4F, -13F, -4F, 8F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(27, 54).addBox(3F, -13F, -3F, 1F, 1F, 6F, new CubeDeformation(0F))
                .texOffs(27, 62).addBox(-4F, -13F, 3F, 8F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(27, 54).addBox(-4F, -13F, -3F, 1F, 1F, 6F, new CubeDeformation(0F))
                .texOffs(27, 54).addBox(3F, -11F, -3F, 1F, 1F, 6F, new CubeDeformation(0F))
                .texOffs(27, 62).addBox(-4F, -11F, 3F, 8F, 1F, 1F, new CubeDeformation(0F)), PartPose.offset(0F, 0F, 0F));
        PartDefinition head_bottom_inner_east_bottom_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_east_bottom_r1", CubeListBuilder.create()
                .texOffs(27, 54).addBox(0F, -4F, -3F, 1F, 1F, 6F, new CubeDeformation(0F))
                .texOffs(27, 54).addBox(0F, 3F, -3F, 1F, 1F, 6F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 0F, 0F, -1.5708F));
        PartDefinition head_bottom_inner_east_middle_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_east_middle_r1", CubeListBuilder.create()
                .texOffs(27, 54).addBox(3F, 1F, -3F, 1F, 1F, 6F, new CubeDeformation(0F))
                .texOffs(27, 54).addBox(-4F, 1F, -3F, 1F, 1F, 6F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 0F, 0F, -3.1416F));
        PartDefinition head_bottom_inner_south_bottom_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_south_bottom_r1", CubeListBuilder.create()
                .texOffs(27, 62).addBox(-4F, 0F, 3F, 8F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 7F, 3.1416F, 0F, 0F));
        PartDefinition head_bottom_inner_south_middle_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_south_middle_r1", CubeListBuilder.create()
                .texOffs(27, 62).addBox(-4F, -4F, 1F, 8F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 7F, 1.5708F, 0F, 0F));
        PartDefinition head_bottom_inner_north_bottom_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_north_bottom_r1", CubeListBuilder.create()
                .texOffs(27, 62).addBox(-4F, 0F, 3F, 8F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 3.1416F, 0F, 0F));
        PartDefinition head_bottom_inner_north_middle_r1 = head_bottom.addOrReplaceChild("head_bottom_inner_north_middle_r1", CubeListBuilder.create()
                .texOffs(27, 62).addBox(-4F, -4F, 1F, 8F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 1.5708F, 0F, 0F));
        PartDefinition orb_north = partdefinition.addOrReplaceChild("orb_north", CubeListBuilder.create(), PartPose.offset(0F, 29F, 0F));
        PartDefinition orb_north_inner_r1 = orb_north.addOrReplaceChild("orb_north_inner_r1", CubeListBuilder.create()
                .texOffs(0, 19).addBox(9F, -9.5F, -0.5F, 2F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -15F, 0F, 0F, 1.5708F, 0F));
        PartDefinition orb_north_outer_r1 = orb_north.addOrReplaceChild("orb_north_outer_r1", CubeListBuilder.create()
                .texOffs(0, 15).addBox(-1F, -10.5F, -10F, 2F, 1F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -15F, 0F, 1.5708F, 0F, 3.1416F));
        PartDefinition orb_south = partdefinition.addOrReplaceChild("orb_south", CubeListBuilder.create(), PartPose.offset(0F, 29F, -1F));
        PartDefinition orb_south_inner_r1 = orb_south.addOrReplaceChild("orb_south_inner_r1", CubeListBuilder.create()
                .texOffs(0, 19).addBox(9.5F, -9.5F, -0.5F, 2F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -15F, 0F, 0F, -1.5708F, 0F));
        PartDefinition orb_south_outer_r1 = orb_south.addOrReplaceChild("orb_south_outer_r1", CubeListBuilder.create()
                .texOffs(0, 15).addBox(-1F, 10F, 8F, 2F, 1F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -15F, 0F, 1.5708F, 0F, 0F));
        PartDefinition orb_east = partdefinition.addOrReplaceChild("orb_east", CubeListBuilder.create(), PartPose.offset(-10F, 29F, -11F));
        PartDefinition orb_east_inner_r1 = orb_east.addOrReplaceChild("orb_east_inner_r1", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-1.5F, -9.5F, -11.5F, 2F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -15F, 0F, -3.1416F, 0F, 3.1416F));
        PartDefinition orb_east_outer_r1 = orb_east.addOrReplaceChild("orb_east_outer_r1", CubeListBuilder.create()
                .texOffs(0, 15).addBox(10F, -1F, 8F, 2F, 1F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -15F, 0F, 1.5708F, -1.5708F, 0F));
        PartDefinition orb_west = partdefinition.addOrReplaceChild("orb_west", CubeListBuilder.create(), PartPose.offsetAndRotation(10F, 29F, -8F, 0F, 1.5708F, 0F));
        PartDefinition orb_south_inner_r2 = orb_west.addOrReplaceChild("orb_south_inner_r2", CubeListBuilder.create()
                .texOffs(0, 19).addBox(9.5F, -19.5F, -0.5F, 2F, 1F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(-8F, -5F, -11F, 0F, -1.5708F, 0F));
        PartDefinition orb_south_outer_r2 = orb_west.addOrReplaceChild("orb_south_outer_r2", CubeListBuilder.create()
                .texOffs(0, 15).addBox(-1F, 10F, 18F, 2F, 1F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(-8F, -5F, -11F, 1.5708F, 0F, 0F));
        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
        PartDefinition rod_northeast_r1 = rods.addOrReplaceChild("rod_northeast_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 1.5708F, 2.3562F, 0F));
        PartDefinition rod_southeast_r1 = rods.addOrReplaceChild("rod_southeast_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 1.5708F, -2.3562F, 0F));
        PartDefinition rod_southwest_r1 = rods.addOrReplaceChild("rod_southwest_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(9.5F, -0.5F, 4F, 1F, 1F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, 1.5708F, -0.7854F, 0F));
        PartDefinition rod_northwest_r1 = rods.addOrReplaceChild("rod_northwest_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-10.5F, -0.5F, -14F, 1F, 1F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -10F, 0F, -1.5708F, -2.3562F, 0F));
        PartDefinition tentacles = partdefinition.addOrReplaceChild("tentacles", CubeListBuilder.create()
                .texOffs(0, 49).addBox(3F, -16F, -4F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(0, 49).addBox(-4F, -16F, -4F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(0, 49).addBox(3F, -16F, 3F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(0, 49).addBox(-4F, -16F, 3F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(5, 53).addBox(-0.5F, -16F, -4F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(5, 53).addBox(-4F, -16F, -0.5F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(5, 53).addBox(-0.5F, -16F, 3F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(5, 53).addBox(3F, -16F, -0.5F, 1F, 10F, 1F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
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
