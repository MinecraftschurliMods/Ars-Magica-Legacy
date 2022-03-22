package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
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

public class LifeGuardianModel <T extends LifeGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "life_guardian"), "main");
    private final ModelPart core;
    private final ModelPart rods;
    private final ModelPart circle;

    public LifeGuardianModel(ModelPart root) {
        this.core = root.getChild("core");
        this.rods = root.getChild("rods");
        this.circle = root.getChild("circle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -20.0F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 5).addBox(-2.5F, -19.0F, -3.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 13).addBox(-3.5F, -18.0F, -4.5F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 24).addBox(-4.5F, -16.0F, -5.5F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 38).addBox(-5.5F, -13.0F, -6.5F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 57).addBox(-4.5F, -7.0F, -5.5F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 69).addBox(-3.5F, -6.0F, -4.5F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 79).addBox(-2.5F, -5.0F, -3.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create().texOffs(0, 109).addBox(-0.5F, -15.0F, -8.5F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 109).addBox(-7.5F, -15.0F, -1.5F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 109).addBox(-0.5F, -15.0F, 5.5F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(0, 109).addBox(6.5F, -15.0F, -1.5F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rod_bottom_northsouth_r1 = rods.addOrReplaceChild("rod_bottom_northsouth_r1", CubeListBuilder.create().texOffs(0, 104).addBox(-6.5F, -3.1F, 1.8F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, 0.7854F, -1.5708F, 0.0F));

        PartDefinition rod_bottom_westeast_r1 = rods.addOrReplaceChild("rod_bottom_westeast_r1", CubeListBuilder.create().texOffs(0, 104).addBox(-8.5F, -4.5F, -2.4F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition rod_top_east_r1 = rods.addOrReplaceChild("rod_top_east_r1", CubeListBuilder.create().texOffs(36, 114).addBox(-2.0F, -17.7F, -11.4F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, 0.0F, 1.5708F, 0.3927F));

        PartDefinition rod_top_south_r1 = rods.addOrReplaceChild("rod_top_south_r1", CubeListBuilder.create().texOffs(36, 114).mirror().addBox(-1.0F, -17.7F, 10.4F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition rod_top_west_r1 = rods.addOrReplaceChild("rod_top_west_r1", CubeListBuilder.create().texOffs(36, 114).mirror().addBox(1.0F, -17.7F, -11.4F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, -2.0F, 0.0F, -1.5708F, -0.3927F));

        PartDefinition rod_top_north_r1 = rods.addOrReplaceChild("rod_top_north_r1", CubeListBuilder.create().texOffs(36, 114).addBox(-1.0F, -17.7F, -11.4F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition circle = partdefinition.addOrReplaceChild("circle", CubeListBuilder.create().texOffs(41, 114).addBox(-7.0F, -17.0F, -10.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
                                                                                          .texOffs(41, 114).addBox(-7.0F, -17.0F, 9.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition circle_east_r1 = circle.addOrReplaceChild("circle_east_r1", CubeListBuilder.create().texOffs(41, 114).addBox(-7.25F, -7.0F, 9.475F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
                                                                                                  .texOffs(41, 114).addBox(-7.25F, -7.0F, -9.825F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.175F, -10.0F, -0.75F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        core.render(poseStack, buffer, packedLight, packedOverlay);
        rods.render(poseStack, buffer, packedLight, packedOverlay);
        circle.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
