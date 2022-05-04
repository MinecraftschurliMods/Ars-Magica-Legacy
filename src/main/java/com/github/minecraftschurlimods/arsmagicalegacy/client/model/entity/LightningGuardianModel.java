package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
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

public class LightningGuardianModel<T extends LightningGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_guardian"), "main");
    private final ModelPart arms;
    private final ModelPart chest;
    private final ModelPart main;

    public LightningGuardianModel(ModelPart root) {
        arms = root.getChild("arms");
        chest = root.getChild("chest");
        main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create()
                .texOffs(16, 47).addBox(-4F, -15F, -0.5F, 2F, 13F, 2F, new CubeDeformation(0F))
                .texOffs(0, 49).addBox(6F, -15F, -0.5F, 2F, 13F, 2F, new CubeDeformation(0F))
                .texOffs(0, 34).addBox(-2F, -15F, -0.5F, 8F, 2F, 2F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        PartDefinition hands = arms.addOrReplaceChild("hands", CubeListBuilder.create()
                .texOffs(0, 0).addBox(8.0001F, -8F, -0.5F, 1F, 4F, 2F, new CubeDeformation(0F))
                .texOffs(32, 12).addBox(5F, -5F, -1.4999F, 4F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(56, 12).mirror().addBox(5F, -8F, -1.4999F, 4F, 1F, 1F, new CubeDeformation(0F)).mirror(false)
                .texOffs(92, 12).addBox(5F, -5F, 1.5001F, 4F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(80, 12).addBox(5F, -8F, 1.5001F, 4F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(36, 16).addBox(4.9999F, -8F, -0.5F, 1F, 1F, 2F, new CubeDeformation(0F))
                .texOffs(20, 16).addBox(4.9999F, -5F, -0.5F, 1F, 1F, 2F, new CubeDeformation(0F))
                .texOffs(7, 0).addBox(-5.0001F, -8F, -0.5F, 1F, 4F, 2F, new CubeDeformation(0F))
                .texOffs(44, 12).mirror().addBox(-5F, -5F, -1.4999F, 4F, 1F, 1F, new CubeDeformation(0F)).mirror(false)
                .texOffs(20, 12).addBox(-5F, -8F, -1.4999F, 4F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(104, 12).addBox(-5F, -5F, 1.5001F, 4F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(68, 12).addBox(-5F, -8F, 1.5001F, 4F, 1F, 1F, new CubeDeformation(0F))
                .texOffs(44, 16).addBox(-1.999F, -5F, -0.5F, 1F, 1F, 2F, new CubeDeformation(0F))
                .texOffs(28, 16).addBox(-1.999F, -8F, -0.5F, 1F, 1F, 2F, new CubeDeformation(0F)), PartPose.offset(0F, 1F, 0F));
        PartDefinition chest = partdefinition.addOrReplaceChild("chest", CubeListBuilder.create()
                .texOffs(30, 0).addBox(-1.5F, -15F, 3.001F, 7F, 5F, 0F, new CubeDeformation(0F))
                .texOffs(0, 38).addBox(-1.5F, -15.001F, -2F, 7F, 4F, 5F, new CubeDeformation(0F))
                .texOffs(15, 3).mirror().addBox(-1.5F, -17F, -2.001F, 7F, 7F, 0F, new CubeDeformation(0F)).mirror(false)
                .texOffs(15, 0).mirror().addBox(-0.5F, -19F, -2F, 5F, 2F, 0F, new CubeDeformation(0F)).mirror(false), PartPose.offset(0F, 24F, 0F));
        PartDefinition chest_right_upperupper_r1 = chest.addOrReplaceChild("chest_right_upperupper_r1", CubeListBuilder.create()
                .texOffs(74, 3).addBox(-2F, -17F, -5.5F, 3F, 1F, 0F, new CubeDeformation(0F))
                .texOffs(56, 5).addBox(-2F, -16F, -5.5F, 5F, 1F, 0F, new CubeDeformation(0F))
                .texOffs(0, 14).addBox(-2F, -13F, -5.5005F, 5F, 3F, 0F, new CubeDeformation(0F))
                .texOffs(67, 3).addBox(-2F, -17F, 1.5F, 3F, 1F, 0F, new CubeDeformation(0F))
                .texOffs(45, 5).addBox(-2F, -16F, 1.5F, 5F, 1F, 0F, new CubeDeformation(0F))
                .texOffs(0, 14).addBox(-2F, -13F, 1.5001F, 5F, 3F, 0F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, -1.5708F, 0F));
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(7, 26).addBox(0.5F, -18F, -1F, 3F, 3F, 3F, new CubeDeformation(0F))
                .texOffs(13, 45).mirror().addBox(0F, -11F, -0.5F, 4F, 4F, 2F, new CubeDeformation(0F)).mirror(false)
                .texOffs(3, 27).addBox(1F, -7F, -1.5F, 2F, 6F, 1F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        chest.render(poseStack, buffer, packedLight, packedOverlay);
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
