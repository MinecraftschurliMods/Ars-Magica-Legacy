package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * @param color          The color for this skill point.
 * @param minEarnLevel   The amount of levels needed to get the next skill point.
 * @param levelsForPoint The minimum amount of levels needed to get this skill point.
 */
public record SkillPoint(int color, int minEarnLevel, int levelsForPoint) implements ITranslatable {
    public static final String SKILL_POINT = "skill_point";
    public static final ResourceKey<Registry<SkillPoint>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, SKILL_POINT));

    @Override
    public String getType() {
        return SKILL_POINT;
    }

    @Override
    public ResourceLocation getId() {
        return Objects.requireNonNull(ArsMagicaAPI.get().getSkillPointRegistry().getKey(this));
    }
}
