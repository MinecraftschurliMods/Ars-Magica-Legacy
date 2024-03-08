package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SkillProvider extends AbstractDatapackRegistryProvider<Skill> {
    protected SkillProvider(String namespace) {
        super(Skill.REGISTRY_KEY, namespace);
    }

    /**
     * @param occulusTab The occulus tab to use.
     * @param x          The x position to use.
     * @param y          The y position to use.
     */
    protected Builder builder(OcculusTab occulusTab, int x, int y) {
        return builder(occulusTab.getId(), x, y);
    }

    /**
     * @param occulusTab The occulus tab to use.
     * @param x          The x position to use.
     * @param y          The y position to use.
     */
    protected Builder builder(ResourceLocation occulusTab, int x, int y) {
        return new Builder(occulusTab, x, y);
    }

    public static class Builder {
        private final Set<ResourceLocation> parents = new HashSet<>();
        private final Map<ResourceLocation, Integer> cost = new HashMap<>();
        private final ResourceLocation occulusTab;
        private final int x;
        private final int y;
        private boolean hidden = false;

        public Builder(ResourceLocation occulusTab, int x, int y) {
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
         * @param point  The skill point the skill should cost.
         * @param amount The amount of skill points the skill should cost.
         */
        public Builder addCost(Holder<SkillPoint> point, int amount) {
            return addCost(point.unwrapKey().get().location(), amount);
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
         * Adds a learning cost to this skill.
         *
         * @param point The skill point the skill should cost.
         */
        public Builder addCost(Holder<SkillPoint> point) {
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

        public Skill build() {
            return new Skill(parents, cost, occulusTab, x, y, hidden);
        }
    }
}
