package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class AirGuardianModel extends AMBossEntityModel<AirGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "air_guardian"), "main");

    public AirGuardianModel(ModelPart mp) {
        addParts(mp, "body", "tail");
        addRotatingCube(mp, 6, "core");
        addHeadPart(mp, "head");
        addPositiveSwingingParts(mp, "right_shoulder", "right_arm");
        addNegativeSwingingParts(mp, "left_shoulder", "left_arm");
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
}
