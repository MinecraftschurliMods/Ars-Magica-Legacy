package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
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

public class EarthGuardianModel extends EntityModel<EarthGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth_guardian"), "main");
    private final ModelPart shoulder;
    private final ModelPart arms;
    private final ModelPart rods;
    private final ModelPart main;

    public EarthGuardianModel(ModelPart root) {
        shoulder = root.getChild("shoulder");
        arms = root.getChild("arms");
        rods = root.getChild("rods");
        main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition shoulder = partdefinition.addOrReplaceChild("shoulder", CubeListBuilder.create(), PartPose.offset(4F, 24F, 0F));
        PartDefinition shoulder_pad_left_r1 = shoulder.addOrReplaceChild("shoulder_pad_left_r1", CubeListBuilder.create()
                .texOffs(0, 48).addBox(-0.5F, -12.5F, -1F, 6F, 6F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(6F, -19F, -4F, 0F, 0F, -0.6109F));
        PartDefinition shoulder_left_r1 = shoulder.addOrReplaceChild("shoulder_left_r1", CubeListBuilder.create()
                .texOffs(0, 31).addBox(10F, -5.5F, 0F, 8F, 8F, 8F, new CubeDeformation(0F)), PartPose.offsetAndRotation(-8F, -19F, -4F, 0F, 0F, -0.6109F));
        PartDefinition shoulder_pad_right_r1 = shoulder.addOrReplaceChild("shoulder_pad_right_r1", CubeListBuilder.create()
                .texOffs(0, 48).addBox(-5.5F, -12.5F, -1F, 6F, 6F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(-14F, -19F, -4F, 0F, 0F, 0.6109F));
        PartDefinition shoulder_right_r1 = shoulder.addOrReplaceChild("shoulder_right_r1", CubeListBuilder.create()
                .texOffs(0, 31).addBox(-18F, -5.5F, 0F, 8F, 8F, 8F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -19F, -4F, 0F, 0F, 0.6109F));
        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create()
                .texOffs(33, 18).addBox(-10.55F, -26.15F, -2F, 4F, 8F, 4F, new CubeDeformation(0F))
                .texOffs(50, 17).addBox(-10.05F, -18.15F, -1.5F, 3F, 10F, 3F, new CubeDeformation(0F))
                .texOffs(33, 18).addBox(6.55F, -26.15F, -2F, 4F, 8F, 4F, new CubeDeformation(0F))
                .texOffs(50, 17).addBox(7.05F, -18.15F, -1.5F, 3F, 10F, 3F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create()
                .texOffs(42, 31).addBox(2F, -7F, 1F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(42, 31).addBox(-3F, -7F, 1F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(42, 31).addBox(2F, -7F, 6F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(42, 31).addBox(-3F, -7F, 6F, 1F, 10F, 1F, new CubeDeformation(0F))
                .texOffs(33, 31).addBox(-1F, -10F, 3F, 2F, 16F, 2F, new CubeDeformation(0F)), PartPose.offset(0F, 17F, -4F));
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4F, -39F, -4F, 8F, 6F, 8F, new CubeDeformation(0F))
                .texOffs(0, 15).addBox(-2F, -33F, -2F, 4F, 1F, 4F, new CubeDeformation(0F))
                .texOffs(0, 21).addBox(-5F, -32F, -3F, 10F, 3F, 6F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        PartDefinition core_r1 = main.addOrReplaceChild("core_r1", CubeListBuilder.create()
                .texOffs(33, 2).addBox(1F, -7F, -8F, 6F, 6F, 6F, new CubeDeformation(0F)), PartPose.offsetAndRotation(4F, -19F, -4F, 0F, -1.5708F, -1.5708F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(EarthGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        shoulder.render(poseStack, buffer, packedLight, packedOverlay);
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        rods.render(poseStack, buffer, packedLight, packedOverlay);
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
