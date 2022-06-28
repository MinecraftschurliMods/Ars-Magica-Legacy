package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AirGuardianModel extends AMEntityModel<AirGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "air_guardian"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart core1;
    private final ModelPart core2;
    private final ModelPart core3;
    private final ModelPart rightShoulder;
    private final ModelPart rightArm;
    private final ModelPart leftShoulder;
    private final ModelPart leftArm;

    public AirGuardianModel(ModelPart root) {
        head = addPart(root, "head");
        body = addPart(root, "body");
        tail = addPart(root, "tail");
        core1 = addPart(root, "core1");
        core2 = addPart(root, "core2");
        core3 = addPart(root, "core3");
        rightShoulder = addPart(root, "right_shoulder");
        rightArm = addPart(root, "right_arm");
        leftShoulder = addPart(root, "left_shoulder");
        leftArm = addPart(root, "left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 0, 0, -3.5f, -7, -3.5f, 7, 7, 7, 0, -6, 0);
        addCube(pd, "body", 40, 0, -3, 0, -3, 6, 8, 6, 0, -6, 0);
        addCube(pd, "tail", 40, 14, -2, 0, -2, 4, 6, 4, 0, 2, 0);
        addCube(pd, "core1", 0, 14, -3, -3, -3, 6, 6, 6, 0, 16, 0);
        addCube(pd, "core2", 0, 14, -3, -3, -3, 6, 6, 6, 0, 16, 0);
        addCube(pd, "core3", 0, 14, -3, -3, -3, 6, 6, 6, 0, 16, 0);
        addCube(pd, "right_shoulder", 24, 14, -7, -2, -2, 4, 4, 4, 0, -4, 0);
        addCube(pd, "right_arm", 28, 5, -6.5f, 2, -1.5f, 3, 6, 3, 0, -4, 0);
        addCube(pd, "left_shoulder", 24, 22, 3, -2, -2, 4, 4, 4, 0, -4, 0);
        addCube(pd, "left_arm", 28, 5, 3.5f, 2, -1.5f, 3, 6, 3, 0, -4, 0);
        return LayerDefinition.create(md, 64, 32);
    }

    @Override
    public void setupAnim(AirGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setHeadRotations(headPitch, netHeadYaw, head);
        float y = 45 * (Mth.cos(ageInTicks % 360 * (float) Math.PI / 45f) - Mth.cos((ageInTicks - 1) % 360 * (float) Math.PI / 45f));
        head.y = -6 + y;
        body.y = -6 + y;
        tail.y = 2 + y;
        core1.y = 16 + y;
        core2.y = 16 + y;
        core3.y = 16 + y;
        rightShoulder.y = -4 + y;
        rightArm.y = -4 + y;
        leftShoulder.y = -4 + y;
        leftArm.y = -4 + y;
        core1.xRot = ageInTicks % 360 / 6f;
        core2.yRot = (ageInTicks + 120) % 360 / 6f;
        core3.zRot = (ageInTicks + 240) % 360 / 6f;
        float swing = Mth.cos(ageInTicks * 0.1f) * 0.05f + 0.05f;
        rightShoulder.zRot = swing;
        rightArm.zRot = swing;
        leftShoulder.zRot = -swing;
        leftArm.zRot = -swing;
    }
}
