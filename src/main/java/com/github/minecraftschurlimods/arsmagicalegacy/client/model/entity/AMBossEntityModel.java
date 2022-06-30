package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AMBossEntityModel<T extends Entity> extends AMEntityModel<T> {
    private final Map<ModelPart, Float> PARTS = new HashMap<>();
    private final Map<ModelPart, Float> ROTATING_PARTS = new HashMap<>();
    private final Map<Triple<ModelPart, ModelPart, ModelPart>, Float> ROTATING_CUBES = new HashMap<>();
    private final Set<ModelPart> HEAD_PARTS = new HashSet<>();
    private final Set<ModelPart> POSITIVE_SWINGING_PARTS = new HashSet<>();
    private final Set<ModelPart> NEGATIVE_SWINGING_PARTS = new HashSet<>();

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        float yOffset = 45 * (Mth.cos(4 * degToRad(pAgeInTicks)) - Mth.cos(4 * degToRad(pAgeInTicks - 1)));
        for (Map.Entry<ModelPart, Float> entry : PARTS.entrySet()) {
            if (entry.getValue() == null) continue;
            entry.getKey().y = entry.getValue() + yOffset;
        }
        float rot = degToRad(pAgeInTicks);
        for (Map.Entry<ModelPart, Float> entry : ROTATING_PARTS.entrySet()) {
            entry.getKey().yRot = degToRad(entry.getValue()) + rot;
        }
        for (Map.Entry<Triple<ModelPart, ModelPart, ModelPart>, Float> entry : ROTATING_CUBES.entrySet()) {
            float size = entry.getValue();
            entry.getKey().getLeft().xRot = pAgeInTicks % 360 / size;
            entry.getKey().getMiddle().yRot = (pAgeInTicks + 120) % 360 / size;
            entry.getKey().getRight().zRot = (pAgeInTicks + 240) % 360 / size;
        }
        float pitch = degToRad(pHeadPitch);
        float yaw = degToRad(pNetHeadYaw);
        for (ModelPart mp : HEAD_PARTS) {
            mp.xRot = pitch;
            mp.yRot = yaw;
        }
        float swing = Mth.cos((pAgeInTicks % 360) * 0.1f) * 0.05f + 0.05f;
        for (ModelPart mp : POSITIVE_SWINGING_PARTS) {
            mp.zRot = swing;
        }
        swing = -swing;
        for (ModelPart mp : NEGATIVE_SWINGING_PARTS) {
            mp.zRot = swing;
        }
    }

    @Override
    public ModelPart addPart(ModelPart root, String name) {
        return addPart(root, name, true);
    }

    /**
     * Adds a model part to a model by getting it from the root part.
     *
     * @param root     The root model part.
     * @param name     The name of the part.
     * @param levitate Whether this part should have a levitation animation or not.
     * @return The model part added.
     */
    public ModelPart addPart(ModelPart root, String name, boolean levitate) {
        ModelPart part = super.addPart(root, name);
        PARTS.put(part, levitate ? part.y : null);
        return part;
    }

    /**
     * Adds a y-rotating model part to a model by getting it from the root part.
     *
     * @param root     The root model part.
     * @param name     The name of the part.
     * @param levitate Whether this part should have a levitation animation or not.
     * @return The model part added.
     */
    public ModelPart addRotatingPart(ModelPart root, String name, boolean levitate) {
        ModelPart part = addPart(root, name, levitate);
        ROTATING_PARTS.put(part, (float) Math.toDegrees(part.yRot));
        return part;
    }

    /**
     * Adds a y-rotating model part to a model by getting it from the root part.
     *
     * @param root The root model part.
     * @param name The name of the part.
     * @return The model part added.
     */
    public ModelPart addRotatingPart(ModelPart root, String name) {
        return addRotatingPart(root, name, true);
    }

    /**
     * Adds multiple y-rotating model parts to a model by getting them from the root part.
     *
     * @param root  The root model part.
     * @param names The names of the parts.
     */
    public Set<ModelPart> addRotatingParts(ModelPart root, String... names) {
        Set<ModelPart> set = new HashSet<>();
        for (String s : names) {
            set.add(addRotatingPart(root, s));
        }
        return set;
    }

    /**
     * Adds a triple of rotating cube model parts to a model by getting it from the root part.
     *
     * @param root     The root model part.
     * @param name     The name prefix of the cubes.
     * @param size     The size of the cubes.
     * @param levitate Whether this part should have a levitation animation or not.
     * @return The model parts added.
     */
    public Triple<ModelPart, ModelPart, ModelPart> addRotatingCube(ModelPart root, float size, String name, boolean levitate) {
        Triple<ModelPart, ModelPart, ModelPart> triple = new MutableTriple<>(addPart(root, name + "1", levitate), addPart(root, name + "2", levitate), addPart(root, name + "3", levitate));
        ROTATING_CUBES.put(triple, size);
        return triple;
    }

    /**
     * Adds a triple of rotating cube model parts to a model by getting it from the root part.
     *
     * @param root The root model part.
     * @param name The name prefix of the cubes.
     * @param size The size of the cubes.
     * @return The model part added.
     */
    public Triple<ModelPart, ModelPart, ModelPart> addRotatingCube(ModelPart root, float size, String name) {
        return addRotatingCube(root, size, name, true);
    }

    /**
     * Adds multiple triple of rotating cube model parts to a model by getting them from the root part.
     *
     * @param root  The root model part.
     * @param size  The size of the cubes.
     * @param names The name prefixes of the cubes.
     */
    public Set<Triple<ModelPart, ModelPart, ModelPart>> addRotatingCubes(ModelPart root, float size, String... names) {
        Set<Triple<ModelPart, ModelPart, ModelPart>> set = new HashSet<>();
        for (String s : names) {
            set.add(addRotatingCube(root, size, s));
        }
        return set;
    }

    /**
     * Adds a head model part to a model by getting it from the root part. This part accordingly rotates with the entity's look.
     *
     * @param root     The root model part.
     * @param name     The name of the part.
     * @param levitate Whether this part should have a levitation animation or not.
     * @return The model part added.
     */
    public ModelPart addHeadPart(ModelPart root, String name, boolean levitate) {
        ModelPart part = addPart(root, name, levitate);
        HEAD_PARTS.add(part);
        return part;
    }

    /**
     * Adds a head model part to a model by getting it from the root part. This part accordingly rotates with the entity's look.
     *
     * @param root The root model part.
     * @param name The name of the part.
     * @return The model part added.
     */
    public ModelPart addHeadPart(ModelPart root, String name) {
        return addHeadPart(root, name, true);
    }

    /**
     * Adds multiple head model parts to a model by getting them from the root part. These parts accordingly rotate with the entity's look.
     *
     * @param root  The root model part.
     * @param names The names of the parts.
     */
    public Set<ModelPart> addHeadParts(ModelPart root, String... names) {
        Set<ModelPart> set = new HashSet<>();
        for (String s : names) {
            set.add(addHeadPart(root, s));
        }
        return set;
    }

    /**
     * Adds a positive-swinging (swinging to the right) model part to a model by getting it from the root part.
     *
     * @param root     The root model part.
     * @param name     The name of the part.
     * @param levitate Whether this part should have a levitation animation or not.
     * @return The model part added.
     */
    public ModelPart addPositiveSwingingPart(ModelPart root, String name, boolean levitate) {
        ModelPart part = addPart(root, name, levitate);
        POSITIVE_SWINGING_PARTS.add(part);
        return part;
    }

    /**
     * Adds a positive-swinging (swinging to the right) model part to a model by getting it from the root part.
     *
     * @param root The root model part.
     * @param name The name of the part.
     * @return The model part added.
     */
    public ModelPart addPositiveSwingingPart(ModelPart root, String name) {
        return addPositiveSwingingPart(root, name, true);
    }

    /**
     * Adds multiple positive-swinging (swinging to the right) model parts to a model by getting them from the root part.
     *
     * @param root  The root model part.
     * @param names The names of the parts.
     */
    public Set<ModelPart> addPositiveSwingingParts(ModelPart root, String... names) {
        Set<ModelPart> set = new HashSet<>();
        for (String s : names) {
            set.add(addPositiveSwingingPart(root, s));
        }
        return set;
    }

    /**
     * Adds a negative-swinging (swinging to the left) model part to a model by getting it from the root part.
     *
     * @param root     The root model part.
     * @param name     The name of the part.
     * @param levitate Whether this part should have a levitation animation or not.
     * @return The model part added.
     */
    public ModelPart addNegativeSwingingPart(ModelPart root, String name, boolean levitate) {
        ModelPart part = addPart(root, name, levitate);
        NEGATIVE_SWINGING_PARTS.add(part);
        return part;
    }

    /**
     * Adds a Negative-swinging (swinging to the right) model part to a model by getting it from the root part.
     *
     * @param root The root model part.
     * @param name The name of the part.
     * @return The model part added.
     */
    public ModelPart addNegativeSwingingPart(ModelPart root, String name) {
        return addNegativeSwingingPart(root, name, true);
    }

    /**
     * Adds multiple Negative-swinging (swinging to the right) model parts to a model by getting them from the root part.
     *
     * @param root  The root model part.
     * @param names The names of the parts.
     */
    public Set<ModelPart> addNegativeSwingingParts(ModelPart root, String... names) {
        Set<ModelPart> set = new HashSet<>();
        for (String s : names) {
            set.add(addNegativeSwingingPart(root, s));
        }
        return set;
    }
}
