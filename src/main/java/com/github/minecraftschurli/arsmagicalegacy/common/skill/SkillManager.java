package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurli.arsmagicalegacy.network.SyncSkillsPacket;
import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.OnDatapackSyncEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.*;
import java.util.stream.Collectors;


public final class SkillManager extends SimpleJsonResourceReloadListener implements ISkillManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Lazy<SkillManager> INSTANCE = Lazy.concurrentOf(SkillManager::new);
    private final Map<ResourceLocation, ISkill> skills = new HashMap<>();

    private SkillManager() {
        super(GSON, "am_skills");
    }

    @Override
    public Set<ISkill> getSkillsForOcculusTab(final ResourceLocation id) {
        return skills.values().stream().filter(skill -> id.equals(skill.getOcculusTab())).collect(Collectors.toSet());
    }

    @Override
    public Optional<ISkill> getOptional(ResourceLocation id) {
        return Optional.ofNullable(skills.get(id));
    }

    @Override
    public Collection<ISkill> getSkills() {
        return Collections.unmodifiableCollection(skills.values());
    }

    public static SkillManager instance() {
        return INSTANCE.get();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        skills.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
            ResourceLocation key = entry.getKey();
            skills.put(key, Skill.CODEC.decode(JsonOps.INSTANCE, entry.getValue())
                    .map(Pair::getFirst)
                    .map(Skill.class::cast)
                    .getOrThrow(true, LOGGER::error).setId(key));
        }
    }

    @Internal
    public void receiveSync(Collection<ISkill> values) {
        this.skills.clear();
        values.forEach(iSkill -> this.skills.put(iSkill.getId(), iSkill));
    }

    public void onSync(OnDatapackSyncEvent event) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayerOrAll(new SyncSkillsPacket(skills.values()), event.getPlayer());
    }
}
