package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class NatureGuardianModel extends AMBossEntityModel<NatureGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature_guardian"), "main");
    private final Set<ModelPart> rightArm;
    private final Set<ModelPart> scythe;

    public NatureGuardianModel(ModelPart root) {
        rightArm = addParts(root, "right_arm", "right_hand");
        scythe = addParts(root, "scythe_rod", "scythe_head", "scythe_blade1", "scythe_blade2", "scythe_blade3");
        addParts(root, "neck", "body1", "body2", "body3", "body4", "body5", "shoulder", "left_arm", "left_hand", "shield");
        addRotatingParts(root, "rod1", "rod2", "rod3", "rod4", "rod5", "rod6", "rod7", "rod8");
        addRotatingCubes(root, 6, "core");
        addHeadParts(root, "hair1", "hair2", "hair3", "hair4", "hair5", "hair6", "hair7", "hair8", "head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "hair1", 76, 0, -2, -1, -2, 4, 2, 20, 0, -44, 0);
        addCube(pd, "hair2", 76, 22, 3, -1, 0, 2, 2, 12, 0, -44, 0);
        addCube(pd, "hair3", 76, 36, 3, -1, 2, 2, 2, 8, 0, -41.5f, 0);
        addCube(pd, "hair4", 76, 46, 3, -1, 3, 2, 2, 5, 0, -38.5f, 0);
        addCube(pd, "hair5", 78, 24, -1, -1, 3, 2, 2, 10, 0, -40, 0);
        addCube(pd, "hair6", 76, 46, -5, -1, 3, 2, 2, 5, 0, -38.5f, 0);
        addCube(pd, "hair7", 76, 36, -5, -1, 0, 2, 2, 8, 0, -41.5f, 0);
        addCube(pd, "hair8", 76, 22, -5, -1, 0, 2, 2, 12, 0, -44, 0);
        addCube(pd, "head", 0, 0, -4, -6, -6, 8, 6, 12, 0, -38, 0);
        addCube(pd, "neck", 44, 45, -2, -4, -2, 4, 4, 4, 0, -34, 0);
        addCube(pd, "body1", 0, 18, -6, -4, -5, 12, 8, 10, 0, -30, 0);
        addCube(pd, "body2", 44, 0, -4, -4, -4, 8, 8, 8, 0, -22, 0);
        addCube(pd, "body3", 44, 31, -3, -4, -3, 6, 8, 6, 0, -14, 0);
        addCube(pd, "body4", 44, 16, -3.5f, -4, -3.5f, 7, 8, 7, 0, -6, 0);
        addCube(pd, "body5", 44, 0, -4, -4, -4, 8, 8, 8, 0, 2, 0);
        addCube(pd, "core1", 60, 45, -2, -2, -2, 4, 4, 4, 0, 10, 0);
        addCube(pd, "core2", 60, 45, -2, -2, -2, 4, 4, 4, 0, 10, 0);
        addCube(pd, "core3", 60, 45, -2, -2, -2, 4, 4, 4, 0, 10, 0);
        addCube(pd, "shoulder", 0, 36, -8, -1, -1, 16, 2, 2, 0, -31.5f, 0);
        addCube(pd, "rod1", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0);
        addCube(pd, "rod2", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, -45, 0);
        addCube(pd, "rod3", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, -90, 0);
        addCube(pd, "rod4", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, -135, 0);
        addCube(pd, "rod5", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, 180, 0);
        addCube(pd, "rod6", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, 135, 0);
        addCube(pd, "rod7", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, 90, 0);
        addCube(pd, "rod8", 78, 53, -0.5f, 0, -4, 1, 14, 1, 0, 6, 0, 0, 45, 0);
        addCube(pd, "left_arm", 44, 53, 18, -5, -3.5f, 4, 14, 6, -10, -28.5f, 0.5f, 15, 0, 0);
        addCube(pd, "left_hand", 64, 53, -1.5f, -1, -2, 3, 12, 4, 10, -20.5f, 2, -15, 0, 0);
        addCube(pd, "shield", 0, 40, -10, -15, -17, 20, 30, 2, 0, -20.5f, 0, 0, 60, 0);
        addCube(pd, "right_arm", 44, 53, -2, -5, -3.5f, 4, 14, 6, -10, -28.5f, 0.5f, 15, 0, 0);
        addCube(pd, "right_hand", 64, 53, -1.5f, -2, -2, 3, 12, 4, -10, -20.5f, 2, -75, 0, 0);
        addCube(pd, "scythe_rod", 0, 73, -1, 10.5f, -32, 2, 2, 36, 10, -20.5f, 2, -15, 0, 0);
        addCube(pd, "scythe_head", 84, 73, -1.5f, 5.5f, -35, 3, 12, 3, 10, -20.5f, 2, -15, 0, 0);
        addCube(pd, "scythe_blade1", 76, 73, -0.5f, 3.5f, -34.5f, 1, 30, 3, 10, -20.5f, 2, -15, 0, 0);
        addCube(pd, "scythe_blade2", 96, 73, -0.499f, 11.5f, -46, 1, 12, 2, 10, -20.5f, 2, 15, 0, 0);
        addCube(pd, "scythe_blade3", 102, 73, -0.5f, -3.5f, -51, 1, 8, 1, 10, -20.5f, 2, 45, 0, 0);
        return LayerDefinition.create(md, 128, 128);
    }

    @Override
    public void setupAnim(NatureGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        for (ModelPart mp : scythe) {
            mp.visible = true;
        }
    }
}
