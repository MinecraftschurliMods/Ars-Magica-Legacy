package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
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

public class AirGuardianModel<T extends AirGuardian> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "air_guardian"), "main");
    private final ModelPart arms;
    private final ModelPart core;
    private final ModelPart main;

    public AirGuardianModel(ModelPart root) {
        arms = root.getChild("arms");
        core = root.getChild("core");
        main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
        PartDefinition shoulder = arms.addOrReplaceChild("shoulder", CubeListBuilder.create()
                .texOffs(0, 56).addBox(3.5F, -22F, 2F, 4F, 4F, 4F, new CubeDeformation(0F))
                .texOffs(0, 47).addBox(-6.5F, -22F, 2F, 4F, 4F, 4F, new CubeDeformation(0F)), PartPose.offset(0F, 0F, 0F));
        PartDefinition arm = arms.addOrReplaceChild("arm", CubeListBuilder.create()
                .texOffs(17, 55).addBox(4F, -18F, 2.5F, 3F, 6F, 3F, new CubeDeformation(0F))
                .texOffs(17, 55).mirror().addBox(-6F, -18F, 2.5F, 3F, 6F, 3F, new CubeDeformation(0F)).mirror(false), PartPose.offset(0F, 0F, 0F));
        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create()
                .texOffs(21, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, new CubeDeformation(0F))
                .texOffs(21, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, new CubeDeformation(0F))
                .texOffs(21, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, new CubeDeformation(0F)), PartPose.offset(0.5F, 21F, 4F));
        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
                .texOffs(17, 17).addBox(-3F, -29F, 0.5F, 7F, 7F, 7F, new CubeDeformation(0F))
                .texOffs(0, 32).addBox(-2.5F, -22F, 1F, 6F, 8F, 6F, new CubeDeformation(0F))
                .texOffs(0, 21).addBox(-1.5F, -14F, 2F, 4F, 6F, 4F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        arms.render(poseStack, buffer, packedLight, packedOverlay);
        core.render(poseStack, buffer, packedLight, packedOverlay);
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
