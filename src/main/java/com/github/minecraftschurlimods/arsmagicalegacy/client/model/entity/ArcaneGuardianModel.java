package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
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

public class ArcaneGuardianModel <T extends ArcaneGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "arcane_guardian"), "main");
    private final ModelPart head;
    private final ModelPart arms;
    private final ModelPart core;
    private final ModelPart legs;
    private final ModelPart bb_main;

    public ArcaneGuardianModel(ModelPart root) {
        this.head = root.getChild("head");
        this.arms = root.getChild("arms");
        this.core = root.getChild("core");
        this.legs = root.getChild("legs");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(100, 44).addBox(-3.0F, -34.0F, -3.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(95, 116).addBox(-0.5F, -27.0F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hood = head.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(94, 34).addBox(-4.0F, -35.0F, -3.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                                                                            .texOffs(91, 17).addBox(-4.0F, -34.0F, -3.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                                                                            .texOffs(110, 17).addBox(4.0F, -34.0F, -3.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                                                                            .texOffs(112, 7).addBox(-3.0F, -34.0F, 4.0F, 7.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(103, 68).addBox(-4.0F, -19.0F, -5.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(69, 68).addBox(-4.0F, -14.0F, -5.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 17.0F, 3.5F));

        PartDefinition arm_lower_left_r1 = arms.addOrReplaceChild("arm_lower_left_r1", CubeListBuilder.create().texOffs(86, 68).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, -12.8891F, -6.1464F, 0.0F, 0.7854F, 1.5708F));

        PartDefinition arm_upper_left_r1 = arms.addOrReplaceChild("arm_upper_left_r1", CubeListBuilder.create().texOffs(103, 68).addBox(0.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -16.0711F, -2.9645F, -0.7854F, 0.0F, 0.0F));

        PartDefinition hand = arms.addOrReplaceChild("hand", CubeListBuilder.create(), PartPose.offset(10.0F, -2.8891F, -3.1464F));

        PartDefinition hand_right_r1 = hand.addOrReplaceChild("hand_right_r1", CubeListBuilder.create().texOffs(106, 78).addBox(-1.0F, -1.0F, -6.3333F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -11.4442F, 0.1464F, 1.5708F, 0.0F, 0.0F));

        PartDefinition hand_left_r1 = hand.addOrReplaceChild("hand_left_r1", CubeListBuilder.create().texOffs(113, 78).addBox(-3.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.0F, -3.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(103, 8).addBox(-0.9716F, -15.3716F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.4716F, 16.3716F, 0.5F));

        PartDefinition core_4_r1 = core.addOrReplaceChild("core_4_r1", CubeListBuilder.create().texOffs(118, 1).addBox(-2.0015F, -1.9186F, -0.5327F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0364F, -12.0515F, 0.0327F, 0.0F, 0.0F, -0.7854F));

        PartDefinition core_3_r1 = core.addOrReplaceChild("core_3_r1", CubeListBuilder.create().texOffs(118, 1).addBox(-1.9048F, -1.8911F, -0.5489F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0364F, -12.0515F, 0.0327F, 0.6155F, 0.5236F, 0.9553F));

        PartDefinition core_2_r1 = core.addOrReplaceChild("core_2_r1", CubeListBuilder.create().texOffs(118, 1).addBox(-1.9989F, -1.916F, -0.4636F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0364F, -12.0515F, 0.0327F, 1.5708F, -0.7854F, -1.5708F));

        PartDefinition core_1_r1 = core.addOrReplaceChild("core_1_r1", CubeListBuilder.create().texOffs(118, 1).addBox(-2.0103F, -1.9275F, -0.4974F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0364F, -12.0515F, 0.0327F, 0.6155F, -0.5236F, -0.9553F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(3.5F, 19.0F, -0.5F));

        PartDefinition leg_right_r1 = legs.addOrReplaceChild("leg_right_r1", CubeListBuilder.create().texOffs(120, 68).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -9.0F, 2.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_left_r1 = legs.addOrReplaceChild("leg_left_r1", CubeListBuilder.create().texOffs(120, 68).addBox(-1.0F, 0.0825F, -1.0571F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -9.0F, 1.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition robe = legs.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(104, 83).addBox(-4.0F, -20.5F, -0.5F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 7.5F, -0.5F));

        PartDefinition robe_south_right_r1 = robe.addOrReplaceChild("robe_south_right_r1", CubeListBuilder.create().texOffs(88, 105).addBox(-2.0F, 0.4977F, -0.5834F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                                                                                                          .texOffs(99, 105).addBox(2.0F, 0.4977F, -0.5834F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -17.0F, 3.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition robe_west_r1 = robe.addOrReplaceChild("robe_west_r1", CubeListBuilder.create().texOffs(122, 93).addBox(-0.5F, -0.6281F, -0.8524F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                                                                                            .texOffs(113, 92).addBox(-7.5F, -0.5281F, -1.3524F, 1.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -16.0F, 1.5F, 0.0873F, 0.0F, 0.0F));

        PartDefinition robe_front_r1 = robe.addOrReplaceChild("robe_front_r1", CubeListBuilder.create().texOffs(110, 105).addBox(-4.0F, -0.5152F, -0.6514F, 8.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(104, 59).addBox(-3.5F, -26.0F, -1.5F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                                                                                            .texOffs(91, 120).addBox(-1.5F, -23.0F, -3.5F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                                                                                            .texOffs(0, 0).addBox(-21.5F, -42.0F, 10.5F, 44.0F, 44.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition wand_r1 = bb_main.addOrReplaceChild("wand_r1", CubeListBuilder.create().texOffs(98, 97).addBox(-0.5F, -0.5F, -12.3333F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -21.3333F, 0.5F, 1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        core.render(poseStack, buffer, packedLight, packedOverlay);
        legs.render(poseStack, buffer, packedLight, packedOverlay);
        bb_main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
