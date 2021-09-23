package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.SerializationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SkillBuilder {
    private final Set<ResourceLocation> parents = new HashSet<>();
    private final Map<ResourceLocation, Integer> cost = new HashMap<>();
    private final ResourceLocation id;
    private ResourceLocation occulusTab;
    private ResourceLocation icon;
    private Boolean hidden;
    private Integer x;
    private Integer y;

    public static SkillBuilder create(ResourceLocation id, ResourceLocation occulusTab, ResourceLocation icon) {
        return new SkillBuilder(id).setOcculusTab(occulusTab).setIcon(icon);
    }

    public static SkillBuilder create(ResourceLocation id, IOcculusTab occulusTab, ResourceLocation icon) {
        return new SkillBuilder(id).setOcculusTab(occulusTab).setIcon(icon);
    }

    private SkillBuilder(final ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public SkillBuilder addCost(ResourceLocation point, int amt) {
        this.cost.compute(point, (key, i) -> i != null ? i + amt : amt);
        return this;
    }

    public SkillBuilder addCost(ISkillPoint point, int amt) {
        return addCost(point.getId(), amt);
    }

    public SkillBuilder addCost(ResourceLocation point) {
        return addCost(point, 1);
    }

    public SkillBuilder addCost(ISkillPoint point) {
        return addCost(point, 1);
    }

    public SkillBuilder addParent(ResourceLocation parent) {
        this.parents.add(parent);
        return this;
    }

    public SkillBuilder addParent(ISkill parent) {
        return addParent(parent.getId());
    }

    public SkillBuilder addParent(SkillBuilder parent) {
        return addParent(parent.getId());
    }

    public SkillBuilder setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public SkillBuilder setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public SkillBuilder hidden() {
        return setHidden(true);
    }

    protected SkillBuilder setIcon(ResourceLocation icon) {
        this.icon = icon;
        return this;
    }

    protected SkillBuilder setOcculusTab(ResourceLocation occulusTab) {
        this.occulusTab = occulusTab;
        return this;
    }

    protected SkillBuilder setOcculusTab(IOcculusTab occulusTab) {
        return setOcculusTab(occulusTab.getId());
    }

    public SkillBuilder build(Consumer<SkillBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("occulus_tab", this.occulusTab.toString());
        json.addProperty("icon", this.icon.toString());
        if (this.x == null || this.y == null)
            throw new SerializationException("A skill needs a position!");
        json.addProperty("x", this.x);
        json.addProperty("y", this.y);
        if (this.hidden != null)
            json.addProperty("hidden", this.hidden);
        if (!this.parents.isEmpty())
            json.add("parents", serializeParents());
        if (!this.cost.isEmpty())
            json.add("cost", serializeCost());
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
