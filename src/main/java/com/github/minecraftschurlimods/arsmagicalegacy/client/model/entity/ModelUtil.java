package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class ModelUtil {
    /**
     * Adds a cube to a part definition.
     *
     * @param pd      The part definition to add the cube to.
     * @param name    The name of the cube to add.
     * @param texU    The texture U value to use.
     * @param texV    The texture V value to use.
     * @param originX The origin x coordinate.
     * @param originY The origin y coordinate.
     * @param originZ The origin z coordinate.
     * @param sizeX   The x size.
     * @param sizeY   The y size.
     * @param sizeZ   The z size.
     * @param offsetX The x offset.
     * @param offsetY The y offset.
     * @param offsetZ The z offset.
     */
    public static void addCube(PartDefinition pd, String name, int texU, int texV, float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float offsetX, float offsetY, float offsetZ) {
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE), PartPose.offset(offsetX, offsetY, offsetZ));
    }

    /**
     * Adds a cube to a part definition.
     *
     * @param pd        The part definition to add the cube to.
     * @param name      The name of the cube to add.
     * @param texU      The texture U value to use.
     * @param texV      The texture V value to use.
     * @param originX   The origin x coordinate.
     * @param originY   The origin y coordinate.
     * @param originZ   The origin z coordinate.
     * @param sizeX     The x size.
     * @param sizeY     The y size.
     * @param sizeZ     The z size.
     * @param offsetX   The x offset.
     * @param offsetY   The y offset.
     * @param offsetZ   The z offset.
     * @param rotationX The x rotation.
     * @param rotationY The y rotation.
     * @param rotationZ The z rotation.
     */
    public static void addCube(PartDefinition pd, String name, int texU, int texV, float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float offsetX, float offsetY, float offsetZ, float rotationX, float rotationY, float rotationZ) {
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE), PartPose.offsetAndRotation(offsetX, offsetY, offsetZ, rotationX, rotationY, rotationZ));
    }

    /**
     * Adds a mirrored cube to a part definition.
     *
     * @param pd      The part definition to add the cube to.
     * @param name    The name of the cube to add.
     * @param texU    The texture U value to use.
     * @param texV    The texture V value to use.
     * @param originX The origin x coordinate.
     * @param originY The origin y coordinate.
     * @param originZ The origin z coordinate.
     * @param sizeX   The x size.
     * @param sizeY   The y size.
     * @param sizeZ   The z size.
     * @param offsetX The x offset.
     * @param offsetY The y offset.
     * @param offsetZ The z offset.
     */
    public static void addMirroredCube(PartDefinition pd, String name, int texU, int texV, float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float offsetX, float offsetY, float offsetZ) {
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).mirror().addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE).mirror(false), PartPose.offset(offsetX, offsetY, offsetZ));
    }

    /**
     * Adds a mirrored cube to a part definition.
     *
     * @param pd        The part definition to add the cube to.
     * @param name      The name of the cube to add.
     * @param texU      The texture U value to use.
     * @param texV      The texture V value to use.
     * @param originX   The origin x coordinate.
     * @param originY   The origin y coordinate.
     * @param originZ   The origin z coordinate.
     * @param sizeX     The x size.
     * @param sizeY     The y size.
     * @param sizeZ     The z size.
     * @param offsetX   The x offset.
     * @param offsetY   The y offset.
     * @param offsetZ   The z offset.
     * @param rotationX The x rotation.
     * @param rotationY The y rotation.
     * @param rotationZ The z rotation.
     */
    public static void addMirroredCube(PartDefinition pd, String name, int texU, int texV, float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ, float offsetX, float offsetY, float offsetZ, float rotationX, float rotationY, float rotationZ) {
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).mirror().addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE).mirror(false), PartPose.offsetAndRotation(offsetX, offsetY, offsetZ, rotationX, rotationY, rotationZ));
    }
}
