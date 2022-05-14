package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
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

public class LifeGuardianModel extends EntityModel<LifeGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "life_guardian"), "main");
    private final ModelPart core;
    private final ModelPart rods;
    private final ModelPart circle;

    public LifeGuardianModel(ModelPart root) {
        core = root.getChild("core");
        rods = root.getChild("rods");
        circle = root.getChild("circle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("core", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1F, -20F, -2F, 3F, 1F, 3F, new CubeDeformation(0F))
                .texOffs(0, 5).addBox(-2.5F, -19F, -3.5F, 6F, 1F, 6F, new CubeDeformation(0F))
                .texOffs(0, 13).addBox(-3.5F, -18F, -4.5F, 8F, 2F, 8F, new CubeDeformation(0F))
                .texOffs(0, 24).addBox(-4.5F, -16F, -5.5F, 10F, 3F, 10F, new CubeDeformation(0F))
                .texOffs(0, 38).addBox(-5.5F, -13F, -6.5F, 12F, 6F, 12F, new CubeDeformation(0F))
                .texOffs(0, 57).addBox(-4.5F, -7F, -5.5F, 10F, 1F, 10F, new CubeDeformation(0F))
                .texOffs(0, 69).addBox(-3.5F, -6F, -4.5F, 8F, 1F, 8F, new CubeDeformation(0F))
                .texOffs(0, 79).addBox(-2.5F, -5F, -3.5F, 6F, 1F, 6F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create()
                .texOffs(0, 109).addBox(-0.5F, -15F, -8.5F, 2F, 11F, 2F, new CubeDeformation(0F))
                .texOffs(0, 109).addBox(-7.5F, -15F, -1.5F, 2F, 11F, 2F, new CubeDeformation(0F))
                .texOffs(0, 109).addBox(-0.5F, -15F, 5.5F, 2F, 11F, 2F, new CubeDeformation(0F))
                .texOffs(0, 109).addBox(6.5F, -15F, -1.5F, 2F, 11F, 2F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        rods.addOrReplaceChild("rod_bottom_northsouth", CubeListBuilder.create()
                .texOffs(0, 104).addBox(-6.5F, -3.1F, 1.8F, 16F, 2F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(1F, 0F, -2F, 0.7854F, -1.5708F, 0F));
        rods.addOrReplaceChild("rod_bottom_westeast", CubeListBuilder.create()
                .texOffs(0, 104).addBox(-8.5F, -4.5F, -2.4F, 16F, 2F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(1F, 0F, -2F, -0.7854F, 0F, 0F));
        rods.addOrReplaceChild("rod_top_east", CubeListBuilder.create()
                .texOffs(36, 114).addBox(-2F, -17.7F, -11.4F, 1F, 7F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(-1F, 0F, -2F, 0F, 1.5708F, 0.3927F));
        rods.addOrReplaceChild("rod_top_south", CubeListBuilder.create()
                .texOffs(36, 114).mirror().addBox(-1F, -17.7F, 10.4F, 1F, 7F, 1F, new CubeDeformation(0F)).mirror(false), PartPose.offsetAndRotation(1F, 0F, 1F, 0.3927F, 0F, 0F));
        rods.addOrReplaceChild("rod_top_west", CubeListBuilder.create()
                .texOffs(36, 114).mirror().addBox(1F, -17.7F, -11.4F, 1F, 7F, 1F, new CubeDeformation(0F)).mirror(false), PartPose.offsetAndRotation(2F, 0F, -2F, 0F, -1.5708F, -0.3927F));
        rods.addOrReplaceChild("rod_top_north", CubeListBuilder.create()
                .texOffs(36, 114).addBox(-1F, -17.7F, -11.4F, 1F, 7F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(1F, 0F, -2F, -0.3927F, 0F, 0F));
        PartDefinition circle = partdefinition.addOrReplaceChild("circle", CubeListBuilder.create()
                .texOffs(41, 114).addBox(-7F, -17F, -10F, 14F, 14F, 0F, new CubeDeformation(0F))
                .texOffs(41, 114).addBox(-7F, -17F, 9F, 14F, 14F, 0F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        circle.addOrReplaceChild("circle_east", CubeListBuilder.create()
                .texOffs(41, 114).addBox(-7.25F, -7F, 9.475F, 14F, 14F, 0F, new CubeDeformation(0F))
                .texOffs(41, 114).addBox(-7.25F, -7F, -9.825F, 14F, 14F, 0F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0.175F, -10F, -0.75F, 0F, -1.5708F, 0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(LifeGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        core.render(poseStack, buffer, packedLight, packedOverlay);
        rods.render(poseStack, buffer, packedLight, packedOverlay);
        circle.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
