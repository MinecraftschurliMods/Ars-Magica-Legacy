package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.SerializationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SkillProvider extends AbstractDataProvider<Skill, SkillProvider.Builder> {

    protected SkillProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(Skill.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Skills[" + namespace + "]";
    }

    /**
     * @return The skills generated.
     */
    public Set<ResourceLocation> getSkills() {
        return dataView.keySet();
    }

    /**
     * @param name The skill name.
     * @return A new skill.
     */
    protected Builder builder(String name) {
        return new Builder(new ResourceLocation(namespace, name));
    }

    public static class Builder extends AbstractDataBuilder<Skill, Builder> {
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
        public Builder setOcculusTab(OcculusTab occulusTab) {
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
        public Builder addCost(SkillPoint point, int amount) {
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
        public Builder addCost(SkillPoint point) {
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
         * @param parent A builder for the parent skill to add.
         * @return This builder, for chaining.
         */
        public Builder addParent(Builder parent) {
            return addParent(parent.id);
        }

        @Override
        protected Skill build() {
            if (occulusTab == null) throw new SerializationException("A skill needs an occulus tab!");
            if (x == null || y == null) throw new SerializationException("A skill needs a position!");
            return new Skill(parents, cost, occulusTab, x, y, hidden);
        }
    }
}
