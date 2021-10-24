package com.github.minecraftschurli.arsmagicalegacy.client.model;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.EarthGuardian;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class EarthGuardianModel<T extends EarthGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "earth_guardian"), "main");
    private final ModelPart shoulder;
    private final ModelPart arms;
    private final ModelPart rods;
    private final ModelPart bb_main;

    public EarthGuardianModel(ModelPart root) {
        this.shoulder = root.getChild("shoulder");
        this.arms = root.getChild("arms");
        this.rods = root.getChild("rods");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition shoulder = partdefinition.addOrReplaceChild("shoulder", CubeListBuilder.create(), PartPose.offset(4.0F, 24.0F, 0.0F));

        PartDefinition shoulder_pad_left_r1 = shoulder.addOrReplaceChild("shoulder_pad_left_r1", CubeListBuilder.create().texOffs(0, 48).addBox(-0.5F, -12.5F, -1.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -19.0F, -4.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition shoulder_left_r1 = shoulder.addOrReplaceChild("shoulder_left_r1", CubeListBuilder.create().texOffs(0, 31).addBox(10.0F, -5.5F, 0.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -19.0F, -4.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition shoulder_pad_right_r1 = shoulder.addOrReplaceChild("shoulder_pad_right_r1", CubeListBuilder.create().texOffs(0, 48).addBox(-5.5F, -12.5F, -1.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -19.0F, -4.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition shoulder_right_r1 = shoulder.addOrReplaceChild("shoulder_right_r1", CubeListBuilder.create().texOffs(0, 31).addBox(-18.0F, -5.5F, 0.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -4.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(33, 18).addBox(-10.55F, -26.15F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(50, 17).addBox(-10.05F, -18.15F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(33, 18).addBox(6.55F, -26.15F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(50, 17).addBox(7.05F, -18.15F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create().texOffs(42, 31).addBox(2.0F, -7.0F, 1.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 31).addBox(-3.0F, -7.0F, 1.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 31).addBox(2.0F, -7.0F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(42, 31).addBox(-3.0F, -7.0F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 31).addBox(-1.0F, -10.0F, 3.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, -4.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -39.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-2.0F, -33.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-5.0F, -32.0F, -3.0F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition core_r1 = bb_main.addOrReplaceChild("core_r1", CubeListBuilder.create().texOffs(33, 2).addBox(1.0F, -7.0F, -8.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -19.0F, -4.0F, 0.0F, -1.5708F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        shoulder.render(poseStack, buffer, packedLight, packedOverlay);
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        rods.render(poseStack, buffer, packedLight, packedOverlay);
        bb_main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
