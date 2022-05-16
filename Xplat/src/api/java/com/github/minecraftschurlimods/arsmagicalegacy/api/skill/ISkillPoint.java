package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IRegistryEntry;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface ISkillPoint extends IRegistryEntry, ITranslatable.OfRegistryEntry<ISkillPoint> {
    ResourceKey<Registry<ISkillPoint>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "skill_point"));

    /**
     * @return The color for this skill point.
     */
    int color();

    /**
     * @return The amount of levels needed to get the next skill point.
     */
    int levelsForPoint();

    /**
     * @return The minimum amount of levels needed to get this skill point.
     */
    int minEarnLevel();

    @Override
    default ResourceKey<Registry<ISkillPoint>> getRegistryKey() {
        return REGISTRY_KEY;
    }
}
