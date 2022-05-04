package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
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

public class EnderGuardianModel extends EntityModel<EnderGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_guardian"), "main");
    private final ModelPart spine;
    private final ModelPart arms;
    private final ModelPart wings;
    private final ModelPart main;

    public EnderGuardianModel(ModelPart root) {
        spine = root.getChild("spine");
        arms = root.getChild("arms");
        wings = root.getChild("wings");
        main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition spine = partdefinition.addOrReplaceChild("spine", CubeListBuilder.create()
                .texOffs(0, 17).addBox(-4F, -33F, -4F, 4F, 14F, 4F, new CubeDeformation(0F)), PartPose.offset(2F, 29F, 3F));
        PartDefinition ribs = spine.addOrReplaceChild("ribs", CubeListBuilder.create()
                .texOffs(0, 41).addBox(-6F, -30F, -3F, 8F, 2F, 2F, new CubeDeformation(0F))
                .texOffs(0, 41).addBox(-6F, -27F, -3F, 8F, 2F, 2F, new CubeDeformation(0F))
                .texOffs(0, 41).addBox(-6F, -24F, -3F, 8F, 2F, 2F, new CubeDeformation(0F)), PartPose.offset(0F, 0F, 0F));
        PartDefinition backbone = spine.addOrReplaceChild("backbone", CubeListBuilder.create()
                .texOffs(0, 93).addBox(-3F, -32F, 0F, 2F, 2F, 1F, new CubeDeformation(0F))
                .texOffs(0, 93).addBox(-3F, -29F, 0F, 2F, 2F, 1F, new CubeDeformation(0F))
                .texOffs(0, 93).addBox(-3F, -26F, 0F, 2F, 2F, 1F, new CubeDeformation(0F))
                .texOffs(0, 93).addBox(-3F, -23F, 0F, 2F, 2F, 1F, new CubeDeformation(0F)), PartPose.offset(0F, 0F, 0F));
        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create()
                .texOffs(9, 80).addBox(4F, -22F, -3F, 2F, 10F, 2F, new CubeDeformation(0F))
                .texOffs(0, 80).addBox(-10F, -22F, -3F, 2F, 10F, 2F, new CubeDeformation(0F))
                .texOffs(11, 65).addBox(4F, -33F, -3.5F, 2F, 11F, 3F, new CubeDeformation(0F))
                .texOffs(0, 65).addBox(-10F, -33F, -3.5F, 2F, 11F, 3F, new CubeDeformation(0F)), PartPose.offset(2F, 29F, 3F));
        PartDefinition wings = partdefinition.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
        PartDefinition wing_right_r1 = wings.addOrReplaceChild("wing_right_r1", CubeListBuilder.create()
                .texOffs(0, 107).mirror().addBox(-7.5F, 0.765F, 0.3386F, 15F, 20F, 0F, new CubeDeformation(0F)).mirror(false), PartPose.offsetAndRotation(-11.514F, -26.2328F, -1.5F, 0F, -1.5708F, 0.3491F));
        PartDefinition wing_left_r1 = wings.addOrReplaceChild("wing_left_r1", CubeListBuilder.create()
                .texOffs(0, 107).addBox(-7.5F, 0.765F, 0.3386F, 15F, 20F, 0F, new CubeDeformation(0F)), PartPose.offsetAndRotation(11.2275F, -26.2299F, -1.5F, 0F, 1.5708F, -0.3491F));
        PartDefinition wingbone = wings.addOrReplaceChild("wingbone", CubeListBuilder.create(), PartPose.offset(-3F, 15F, 0F));
        PartDefinition wingbone_upper_right_r1 = wingbone.addOrReplaceChild("wingbone_upper_right_r1", CubeListBuilder.create()
                .texOffs(0, 102).addBox(-1F, -1F, -1F, 14F, 2F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(-8.4503F, -41.4876F, 6F, 0F, 1.5708F, 0.3491F));
        PartDefinition wingbone_upper_left_r1 = wingbone.addOrReplaceChild("wingbone_upper_left_r1", CubeListBuilder.create()
                .texOffs(0, 102).addBox(-13F, -1F, -1F, 14F, 2F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(14.4503F, -41.4876F, 6F, 0F, -1.5708F, -0.3491F));
        PartDefinition wingbone_lower_right_r1 = wingbone.addOrReplaceChild("wingbone_lower_right_r1", CubeListBuilder.create()
                .texOffs(0, 97).addBox(0.37F, -1F, -1.4039F, 10F, 2F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0.1568F, -42F, 2.5159F, 3.1416F, -0.3054F, 3.1416F));
        PartDefinition wingbone_lower_left_r1 = wingbone.addOrReplaceChild("wingbone_lower_left_r1", CubeListBuilder.create()
                .texOffs(0, 97).addBox(-10.37F, -1F, -1.4039F, 10F, 2F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(5.8432F, -42F, 2.5159F, 3.1416F, 0.3054F, -3.1416F));
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4F, -36F, -3F, 8F, 8F, 8F, new CubeDeformation(0F))
                .texOffs(0, 36).addBox(-6F, -28F, 0F, 12F, 2F, 2F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        PartDefinition tail_r1 = main.addOrReplaceChild("tail_r1", CubeListBuilder.create()
                .texOffs(0, 46).addBox(-3F, -4.5F, 0.3F, 2F, 16F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -10F, 3F, 0.5236F, 0F, 0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(EnderGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        spine.render(poseStack, buffer, packedLight, packedOverlay);
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        wings.render(poseStack, buffer, packedLight, packedOverlay);
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
