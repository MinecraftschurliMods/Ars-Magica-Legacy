package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class EarthGuardianModel extends AMBossEntityModel<EarthGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth_guardian"), "main");
    private final Set<ModelPart> rightArm;
    private final Set<ModelPart> leftArm;
    private final Set<ModelPart> rock;

    public EarthGuardianModel(ModelPart root) {
        rightArm = addParts(root, "right_shoulder_pad", "right_shoulder");
        rightArm.addAll(addPositiveSwingingParts(root, "right_arm", "right_hand"));
        leftArm = addParts(root, "left_shoulder_pad", "left_shoulder");
        leftArm.addAll(addNegativeSwingingParts(root, "left_arm", "left_hand"));
        rock = addParts(root, "rock1", "rock2", "rock3");
        addParts(root, "neck", "body", "rod");
        addHeadPart(root, "head");
        addRotatingCube(root, 6, "core");
        addRotatingParts(root, "rod1", "rod2", "rod3", "rod4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "head", 0, 0, -4, -6, -4, 8, 6, 8, 0, -11, 0);
        addCube(pd, "neck", 44, 25, -2, -1, -2, 4, 1, 4, 0, -10, 0);
        addCube(pd, "body", 0, 14, -5, 0, -3, 10, 3, 6, 0, -10, 0);
        addCube(pd, "core1", 32, 0, -3, -3, -3, 6, 6, 6, 0, -2, 0);
        addCube(pd, "core2", 32, 0, -3, -3, -3, 6, 6, 6, 0, -2, 0);
        addCube(pd, "core3", 32, 0, -3, -3, -3, 6, 6, 6, 0, -2, 0);
        addCube(pd, "right_shoulder_pad", 0, 39, -3, -3, -5, 6, 6, 10, -8, -10, 0, 0, 0, 35);
        addCube(pd, "right_shoulder", 0, 23, -4, -4, -4, 8, 8, 8, -8, -10, 0, 0, 0, 35);
        addCube(pd, "right_arm", 32, 12, -2, 3.5f, -2, 4, 8, 4, -8, -10, 0);
        addCube(pd, "right_hand", 48, 12, -1.5f, 11.5f, -1.5f, 3, 10, 3, -8, -10, 0);
        addCube(pd, "left_shoulder_pad", 0, 39, -3, -3, -5, 6, 6, 10, 8, -10, 0, 0, 0, -35);
        addCube(pd, "left_shoulder", 0, 23, -4, -4, -4, 8, 8, 8, 8, -10, 0, 0, 0, -35);
        addCube(pd, "left_arm", 32, 12, -2, 3.5f, -2, 4, 8, 4, 8, -10, 0);
        addCube(pd, "left_hand", 48, 12, -1.5f, 11.5f, -1.5f, 3, 10, 3, 8, -10, 0);
        addCube(pd, "rod", 32, 24, -1, -8, -1, 2, 16, 2, 0, 12, 0);
        addCube(pd, "rod1", 40, 24, 2, -5, -3, 1, 10, 1, 0, 12, 0);
        addCube(pd, "rod2", 40, 24, 2, -5, 2, 1, 10, 1, 0, 12, 0);
        addCube(pd, "rod3", 40, 24, -3, -5, 2, 1, 10, 1, 0, 12, 0);
        addCube(pd, "rod4", 40, 24, -3, -5, -3, 1, 10, 1, 0, 12, 0);
        addCube(pd, "rock1", 1, 24, -7, -23, -2.999f, 8, 6, 7, 0, -10, 0);
        addCube(pd, "rock2", 1, 23, 1, -24, -4, 7, 7, 8, 0, -10, 0);
        addCube(pd, "rock3", 1, 24, -3, -21, -5, 8, 6, 7, 0, -10, 0);
        return LayerDefinition.create(md, 64, 64);
    }

    @Override
    public void setupAnim(EarthGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        boolean visible = pEntity.getAction() == EarthGuardian.EarthGuardianAction.THROWING;
        for (ModelPart mp : rock) {
            mp.visible = visible;
        }
        if (visible) {
            float ticks = (pEntity.getTicksInAction() + pLimbSwing) % 15;
            if (ticks < 13) {
                float rot = degToRad(30 * Math.min(ticks, 6));
                for (ModelPart mp : rightArm) {
                    mp.xRot = -rot;
                }
                for (ModelPart mp : leftArm) {
                    mp.xRot = -rot;
                }
                for (ModelPart mp : rock) {
                    mp.xRot = degToRad(180) - rot;
                }
            } else if (ticks > 13) {
                float rot = 180 - 18 * (15 - ticks + pAgeInTicks - pEntity.tickCount);
                for (ModelPart mp : rightArm) {
                    mp.xRot = rot;
                }
                for (ModelPart mp : leftArm) {
                    mp.xRot = rot;
                }
                for (ModelPart mp : rock) {
                    mp.xRot = 180 - rot;
                }
            }
        } else {
            for (ModelPart mp : rightArm) {
                mp.xRot = 0;
            }
            for (ModelPart mp : leftArm) {
                mp.xRot = 0;
            }
        }
    }
}
