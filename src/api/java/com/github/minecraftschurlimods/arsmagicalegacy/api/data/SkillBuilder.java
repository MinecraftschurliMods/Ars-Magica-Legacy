package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
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

    protected SkillBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * @param id         The id for the skill.
     * @param occulusTab The id of the occulus tab for the skill.
     * @return A new builder for the skill.
     */
    @Contract("_, _ -> new")
    public static SkillBuilder create(ResourceLocation id, ResourceLocation occulusTab) {
        return new SkillBuilder(id).setOcculusTab(occulusTab);
    }

    /**
     * @param id         The id for the skill.
     * @param occulusTab The occulus tab for the skill.
     * @return A new builder for the skill.
     */
    @Contract("_, _ -> new")
    public static SkillBuilder create(ResourceLocation id, IOcculusTab occulusTab) {
        return new SkillBuilder(id).setOcculusTab(occulusTab);
    }

    /**
     * @return The id of the skill.
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * Adds a learning cost to this skill.
     *
     * @param point The id of the skill point the skill should cost.
     * @param amt   The amount of skill points the skill should cost.
     * @return This builder, for chaining.
     */
    @Contract("_, _ -> this")
    public SkillBuilder addCost(ResourceLocation point, int amt) {
        cost.compute(point, (key, i) -> i != null ? i + amt : amt);
        return this;
    }

    /**
     * Adds a learning cost to this skill.
     *
     * @param point The skill point the skill should cost.
     * @param amt   The amount of skill points the skill should cost.
     * @return This builder, for chaining.
     */
    @Contract("_, _ -> this")
    public SkillBuilder addCost(ISkillPoint point, int amt) {
        return addCost(point.getId(), amt);
    }

    /**
     * Adds a learning cost to this skill.
     *
     * @param point The skill point the skill should cost.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder addCost(ResourceLocation point) {
        return addCost(point, 1);
    }

    /**
     * Adds a learning cost to this skill.
     *
     * @param point The skill point the skill should cost.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder addCost(ISkillPoint point) {
        return addCost(point, 1);
    }

    /**
     * Adds a parent skill that must be learned first.
     *
     * @param parent The id of the parent skill to add.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder addParent(ResourceLocation parent) {
        parents.add(parent);
        return this;
    }

    /**
     * Adds a parent skill that must be learned first.
     *
     * @param parent The parent skill to add.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder addParent(ISkill parent) {
        return addParent(parent.getId());
    }

    /**
     * Adds a parent skill that must be learned first.
     *
     * @param parent A builder for the parent skill to add.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder addParent(SkillBuilder parent) {
        return addParent(parent.getId());
    }

    /**
     * Sets the position this skill should be displayed at in the defined occulus tab.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @return This builder, for chaining.
     */
    @Contract("_, _ -> this")
    public SkillBuilder setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Sets if this skill should be hidden or not. Default behavior is false.
     *
     * @param hidden The hidden state to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    /**
     * Sets that this skill should be hidden.
     *
     * @return This builder, for chaining.
     */
    @Contract("-> this")
    public SkillBuilder hidden() {
        return setHidden(true);
    }

    /**
     * Sets the occulus tab this skill belongs to.
     *
     * @param occulusTab The id of the occulus tab to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    protected SkillBuilder setOcculusTab(ResourceLocation occulusTab) {
        this.occulusTab = occulusTab;
        return this;
    }

    /**
     * Sets the occulus tab this skill belongs to.
     *
     * @param occulusTab The occulus tab to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    protected SkillBuilder setOcculusTab(IOcculusTab occulusTab) {
        return setOcculusTab(occulusTab.getId());
    }

    /**
     * Builds the skill.
     *
     * @param consumer The consumer that will consume the builder.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public SkillBuilder build(Consumer<SkillBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    /**
     * @return The serialized occulus tab.
     */
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
            JsonArray parents = new JsonArray();
            this.parents.forEach(id -> parents.add(id.toString()));
            json.add("parents", parents);
        }
        if (!cost.isEmpty()) {
            JsonObject cost = new JsonObject();
            this.cost.forEach((id, amt) -> cost.addProperty(id.toString(), amt));
            json.add("cost", cost);
        }
        return json;
    }
}
