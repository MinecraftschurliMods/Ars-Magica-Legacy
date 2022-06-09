package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class SkillManager extends CodecDataManager<ISkill> implements ISkillManager {
    private static final Lazy<SkillManager> INSTANCE = Lazy.concurrentOf(SkillManager::new);

    private SkillManager() {
        super(ArsMagicaAPI.MOD_ID, "am_skills", Skill.CODEC, Skill.NETWORK_CODEC, SkillManager::validateSkills, LoggerFactory.getLogger(SkillManager.class));
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    /**
     * @return The only instance of this class.
     */
    public static SkillManager instance() {
        return INSTANCE.get();
    }

    private static void validateSkills(Map<ResourceLocation, ISkill> data, Logger logger) {
        data.forEach((id, skill) -> ((Skill) skill).setId(id));
        data.values().removeIf(skill -> {
            boolean err = false;
            if (!data.keySet().containsAll(skill.getParents())) {
                logger.warn("Skill {} is missing parents {}. It will be removed!", skill.getId(), new HashSet<>(skill.getParents()).removeAll(data.keySet()));
                err = true;
            }
            Optional<IOcculusTab> tabOpt = ArsMagicaAPI.get().getOcculusTabManager().getOptional(skill.getOcculusTab());
            if (tabOpt.isEmpty()) {
                logger.warn("The occulus tab {} for skill {} is not available. The skill will be removed!", skill.getOcculusTab(), skill.getId());
                return true;
            }
            IOcculusTab tab = tabOpt.get();
            if (skill.getY() < 0 || skill.getY() > (tab.getHeight() - 32) || skill.getX() < 0 || skill.getX() > (tab.getWidth() - 32)) {
                logger.warn("Skill {} is outside the bounds of the skill tree. It will be removed!", skill.getId());
                err = true;
            }
            return err;
        });
    }

    @Override
    public Set<ISkill> getSkillsForOcculusTab(ResourceLocation id) {
        return values().stream().filter(skill -> id.equals(skill.getOcculusTab())).collect(Collectors.toSet());
    }

    @Override
    public Set<ISkill> getSkillsForOcculusTab(IOcculusTab tab) {
        return getSkillsForOcculusTab(tab.getId());
    }

    @Override
    public Optional<ISkill> getOptional(@Nullable ResourceLocation id) {
        return super.getOptional(id);
    }

    @Nullable
    @Override
    public ISkill get(@Nullable ResourceLocation id) {
        return super.get(id);
    }

    @Override
    public ISkill getOrThrow(@Nullable ResourceLocation id) {
        return super.getOrThrow(id);
    }

    @Override
    public Collection<ISkill> getSkills() {
        return values();
    }
}
