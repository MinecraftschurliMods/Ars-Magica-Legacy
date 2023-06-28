package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SkillProvider extends AbstractRegistryDataProvider<Skill, SkillProvider.Builder> {
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
        return Collections.unmodifiableSet(data.keySet());
    }

    /**
     * @param name       The name of the skill.
     * @param occulusTab The occulus tab to use.
     * @param x          The x position to use.
     * @param y          The y position to use.
     */
    protected Builder builder(String name, OcculusTab occulusTab, int x, int y) {
        return builder(name, occulusTab.getId(), x, y);
    }

    /**
     * @param name       The name of the skill.
     * @param occulusTab The occulus tab to use.
     * @param x          The x position to use.
     * @param y          The y position to use.
     */
    protected Builder builder(String name, ResourceLocation occulusTab, int x, int y) {
        return new Builder(new ResourceLocation(namespace, name), this, occulusTab, x, y);
    }

    public static class Builder extends AbstractRegistryDataProvider.Builder<Skill, Builder> {
        private final Set<ResourceLocation> parents = new HashSet<>();
        private final Map<ResourceLocation, Integer> cost = new HashMap<>();
        private final ResourceLocation occulusTab;
        private final int x;
        private final int y;
        private boolean hidden = false;

        public Builder(ResourceLocation id, SkillProvider provider, ResourceLocation occulusTab, int x, int y) {
            super(id, provider, Skill.DIRECT_CODEC);
            this.occulusTab = occulusTab;
            this.x = x;
            this.y = y;
        }

        /**
         * Sets this skill's hidden property to true.
         */
        public Builder setHidden() {
            this.hidden = true;
            return this;
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point  The id of the skill point the skill should cost.
         * @param amount The amount of skill points the skill should cost.
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
         */
        public Builder addCost(SkillPoint point, int amount) {
            return addCost(point.getId(), amount);
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point The skill point the skill should cost.
         */
        public Builder addCost(ResourceLocation point) {
            return addCost(point, 1);
        }

        /**
         * Adds a learning cost to this skill.
         *
         * @param point The skill point the skill should cost.
         */
        public Builder addCost(SkillPoint point) {
            return addCost(point, 1);
        }

        /**
         * Adds a parent skill that must be learned first.
         *
         * @param parent The id of the parent skill to add.
         */
        public Builder addParent(ResourceLocation parent) {
            parents.add(parent);
            return this;
        }

        /**
         * Adds a parent skill that must be learned first.
         *
         * @param parent A builder for the parent skill to add.
         */
        public Builder addParent(Builder parent) {
            return addParent(parent.id);
        }

        @Override
        protected Skill get() {
            return new Skill(parents, cost, occulusTab, x, y, hidden);
        }
    }
}
