package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Builder for skills. Used in SkillProviders for data generation.
 */
public class SkillBuilder {
    private final Set<ResourceLocation> parents = new HashSet<>();
    private final Map<ResourceLocation, Integer> cost = new HashMap<>();
    private final ResourceLocation id;
    private ResourceLocation occulusTab;
    private Boolean hidden;
    private Integer x;
    private Integer y;

    /**
     * Create a new {@link SkillBuilder} with the given id in the given occulusTab.
     *
     * @param id         the id for the skill
     * @param occulusTab the occulus tab this skill belongs to
     * @return the new {@link SkillBuilder} for the skill
     */
    @Contract("_, _ -> new")
    public static SkillBuilder create(ResourceLocation id, ResourceLocation occulusTab) {
        return new SkillBuilder(id).setOcculusTab(occulusTab);
    }

    /**
     * Create a new {@link SkillBuilder} with the given id in the given occulusTab.
     *
     * @param id         the id for the skill
     * @param occulusTab the occulus tab this skill belongs to
     * @return the new {@link SkillBuilder} for the skill
     */
    @Contract("_, _ -> new")
    public static SkillBuilder create(ResourceLocation id, IOcculusTab occulusTab) {
        return new SkillBuilder(id).setOcculusTab(occulusTab);
    }

    protected SkillBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * Get the id of this skill.
     *
     * @return the id of this skill
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * Add learning cost to this skill.
     *
     * @param point the point it should cost
     * @param amt   the amount of points it should cost
     * @return the {@link SkillBuilder}
     */
    @Contract("_, _ -> this")
    public SkillBuilder addCost(ResourceLocation point, int amt) {
        cost.compute(point, (key, i) -> i != null ? i + amt : amt);
        return this;
    }

    /**
     * Add learning cost to this skill.
     *
     * @param point the point it should cost
     * @param amt   the amount of points it should cost
     * @return the {@link SkillBuilder}
     */
    @Contract("_, _ -> this")
    public SkillBuilder addCost(ISkillPoint point, int amt) {
        return addCost(point.getId(), amt);
    }

    /**
     * Add learning cost to this skill.
     *
     * @param point the point it should cost
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder addCost(ResourceLocation point) {
        return addCost(point, 1);
    }

    /**
     * Add learning cost to this skill.
     *
     * @param point the point it should cost
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder addCost(ISkillPoint point) {
        return addCost(point, 1);
    }

    /**
     * Add a parent to this skill that is required to learn first.
     *
     * @param parent the parent to add to this skill
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder addParent(ResourceLocation parent) {
        parents.add(parent);
        return this;
    }

    /**
     * Add a parent to this skill that is required to learn first.
     *
     * @param parent the parent to add to this skill
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder addParent(ISkill parent) {
        return addParent(parent.getId());
    }

    /**
     * Add a parent to this skill that is required to learn first.
     *
     * @param parent the parent to add to this skill
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder addParent(SkillBuilder parent) {
        return addParent(parent.getId());
    }

    /**
     * Set the position this skill should be displayed at in the gui of the defined occulus tab.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the {@link SkillBuilder}
     */
    @Contract("_, _ -> this")
    public SkillBuilder setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set if this skill should be hidden.
     *
     * @param hidden the hidden state to set
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    /**
     * Set that this skill should be hidden.
     *
     * @return the {@link SkillBuilder}
     */
    @Contract("-> this")
    public SkillBuilder hidden() {
        return setHidden(true);
    }

    /**
     * Set the occulus tab this skill should belong to.
     *
     * @param occulusTab the occulus tab this skill should belong to
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    protected SkillBuilder setOcculusTab(ResourceLocation occulusTab) {
        this.occulusTab = occulusTab;
        return this;
    }

    /**
     * Set the occulus tab this skill should belong to.
     *
     * @param occulusTab the occulus tab this skill should belong to
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    protected SkillBuilder setOcculusTab(IOcculusTab occulusTab) {
        return setOcculusTab(occulusTab.getId());
    }

    /**
     * Build this {@link SkillBuilder}.<br>
     * This method accepts this builder to the provided consumer
     *
     * @param consumer the consumer that will consume this builder
     * @return the {@link SkillBuilder}
     */
    @Contract("_ -> this")
    public SkillBuilder build(Consumer<SkillBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("occulus_tab", occulusTab.toString());
        if (x == null || y == null) throw new SerializationException("A skill needs a position!");
        json.addProperty("x", x);
        json.addProperty("y", y);
        if (hidden != null) {
            json.addProperty("hidden", hidden);
        }
        if (!parents.isEmpty()) {
            json.add("parents", serializeParents());
        }
        if (!cost.isEmpty()) {
            json.add("cost", serializeCost());
        }
        return json;
    }

    private JsonObject serializeCost() {
        JsonObject cost = new JsonObject();
        this.cost.forEach((id, amt) -> cost.addProperty(id.toString(), amt));
        return cost;
    }

    private JsonArray serializeParents() {
        JsonArray parents = new JsonArray();
        this.parents.forEach(id -> parents.add(id.toString()));
        return parents;
    }
}
