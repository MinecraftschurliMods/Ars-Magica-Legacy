package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class IceGuardianModel extends AMBossEntityModel<IceGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ice_guardian"), "main");
    private final Set<ModelPart> rightArm;
    private final Set<ModelPart> leftArm;

    public IceGuardianModel(ModelPart root) {
        addParts(root, "top_body", "middle_body", "bottom_body", "right_shoulder", "left_shoulder");
        addHeadParts(root, "right_eye", "left_eye", "top_head", "middle_head", "bottom_head");
        addRotatingParts(root, "rod1", "rod2", "rod3", "rod4", "cube1", "cube2", "cube3", "cube4");
        addRotatingCube(root, 6, "core");
        addRotatingCubes(root, 4, "right_core", "left_core");
        rightArm = addPositiveSwingingParts(root, "right_arm", "right_hand", "right_outer_fingers", "right_inner_fingers", "right_thumb");
        leftArm = addPositiveSwingingParts(root, "left_arm", "left_hand", "left_outer_fingers", "left_inner_fingers", "left_thumb");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "right_eye", 0, 24, 2, -6, -4.5f, 2, 1, 9, 0, -15, 0);
        addCube(pd, "left_eye", 0, 24, -4, -6, -4.5f, 2, 1, 9, 0, -15, 0);
        addCube(pd, "top_head", 0, 0, -5, -10, -5, 10, 4, 10, 0, -15, 0);
        addCube(pd, "middle_head", 0, 14, -5, -6, -3, 10, 2, 8, 0, -15, 0);
        addCube(pd, "bottom_head", 0, 0, -5, -4, -5, 10, 4, 10, 0, -15, 0);
        addCube(pd, "top_body", 0, 34, -7, 2.5f, -5.5f, 14, 7, 11, 0, -9.5f, -6, 90, 0, 0);
        addCube(pd, "middle_body", 50, 0, -3, -3, -5, 8, 8, 10, 0, -4, 0, 0, 0, 45);
        addCube(pd, "bottom_body", 50, 40, -2, -6, -4, 8, 8, 8, 0, 4, 0, 0, 0, -45);
        addCube(pd, "core1", 94, 32, -3, -3, -3, 6, 6, 6, 0, 15, 0);
        addCube(pd, "core2", 94, 32, -3, -3, -3, 6, 6, 6, 0, 15, 0);
        addCube(pd, "core3", 94, 32, -3, -3, -3, 6, 6, 6, 0, 15, 0);
        addCube(pd, "cube1", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0);
        addCube(pd, "cube2", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0, 0, 90, 0);
        addCube(pd, "cube3", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0, 0, 180, 0);
        addCube(pd, "cube4", 118, 14, -1.5f, -1.5f, -9, 3, 3, 1, 0, 15, 0, 0, -90, 0);
        addCube(pd, "rod1", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, -45, 0);
        addCube(pd, "rod2", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, 45, 0);
        addCube(pd, "rod3", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, 135, 0);
        addCube(pd, "rod4", 118, 0, -10, -6, -1, 2, 12, 2, 0, 15, 0, 0, -135, 0);
        addCube(pd, "right_shoulder", 50, 18, -5, -5, -6, 10, 10, 12, -7, -8, 0, 0, 0, 45);
        addMirroredCube(pd, "right_core1", 22, 24, -2, -2, -2, 4, 4, 4, 13, -14.5f, 0);
        addMirroredCube(pd, "right_core2", 22, 24, -2, -2, -2, 4, 4, 4, 13, -14.5f, 0);
        addMirroredCube(pd, "right_core3", 22, 24, -2, -2, -2, 4, 4, 4, 13, -14.5f, 0);
        addCube(pd, "right_arm", 94, 0, 17, 0, -3, 6, 26, 6, 0, -10, 0);
        addMirroredCube(pd, "right_hand", 82, 44, 17, 27, -2, 6, 1, 5, 0, -10, 0);
        addMirroredCube(pd, "right_outer_fingers", 104, 44, 22, 26, -2, 1, 1, 5, 0, -10, 0);
        addMirroredCube(pd, "right_inner_fingers", 104, 44, 17, 26, -2, 1, 1, 5, 0, -10, 0);
        addMirroredCube(pd, "right_thumb", 82, 50, 17, 26, -3, 3, 2, 1, 0, -10, 0);
        addCube(pd, "left_shoulder", 50, 18, -5, -5, -6, 10, 10, 12, 7, -8, 0, 0, 0, -45);
        addCube(pd, "left_core1", 22, 24, -2, -2, -2, 4, 4, 4, -13, -14.5f, 0);
        addCube(pd, "left_core2", 22, 24, -2, -2, -2, 4, 4, 4, -13, -14.5f, 0);
        addCube(pd, "left_core3", 22, 24, -2, -2, -2, 4, 4, 4, -13, -14.5f, 0);
        addCube(pd, "left_arm", 94, 0, -23, 0, -3, 6, 26, 6, 0, -10, 0);
        addCube(pd, "left_hand", 82, 44, -23, 27, -2, 6, 1, 5, 0, -10, 0);
        addCube(pd, "left_outer_fingers", 104, 44, -23, 26, -2, 1, 1, 5, 0, -10, 0);
        addCube(pd, "left_inner_fingers", 104, 44, -18, 26, -2, 1, 1, 5, 0, -10, 0);
        addCube(pd, "left_thumb", 82, 50, -20, 26, -3, 3, 2, 1, 0, -10, 0);
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(IceGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        boolean r = pEntity.hasRightArm();
        for (ModelPart mp : rightArm) {
            mp.visible = r;
        }
        boolean l = pEntity.hasLeftArm();
        for (ModelPart mp : leftArm) {
            mp.visible = l;
        }
    }
}
