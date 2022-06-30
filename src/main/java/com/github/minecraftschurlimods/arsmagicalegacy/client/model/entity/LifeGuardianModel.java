package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class LifeGuardianModel extends AMBossEntityModel<LifeGuardian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "life_guardian"), "main");
    private final ModelPart circleNorth;
    private final ModelPart circleEast;
    private final ModelPart circleSouth;
    private final ModelPart circleWest;

    public LifeGuardianModel(ModelPart root) {
        circleNorth = addPart(root, "circle_north");
        circleEast = addPart(root, "circle_east");
        circleSouth = addPart(root, "circle_south");
        circleWest = addPart(root, "circle_west");
        addParts(root, "core1", "core2", "core3", "core4", "core5", "core6", "core7", "core8", "x_rod", "z_rod", "middle_rod_north", "middle_rod_east", "middle_rod_south", "middle_rod_west", "top_rod_north", "top_rod_east", "top_rod_south", "top_rod_west");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        addCube(pd, "core1", 0, 0, -1.5f, -0.5f, -1.5f, 3, 1, 3, 0, 2, 0);
        addCube(pd, "core2", 0, 4, -3, -0.5f, -3, 6, 1, 6, 0, 3, 0);
        addCube(pd, "core3", 0, 11, -4, -1, -4, 8, 2, 8, 0, 4.5f, 0);
        addCube(pd, "core4", 0, 21, -5, -1.5f, -5, 10, 3, 10, 0, 7, 0);
        addCube(pd, "core5", 40, 0, -6, -3, -6, 12, 6, 12, 0, 11.5f, 0);
        addCube(pd, "core6", 0, 34, -5, -0.5f, -5, 10, 1, 10, 0, 15, 0);
        addCube(pd, "core7", 0, 45, -4, -0.5f, -4, 8, 1, 8, 0, 16, 0);
        addCube(pd, "core8", 0, 54, -3, -0.5f, -3, 6, 1, 6, 0, 17, 0);
        addCube(pd, "x_rod", 40, 32, -8, -1, -1, 16, 2, 2, 0, 19, 0, -45, 0, 0);
        addCube(pd, "z_rod", 40, 32, -8, -1, -1, 16, 2, 2, 0, 19, 0, -45, -90, 0);
        addCube(pd, "middle_rod_north", 40, 36, -1, -5.5f, -7.999f, 2, 11, 2, 0, 13, 0);
        addCube(pd, "middle_rod_east", 40, 36, 5.999f, -5.5f, -1, 2, 11, 2, 0, 13, 0);
        addCube(pd, "middle_rod_south", 40, 36, -1, -5.5f, 5.999f, 2, 11, 2, 0, 13, 0);
        addCube(pd, "middle_rod_west", 40, 36, -7.999f, -5.5f, -1, 2, 11, 2, 0, 13, 0);
        addCube(pd, "top_rod_north", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -22.5f, 0, 0);
        addCube(pd, "top_rod_east", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -22.5f, 90, 0);
        addCube(pd, "top_rod_south", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -22.5f, 180, 0);
        addCube(pd, "top_rod_west", 48, 36, -0.5f, -17, -12.5f, 1, 7, 1, 0, 22, 0, -22.5f, -90, 0);
        addCube(pd, "circle_north", 40, 18, -7, -7, 0, 14, 14, 0, 0, 12, -10);
        addCube(pd, "circle_east", 40, 18, -7, -7, 0, 14, 14, 0, -10, 12, 0, 90, 0, 90);
        addCube(pd, "circle_south", 40, 18, -7, -7, 0, 14, 14, 0, 0, 12, 10, 180, 0, 180);
        addCube(pd, "circle_west", 40, 18, -7, -7, 0, 14, 14, 0, 10, 12, 0, -90, 0, 90);
        return LayerDefinition.create(md, 128, 64);
    }

    @Override
    public void setupAnim(LifeGuardian pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        float rot = degToRad(pAgeInTicks);
        circleNorth.zRot = rot;
        circleEast.yRot = -rot;
        circleSouth.zRot = -rot;
        circleWest.yRot = rot;
    }
}
