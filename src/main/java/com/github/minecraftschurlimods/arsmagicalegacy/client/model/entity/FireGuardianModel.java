package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class FireGuardianModel extends AMBossEntityModel<FireGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire_guardian"), "main");

    public FireGuardianModel(ModelPart root) {
        addParts(root, "neck1", "neck2", "neck3", "neck4", "neck5", "neck6", "body1", "body2", "body3", "body4", "body5", "body6", "body7", "body8", "body9");
        addHeadParts(root, "nose_front", "nose_back", "head", "right_ear_front", "right_ear_back", "left_ear_front", "left_ear_back");
        addPositiveSwingingParts(root, "right_arm", "right_hand");
        addNegativeSwingingParts(root, "left_arm", "left_hand");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "nose_front", 48, 20, -2.5f, -3.5f, -14, 5, 3, 2, 0, -18, 0);
        addCube(pd, "nose_back", 0, 46, -3.5f, -4, -12, 7, 4, 3, 0, -18, 0);
        addCube(pd, "head", 20, 34, -4, -5, -9, 8, 5, 8, 0, -18, 0);
        addCube(pd, "right_ear_front", 48, 25, -6, -3.5f, -6, 2, 3, 6, 0, -18, 0);
        addCube(pd, "right_ear_back", 52, 34, -5.5f, -3, 0, 1, 2, 5, 0, -18, 0);
        addCube(pd, "right_arm", 56, 2, -10, -1, 1, 2, 16, 2, 0, -13, 0);
        addCube(pd, "right_hand", 0, 33, -12, 15, 0, 6, 16, 4, 0, -13, 0);
        addCube(pd, "left_ear_front", 48, 25, 4, -3.5f, -6, 2, 3, 6, 0, -18, 0);
        addCube(pd, "left_ear_back", 52, 34, 4.5f, -3, 0, 1, 2, 5, 0, -18, 0);
        addCube(pd, "left_arm", 56, 2, 8, -1, 1, 2, 16, 2, 0, -13, 0);
        addCube(pd, "left_hand", 0, 33, 6, 15, 0, 6, 16, 4, 0, -13, 0);
        addCube(pd, "neck1", 20, 30, -1, -33, -15, 2, 1, 2, 0, 0, 30, 37.5f, 0, 0);
        addCube(pd, "neck2", 16, 28, -2, -32.25f, -16.25f, 4, 1, 4, 0, 0, 30, 37.5f, 0, 0);
        addCube(pd, "neck3", 12, 26, -3, -31.5f, -17, 6, 1, 6, 0, 0, 30, 37.5f, 0, 0);
        addCube(pd, "neck4", 8, 24, -4, -30.75f, -18.5f, 8, 1, 8, 0, 0, 30, 36.5f, 0, 0);
        addCube(pd, "neck5", 4, 22, -5, -29.5f, -21, 10, 1, 10, 0, 0, 30, 34, 0, 0);
        addCube(pd, "neck6", 0, 20, -6, -27.75f, -24, 12, 1, 12, 0, 0, 30, 30, 0, 0);
        addCube(pd, "body1", 0, 0, -7, -26.25f, -25.75f, 14, 6, 14, 0, 0, 30, 27.5f, 0, 0);
        addCube(pd, "body2", 3, 3, -6.5f, -18.75f, -27.25f, 13, 5, 12, 0, 0, 30, 17.5f, 0, 0);
        addCube(pd, "body3", 7, 7, -5.5f, -14, -26.75f, 11, 3, 10, 0, 0, 30, 15, 0, 0);
        addCube(pd, "body4", 11, 9, -4.5f, -11.25f, -26.5f, 9, 3, 8, 0, 0, 30, 12.5f, 0, 0);
        addCube(pd, "body5", 15, 11, -3.5f, -7.5f, -26, 7, 3, 6, 0, 0, 30, 7.5f, 0, 0);
        addCube(pd, "body6", 19, 12, -2.5f, -2.5f, -25, 5, 4, 4, 0, 0, 30);
        addCube(pd, "body7", 21, 12, -2, 3.25f, -24, 4, 5, 3, 0, 0, 30, -5, 0, 0);
        addCube(pd, "body8", 24, 11, -1, 9.25f, -23, 2, 7, 2, 0, 0, 30, -7.5f, 0, 0);
        addCube(pd, "body9", 26, 12, -0.5f, 15.25f, -22.75f, 1, 7, 1, 0, 0, 30, -7.5f, 0, 0);
        return LayerDefinition.create(md, 64, 64);
    }
}
