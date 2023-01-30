package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.SerializationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SkillProvider extends AbstractDataProvider<SkillProvider.Builder> {
    private final Set<ResourceLocation> data = new HashSet<>();

    protected SkillProvider(String namespace, DataGenerator generator) {
        super("am_skills", namespace, generator);
    }

    @Override
    public String getName() {
        return "Skills[" + namespace + "]";
    }

    @Override
    protected void onSave(Builder object) {
        data.add(object.id);
    }

    /**
     * @return The skills generated.
     */
    public Set<ResourceLocation> getSkills() {
        return Collections.unmodifiableSet(data);
    }

    /**
     * @param name The skill name.
     * @return A new skill.
     */
    protected Builder builder(String name) {
        return new Builder(new ResourceLocation(namespace, name));
    }

    public static class Builder extends AbstractDataBuilder<Builder> {
        private final Set<ResourceLocation> parents = new HashSet<>();
        private final Map<ResourceLocation, Integer> cost = new HashMap<>();
        private ResourceLocation occulusTab;
        private Integer x;
        private Integer y;
        private Boolean hidden;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the occulus tab this skill belongs to.
         *
         * @param occulusTab The id of the occulus tab to set.
         * @return This builder, for chaining.
         */
        public Builder setOcculusTab(ResourceLocation occulusTab) {
            this.occulusTab = occulusTab;
            return this;
        }

        /**
         * Sets the occulus tab this skill belongs to.
         *
         * @param occulusTab The occulus tab to set.
         * @return This builder, for chaining.
         */
        public Builder setOcculusTab(IOcculusTab occulusTab) {
            return setOcculusTab(occulusTab.getId());
        }

        /**
         * Sets if this skill should be hidden or not. Default behavior is false.
         *
         * @param hidden The hidden state to set.
         * @return This builder, for chaining.
         */
        public Builder setHidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        /**
         * Sets the position this skill should be displayed at in the defined occulus tab.
         *
         * @param x The X coordinate.
         * @param y The Y coordinate.
         * @return This builder, for chaining.
         */
        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        /**
         * Sets that this skill should be hidden.
         *
         * @return This builder, for chaining.
         */
        public Builder setHidden() {
            return setHidden(true);
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point  The id of the skill point the skill should cost.
         * @param amount The amount of skill points the skill should cost.
         * @return This builder, for chaining.
         */
        public Builder addCost(ResourceLocation point, int amount) {
            cost.compute(point, (key, i) -> i != null ? i + amount : amount);
            return this;
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point  The skill point the skill should cost.
         * @param amount The amount of skill points the skill should cost.
         * @return This builder, for chaining.
         */
        public Builder addCost(ISkillPoint point, int amount) {
            return addCost(point.getId(), amount);
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point The skill point the skill should cost.
         * @return This builder, for chaining.
         */
        public Builder addCost(ResourceLocation point) {
            return addCost(point, 1);
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point The skill point the skill should cost.
         * @return This builder, for chaining.
         */
        public Builder addCost(ISkillPoint point) {
            return addCost(point, 1);
        }

        /**
         * Adds a parent skill that must be learned first.
         *
         * @param parent The id of the parent skill to add.
         * @return This builder, for chaining.
         */
        public Builder addParent(ResourceLocation parent) {
            parents.add(parent);
            return this;
        }

        /**
         * Adds a parent skill that must be learned first.
         *
         * @param parent The parent skill to add.
         * @return This builder, for chaining.
         */
        public Builder addParent(ISkill parent) {
            return addParent(parent.getId());
        }

        /**
         * Adds a parent skill that must be learned first.
         *
         * @param parent A builder for the parent skill to add.
         * @return This builder, for chaining.
         */
        public Builder addParent(Builder parent) {
            return addParent(parent.id);
        }

        @Override
        protected JsonObject toJson() {
            JsonObject json = new JsonObject();
            if (occulusTab == null) throw new SerializationException("A skill needs an occulus tab!");
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
                this.cost.forEach((id, amount) -> cost.addProperty(id.toString(), amount));
                json.add("cost", cost);
            }
            return json;
        }
    }
}
