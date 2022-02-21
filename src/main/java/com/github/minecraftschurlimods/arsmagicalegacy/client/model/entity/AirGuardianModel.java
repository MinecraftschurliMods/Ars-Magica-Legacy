package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
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

public class AirGuardianModel <T extends AirGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "air_guardian"), "main");
    private final       ModelPart          arms;
    private final ModelPart core;
    private final ModelPart bb_main;

    public AirGuardianModel(ModelPart root) {
        this.arms = root.getChild("arms");
        this.core = root.getChild("core");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition shoulder = arms.addOrReplaceChild("shoulder", CubeListBuilder.create().texOffs(0, 56).addBox(3.5F, -22.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                                                                                    .texOffs(0, 47).addBox(-6.5F, -22.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm = arms.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(17, 55).addBox(4.0F, -18.0F, 2.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                                                                          .texOffs(17, 55).mirror().addBox(-6.0F, -18.0F, 2.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                                                                                      .texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 21.0F, 4.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(17, 17).addBox(-3.0F, -29.0F, 0.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                                                                                            .texOffs(0, 32).addBox(-2.5F, -22.0F, 1.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                                                                                            .texOffs(0, 21).addBox(-1.5F, -14.0F, 2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        core.render(poseStack, buffer, packedLight, packedOverlay);
        bb_main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
