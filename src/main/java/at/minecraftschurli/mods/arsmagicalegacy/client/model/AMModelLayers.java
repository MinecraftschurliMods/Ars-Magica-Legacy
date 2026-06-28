package at.minecraftschurli.mods.arsmagicalegacy.client.model;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

public interface AMModelLayers {
    ModelLayerLocation WITCHWOOD_BOAT = modelLayerLocation("boat/witchwood");
    ModelLayerLocation WITCHWOOD_CHEST_BOAT = modelLayerLocation("chest_boat/witchwood");
    ModelLayerLocation DRYAD = modelLayerLocation("dryad");
    ModelLayerLocation WINTERS_GRASP = modelLayerLocation("winters_grasp");
    ModelLayerLocation NATURE_SCYTHE = modelLayerLocation("nature_scythe");
    ModelLayerLocation THROWN_ROCK = modelLayerLocation("thrown_rock");
    Identifier WINTERS_GRASP_TEXTURE = ArsMagicaApi.id("textures/entity/ice_guardian.png");
    Identifier NATURE_SCYTHE_TEXTURE = ArsMagicaApi.id("textures/entity/nature_guardian.png");
    Identifier THROWN_ROCK_TEXTURE = ArsMagicaApi.id("textures/entity/earth_guardian.png");

    private static ModelLayerLocation modelLayerLocation(String path) {
        return new ModelLayerLocation(ArsMagicaApi.id(path), "main");
    }

    static LayerDefinition createDryadLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 32);
    }

    static LayerDefinition createWintersGraspLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        AMClientUtil.addCube(pd, "arm", 94, 0, -3, -15, -3, 6, 26, 6, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "hand", 82, 44, -3, 12, -2, 6, 1, 5, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "outer_fingers", 104, 44, 2, 11, -2, 1, 1, 5, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "inner_fingers", 104, 44, -3, 11, -2, 1, 1, 5, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "thumb", 82, 50, 0, 11, -3, 3, 2, 1, 0, 0, 0, 90, 0, 0);
        return LayerDefinition.create(md, 128, 64);
    }

    static LayerDefinition createNatureScytheLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        AMClientUtil.addCube(pd, "rod", 0, 73, -1, -15.5f, -10, 2, 2, 36, 0, 0, 0);
        AMClientUtil.addCube(pd, "head", 84, 73, -1.5f, -20.5f, -13, 3, 12, 3, 0, 0, 0);
        AMClientUtil.addCube(pd, "blade", 76, 73, -0.5f, -22.5f, -12.5f, 1, 30, 3, 0, 0, 0);
        AMClientUtil.addCube(pd, "blade_curve", 96, 73, -0.501f, 0, -14, 1, 12, 2, 0, 0, 0, 30, 0, 0);
        AMClientUtil.addCube(pd, "blade_tip", 102, 73, -0.5f, 2.5f, -17.5f, 1, 8, 1, 0, 0, 0, 60, 0, 0);
        return LayerDefinition.create(md, 128, 128);
    }

    static LayerDefinition createThrownRockLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        AMClientUtil.addCube(pd, "rock1", 1, 24, -7, -4, -2.999f, 8, 6, 7, 0, 4, 0);
        AMClientUtil.addCube(pd, "rock2", 1, 23, 1, -5, -4, 7, 7, 8, 0, 4, 0);
        AMClientUtil.addCube(pd, "rock3", 1, 24, -3, -2, -5, 8, 6, 7, 0, 4, 0);
        return LayerDefinition.create(md, 64, 64);
    }
}
