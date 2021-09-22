package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillManager;
import com.google.gson.*;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.util.Lazy;

import java.util.*;
import java.util.stream.Collectors;

public final class SkillManager extends SimpleJsonResourceReloadListener implements ISkillManager {
    private static final Codec<ISkill> CODEC = null;
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

    public static SkillManager instance() {
        return INSTANCE.get();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        skills.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonObject json = entry.getValue().getAsJsonObject();
            if (json.entrySet().size() == 0) {
                continue;
            }
            Set<ResourceLocation> parents = new HashSet<>();
            var parentsJson = GsonHelper.getAsJsonArray(json, "parents", new JsonArray());
            if (parentsJson != null) {
                for (JsonElement parent : parentsJson) {
                    parents.add(new ResourceLocation(parent.getAsString()));
                }
            }
            Map<ResourceLocation, Integer> cost = new HashMap<>();
            for (Map.Entry<String, JsonElement> costEntry : GsonHelper.getAsJsonObject(json, "cost", new JsonObject()).entrySet()) {
                cost.put(new ResourceLocation(costEntry.getKey()), costEntry.getValue().getAsInt());
            }
            ResourceLocation occulus_tab = new ResourceLocation(GsonHelper.getAsString(json, "occulus_tab"));
            ResourceLocation icon = new ResourceLocation(GsonHelper.getAsString(json, "icon"));
            int x = GsonHelper.getAsInt(json, "x");
            int y = GsonHelper.getAsInt(json, "y");
            boolean hidden = GsonHelper.getAsBoolean(json, "hidden", false);
            skills.put(key, new Skill(key, occulus_tab, icon, parents, x, y, cost, hidden));
        }
    }
}
