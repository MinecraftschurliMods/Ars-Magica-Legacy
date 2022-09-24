package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class AMEntityModel<T extends Entity> extends EntityModel<T> {
    private static final float[] DEG_TO_RAD = Util.make(new float[360], array -> {
        for (int i = -180; i < 180; i++) {
            array[i + 180] = (float) Math.toRadians(i);
        }
    });
    private final Set<ModelPart> PARTS = new HashSet<>();

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
        for (ModelPart mp : PARTS) {
            mp.render(stack, consumer, light, overlay, r, g, b, a);
        }
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    }

    /**
     * Adds a model part to a model by getting it from the root part.
     *
     * @param root The root model part.
     * @param name The name of the part.
     * @return The model part added.
     */
    public ModelPart addPart(ModelPart root, String name) {
        ModelPart part = root.getChild(name);
        PARTS.add(part);
        return part;
    }

    /**
     * Adds multiple model parts to a model by getting them from the root part.
     *
     * @param root  The root model part.
     * @param names The names of the parts.
     */
    public Set<ModelPart> addParts(ModelPart root, String... names) {
        Set<ModelPart> set = new HashSet<>();
        for (String s : names) {
            set.add(addPart(root, s));
        }
        return set;
    }

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
        pd.addOrReplaceChild(name, CubeListBuilder.create().texOffs(texU, texV).addBox(originX, originY, originZ, sizeX, sizeY, sizeZ, CubeDeformation.NONE), PartPose.offsetAndRotation(offsetX, offsetY, offsetZ, rotationX == 0 ? 0 : degToRad(rotationX), rotationY == 0 ? 0 : degToRad(rotationY), rotationZ == 0 ? 0 : degToRad(rotationZ)));
    }

    /**
     * Converts a double degrees value to a float radians value. Uses an internal cache for integer values.
     *
     * @param deg The degrees value to convert.
     * @return The float radians value.
     */
    public static float degToRad(double deg) {
        deg = Mth.wrapDegrees(deg);
        return deg % 1 == 0 ? DEG_TO_RAD[(int) deg + 180] : (float) Math.toRadians(deg);
    }
}
