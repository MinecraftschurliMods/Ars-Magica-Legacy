package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownRock;
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

public class ThrownRockModel extends EntityModel<ThrownRock> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "thrown_rock"), "main");
    private final ModelPart rock1;
    private final ModelPart rock2;
    private final ModelPart rock3;

    public ThrownRockModel(ModelPart root) {
        rock1 = root.getChild("rock1");
        rock2 = root.getChild("rock2");
        rock3 = root.getChild("rock3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        ModelUtil.addCube(pd, "rock1", 1, 24, -7, -3, -2.999F, 8, 6, 7, 0, 1, 0);
        ModelUtil.addCube(pd, "rock2", 1, 23, 1, -3.5f, -4, 7, 7, 8, 0, 0.5f, 0);
        ModelUtil.addCube(pd, "rock3", 1, 24, -3, -3, -5, 8, 6, 7, 0, 3, 0);
        return LayerDefinition.create(md, 64, 64);
    }

    @Override
    public void setupAnim(ThrownRock entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rock1.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        rock2.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        rock3.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
