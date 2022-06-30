package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class LightningGuardianModel extends AMBossEntityModel<LightningGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_guardian"), "main");

    public LightningGuardianModel(ModelPart root) {
        addParts(root, "chest", "body", "tail", "shoulder", "chest_front", "chest_back", "right_chest", "left_chest");
        addHeadPart(root, "head");
        addPositiveSwingingParts(root, "right_arm", "right_hand", "top_right_hand_front", "top_right_hand_inside", "top_right_hand_back", "bottom_right_hand_front", "bottom_right_hand_inside", "bottom_right_hand_back");
        addNegativeSwingingParts(root, "left_arm", "left_hand", "top_left_hand_front", "top_left_hand_inside", "top_left_hand_back", "bottom_left_hand_front", "bottom_left_hand_inside", "bottom_left_hand_back");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 32, 0, -1.5f, -3, -1.5f, 3, 3, 3, 0, 7, 0);
        addCube(pd, "chest", 32, 6, -3.5f, -2, -2.5f, 7, 4, 5, 0, 9, 0);
        addCube(pd, "body", 44, 0, -2, -2, -1, 4, 4, 2, 0, 13, 0);
        addCube(pd, "tail", 56, 0, -1, -3, -0.5f, 2, 6, 1, 0, 18, 0);
        addCube(pd, "shoulder", 32, 17, -4, -1, -1, 8, 2, 2, 0, 7.999f, 0);
        addCube(pd, "chest_front", 0, 0, -3.5f, -1, -2.501f, 7, 5, 0.001f, 0, 8, 0);
        addCube(pd, "chest_back", 0, 5, -3.5f, -2.5f, 2.501f, 7, 7, 0.001f, 0, 6.5f, 0);
        addCube(pd, "left_chest", 0, 14, -2.5f, -3.5f, 0, 5, 7, 0.001f, 3.501f, 8.5f, 0, 0, -90, 0);
        addCube(pd, "left_arm", 32, 17, 0, -1, -1, 2, 13, 2, 4, 8, 0);
        addCube(pd, "left_hand", 14, 0, 2, 7, -1, 1, 4, 2, 4, 8, 0);
        addCube(pd, "top_left_hand_front", 14, 6, -1, 7, 1, 4, 1, 1, 4, 8, 0);
        addCube(pd, "top_left_hand_inside", 24, 6, -1, 7, -1, 1, 1, 2, 4, 8, 0);
        addCube(pd, "top_left_hand_back", 14, 8, -1, 7, -2, 4, 1, 1, 4, 8, 0);
        addCube(pd, "bottom_left_hand_front", 14, 10, -1, 10, 1, 4, 1, 1, 4, 8, 0);
        addCube(pd, "bottom_left_hand_inside", 24, 10, -1, 10, -1, 1, 1, 2, 4, 8, 0);
        addCube(pd, "bottom_left_hand_back", 14, 12, -1, 10, -2, 4, 1, 1, 4, 8, 0);
        addCube(pd, "right_chest", 0, 21, -2.5f, -3.5f, 0, 5, 7, 0.001f, -3.501f, 8.5f, 0, 0, -90, 0);
        addCube(pd, "right_arm", 40, 17, -2, -1, -1, 2, 13, 2, -4, 8, 0);
        addCube(pd, "right_hand", 20, 0, -3, 7, -1, 1, 4, 2, -4, 8, 0);
        addCube(pd, "top_right_hand_front", 14, 14, -3, 7, 1, 4, 1, 1, -4, 8, 0);
        addCube(pd, "top_right_hand_inside", 24, 14, 0, 7, -1, 1, 1, 2, -4, 8, 0);
        addCube(pd, "top_right_hand_back", 14, 16, -3, 7, -2, 4, 1, 1, -4, 8, 0);
        addCube(pd, "bottom_right_hand_front", 14, 18, -3, 10, 1, 4, 1, 1, -4, 8, 0);
        addCube(pd, "bottom_right_hand_inside", 24, 18, 0, 10, -1, 1, 1, 2, -4, 8, 0);
        addCube(pd, "bottom_right_hand_back", 14, 20, -3, 10, -2, 4, 1, 1, -4, 8, 0);
        return LayerDefinition.create(md, 64, 32);
    }
}
