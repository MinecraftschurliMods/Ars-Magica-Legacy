package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
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

public class NatureGuardianModel<T extends NatureGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "nature_guardian"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rods;
    private final ModelPart bb_main;

    public NatureGuardianModel(ModelPart root) {
        head = root.getChild("head");
        body = root.getChild("body");
        rods = root.getChild("rods");
        bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(65, 24).mirror().addBox(-7.0F, -64.0F, 1.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 24.0F, -7.0F));
        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-5.0F, -65.0F, 5.0F, 4.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(7, 51).mirror().addBox(-4.0F, -61.0F, 10.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 49).mirror().addBox(0.0F, -65.0F, 7.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 49).mirror().addBox(-8.0F, -65.0F, 7.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 38).mirror().addBox(0.0F, -62.5F, 9.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 38).mirror().addBox(-8.0F, -62.5F, 7.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 30).mirror().addBox(0.0F, -59.5F, 10.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 30).mirror().addBox(-8.0F, -59.5F, 10.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 0).addBox(-3.0F, 11.0F, -6.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(69, 19).addBox(-7.0F, -19.5F, -3.5F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -10.0F, 3.0F));
        PartDefinition body4_r1 = body.addOrReplaceChild("body4_r1", CubeListBuilder.create().texOffs(36, 17).addBox(-4.5F, 20.615F, -6.5F, 7.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -17.0F, 4.0F, -0.1309F, 0.0F, 0.0F));
        PartDefinition body3_r1 = body.addOrReplaceChild("body3_r1", CubeListBuilder.create().texOffs(36, 33).addBox(-4.0F, 12.0F, -8.3F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -17.0F, 4.0F, -0.0175F, 0.0F, 0.0F));
        PartDefinition body2_r1 = body.addOrReplaceChild("body2_r1", CubeListBuilder.create().texOffs(36, 0).addBox(-5.0F, 3.615F, -10.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -17.0F, 4.0F, 0.0873F, 0.0F, 0.0F));
        PartDefinition body1_r1 = body.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(69, 0).addBox(-7.0F, -4.0F, -11.4F, 12.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -17.0F, 4.0F, 0.0436F, 0.0F, 0.0F));
        PartDefinition arm = body.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(0, 9).addBox(8.0F, -54.5F, -2.5F, 4.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(114, 0).addBox(8.5F, -40.5F, -1.5F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(-12.0F, -54.5F, -2.5F, 4.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(114, 0).addBox(-11.5F, -40.5F, -1.5F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 34.0F, -3.0F));
        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition rod_southeast_r1 = rods.addOrReplaceChild("rod_southeast_r1", CubeListBuilder.create().texOffs(0, 30).addBox(2.0F, -0.375F, 2.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(2.0F, -0.375F, -3.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-3.0F, -0.375F, 2.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-3.0F, -0.375F, -3.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.625F, 0.5F, 0.0F, -1.5708F, 0.0F));
        PartDefinition rod_east_bottom_r1 = rods.addOrReplaceChild("rod_east_bottom_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 9.625F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, 4.625F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, 4.625F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, 9.625F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, 9.625F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, 4.625F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, 9.625F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, 4.625F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.625F, 0.5F, 0.0F, -1.5708F, 0.0F));
        PartDefinition rod_east_top_r1 = rods.addOrReplaceChild("rod_east_top_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.375F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, -0.375F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -0.375F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -0.375F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.625F, 0.5F, 0.0F, -1.5708F, 0.0F));
        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(36, 48).mirror().addBox(-2.0F, -58.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition core_r1 = bb_main.addOrReplaceChild("core_r1", CubeListBuilder.create().texOffs(5, 0).addBox(-5.5F, -3.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -8.0F, 4.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition shield_r1 = bb_main.addOrReplaceChild("shield_r1", CubeListBuilder.create().texOffs(84, 45).addBox(-7.0F, -17.5F, -18.0F, 20.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -38.0F, 4.0F, 0.0F, 1.5708F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        rods.render(poseStack, buffer, packedLight, packedOverlay);
        bb_main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
