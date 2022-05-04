package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
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
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature_guardian"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rods;
    private final ModelPart main;

    public NatureGuardianModel(ModelPart root) {
        head = root.getChild("head");
        body = root.getChild("body");
        rods = root.getChild("rods");
        main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(65, 24).mirror().addBox(-7F, -64F, 1F, 8F, 6F, 12F, new CubeDeformation(0F)).mirror(false), PartPose.offset(3F, 24F, -7F));
        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create()
                .texOffs(0, 64).mirror().addBox(-5F, -65F, 5F, 4F, 2F, 20F, new CubeDeformation(0F)).mirror(false)
                .texOffs(7, 51).mirror().addBox(-4F, -61F, 10F, 2F, 2F, 10F, new CubeDeformation(0F)).mirror(false)
                .texOffs(5, 49).mirror().addBox(0F, -65F, 7F, 2F, 2F, 12F, new CubeDeformation(0F)).mirror(false)
                .texOffs(5, 49).mirror().addBox(-8F, -65F, 7F, 2F, 2F, 12F, new CubeDeformation(0F)).mirror(false)
                .texOffs(5, 38).mirror().addBox(0F, -62.5F, 9F, 2F, 2F, 8F, new CubeDeformation(0F)).mirror(false)
                .texOffs(5, 38).mirror().addBox(-8F, -62.5F, 7F, 2F, 2F, 8F, new CubeDeformation(0F)).mirror(false)
                .texOffs(5, 30).mirror().addBox(0F, -59.5F, 10F, 2F, 2F, 5F, new CubeDeformation(0F)).mirror(false)
                .texOffs(5, 30).mirror().addBox(-8F, -59.5F, 10F, 2F, 2F, 5F, new CubeDeformation(0F)).mirror(false), PartPose.offset(0F, 0F, 0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(36, 0).addBox(-3F, 11F, -6.5F, 8F, 8F, 8F, new CubeDeformation(0F))
                .texOffs(69, 19).addBox(-7F, -19.5F, -3.5F, 16F, 2F, 2F, new CubeDeformation(0F)), PartPose.offset(-1F, -10F, 3F));
        PartDefinition body4_r1 = body.addOrReplaceChild("body4_r1", CubeListBuilder.create()
                .texOffs(36, 17).addBox(-4.5F, 20.615F, -6.5F, 7F, 8F, 7F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -17F, 4F, -0.1309F, 0F, 0F));
        PartDefinition body3_r1 = body.addOrReplaceChild("body3_r1", CubeListBuilder.create()
                .texOffs(36, 33).addBox(-4F, 12F, -8.3F, 6F, 8F, 6F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -17F, 4F, -0.0175F, 0F, 0F));
        PartDefinition body2_r1 = body.addOrReplaceChild("body2_r1", CubeListBuilder.create()
                .texOffs(36, 0).addBox(-5F, 3.615F, -10.5F, 8F, 8F, 8F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -17F, 4F, 0.0873F, 0F, 0F));
        PartDefinition body1_r1 = body.addOrReplaceChild("body1_r1", CubeListBuilder.create()
                .texOffs(69, 0).addBox(-7F, -4F, -11.4F, 12F, 8F, 10F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -17F, 4F, 0.0436F, 0F, 0F));
        PartDefinition arm = body.addOrReplaceChild("arm", CubeListBuilder.create()
                .texOffs(0, 9).addBox(8F, -54.5F, -2.5F, 4F, 14F, 6F, new CubeDeformation(0F))
                .texOffs(114, 0).addBox(8.5F, -40.5F, -1.5F, 3F, 12F, 4F, new CubeDeformation(0F))
                .texOffs(0, 9).addBox(-12F, -54.5F, -2.5F, 4F, 14F, 6F, new CubeDeformation(0F))
                .texOffs(114, 0).addBox(-11.5F, -40.5F, -1.5F, 3F, 12F, 4F, new CubeDeformation(0F)), PartPose.offset(1F, 34F, -3F));
        PartDefinition rods = partdefinition.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0F, 16F, 0F));
        PartDefinition rod_southeast_r1 = rods.addOrReplaceChild("rod_southeast_r1", CubeListBuilder.create()
                .texOffs(0, 30).addBox(2F, -0.375F, 2F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(0, 30).addBox(2F, -0.375F, -3F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(0, 30).addBox(-3F, -0.375F, 2F, 1F, 14F, 1F, new CubeDeformation(0F))
                .texOffs(0, 30).addBox(-3F, -0.375F, -3F, 1F, 14F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -6.625F, 0.5F, 0F, -1.5708F, 0F));
        PartDefinition rod_east_bottom_r1 = rods.addOrReplaceChild("rod_east_bottom_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, 9.625F, 2F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-0.5F, 4.625F, 2F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(2F, 4.625F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(2F, 9.625F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-0.5F, 9.625F, -3F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-0.5F, 4.625F, -3F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-3F, 9.625F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-3F, 4.625F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -6.625F, 0.5F, 0F, -1.5708F, 0F));
        PartDefinition rod_east_top_r1 = rods.addOrReplaceChild("rod_east_top_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -0.375F, 2F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(2F, -0.375F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-0.5F, -0.375F, -3F, 1F, 5F, 1F, new CubeDeformation(0F))
                .texOffs(0, 0).addBox(-3F, -0.375F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, -6.625F, 0.5F, 0F, -1.5708F, 0F));
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(36, 48).mirror().addBox(-2F, -58F, -2F, 4F, 4F, 4F, new CubeDeformation(0F)).mirror(false), PartPose.offset(0F, 24F, 0F));
        PartDefinition core_r1 = main.addOrReplaceChild("core_r1", CubeListBuilder.create()
                .texOffs(5, 0).addBox(-5.5F, -3F, 0F, 4F, 4F, 4F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -8F, 4F, 0F, -1.5708F, 0F));
        PartDefinition shield_r1 = main.addOrReplaceChild("shield_r1", CubeListBuilder.create()
                .texOffs(84, 45).addBox(-7F, -17.5F, -18F, 20F, 30F, 2F, new CubeDeformation(0F)), PartPose.offsetAndRotation(2F, -38F, 4F, 0F, 1.5708F, 0F));
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
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
