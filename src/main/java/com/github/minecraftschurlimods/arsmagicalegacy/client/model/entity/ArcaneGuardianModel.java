package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ArcaneGuardianModel extends AMBossEntityModel<ArcaneGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_guardian"), "main");
    private final ModelPart rune;

    public ArcaneGuardianModel(ModelPart root) {
        rune = addPart(root, "rune", false);
        addParts(root, "neck", "body", "rod", "rod1", "rod2", "rod3", "rod4", "upper_left_arm", "lower_left_arm", "left_hand", "book", "robe_top", "robe_front", "right_leg", "robe_back_right", "robe_right", "left_leg", "robe_back_left", "robe_left");
        addHeadParts(root, "head", "hood_top", "hood_right", "hood_left", "hood_back");
        addPositiveSwingingParts(root, "upper_right_arm", "lower_right_arm", "right_hand", "wand");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 88, 0, -3, -7, -3, 7, 7, 7, 0, -4.5f, 0);
        addCube(pd, "neck", 116, 8, -0.5f, -0.5f, -0.5f, 2, 1, 2, 0, -4, 0);
        addCube(pd, "hood_top", 88, 14, -4, -8, -3, 9, 1, 8, 0, -4.5f, 0);
        addCube(pd, "hood_right", 106, 23, -4, -7, -3, 1, 8, 8, 0, -4.5f, 0);
        addCube(pd, "hood_left", 88, 23, 4, -7, -3, 1, 8, 8, 0, -4.5f, 0);
        addCube(pd, "hood_back", 88, 39, -3, -7, 4, 7, 8, 1, 0, -4.5f, 0);
        addCube(pd, "body", 104, 39, -3.5f, -2, -1.5f, 8, 4, 4, 0, -1.5f, 0);
        addCube(pd, "rune", 0, 0, -21.5f, -22, 10.5f, 44, 44, 0, 0, 2, 0);
        addCube(pd, "rod", 61, 56, -0.5f, -3, -0.5f, 2, 6, 2, 0, 2.5f, 0);
        addCube(pd, "rod1", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, 0, 0, -45);
        addCube(pd, "rod2", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, 35, 30, 55);
        addCube(pd, "rod3", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, 90, -45, -90);
        addCube(pd, "rod4", 51, 59, -2, -1.5f, -0.5f, 4, 4, 1, 0.5f, 2.5f, 0.5f, 35, -30, -55);
        addCube(pd, "upper_right_arm", 112, 55, -7.5f, -2.5f, -1.5f, 4, 5, 4, 0, -1, 0);
        addCube(pd, "lower_right_arm", 80, 55, -7.5f, 2.5f, -1.5f, 4, 5, 4, 0, -1, 0);
        addCube(pd, "right_hand", 116, 11, -3, -1, -8.5f, 2, 2, 1, -3.5f, -1, 0.5f, 90, 0, 0);
        addCube(pd, "wand", 90, 48, -2.5f, -0.5f, -14.5f, 1, 1, 6, -3.5f, -1.5f, 0.5f, 90, 0, 0);
        addCube(pd, "upper_left_arm", 112, 55, 0, -2.5f, -2, 4, 5, 4, 4.5f, -1, 0.5f, -45, 0, 0);
        addCube(pd, "lower_left_arm", 96, 55, -2.5f, -2.0f, -2.0f, 4, 5, 4, 6.5f, 2.5355f, -3.0355f, 0, 45, 90);
        addCube(pd, "left_hand", 122, 11, -2, 3, -2.5f, 1, 2, 2, 4.5f, 0.5f, 2, -45, 0, 0);
        addCube(pd, "book", 116, 0, -1.5f, -3, -3.5f, 4, 6, 2, 0, 1.5f, 0);
        addCube(pd, "robe_top", 104, 47, -3.5f, -2, -1.5f, 8, 4, 4, 0, 6.5f, 0);
        addCube(pd, "robe_front", 22, 44, -3.5f, -4.5f, -1.5f, 8, 9, 1, 0, 13, 0);
        addCube(pd, "right_leg", 0, 44, -1, 0, -1.1f, 2, 12, 2, -1.5f, 8.5f, 1.5f, 10, 0, 0);
        addCube(pd, "robe_back_right", 40, 44, -2, 0.4f, -0.6f, 4, 9, 1, -1.5f, 8, 2, 10, 0, 0);
        addCube(pd, "robe_right", 8, 44, -7.501f, -0.6f, -1.35f, 1, 9, 3, 4, 9, 0.5f, 5, 0, 0);
        addCube(pd, "left_leg", 0, 44, -1, 0, -1.1f, 2, 12, 2, 2.5f, 8.5f, 0.5f, 10, 0, 0);
        addCube(pd, "robe_back_left", 50, 44, 2, 0.4f, -0.6f, 4, 9, 1, -1.5f, 8, 2, 10, 0, 0);
        addCube(pd, "robe_left", 16, 44, -0.499f, -0.6f, -0.85f, 1, 9, 2, 4, 9, 0.5f, 5, 0, 0);
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(ArcaneGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        rune.zRot = degToRad(pAgeInTicks);
    }
}
