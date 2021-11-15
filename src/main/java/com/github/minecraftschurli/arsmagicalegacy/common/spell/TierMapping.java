package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.EventHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TierMapping extends SimplePreparableReloadListener<JsonArray> {
    private static final ResourceLocation TIER_MAPPING = new ResourceLocation(ArsMagicaAPI.MOD_ID, "tier_mapping.json");
    private static final Lazy<TierMapping> INSTANCE = Lazy.concurrentOf(TierMapping::new);

    private final Logger                 LOGGER = LogManager.getLogger();
    private final Gson                   gson   = (new GsonBuilder()).create();
    private final List<ResourceLocation> tiers  = new ArrayList<>();

    public static TierMapping instance() {
        return INSTANCE.get();
    }

    @Nonnull
    @Override
    protected JsonArray prepare(@Nonnull ResourceManager resourceManager, ProfilerFiller p) {
        if (!resourceManager.hasResource(TIER_MAPPING)) {
            return new JsonArray();
        }

        try (Resource r = resourceManager.getResource(TIER_MAPPING); InputStream stream = r.getInputStream(); Reader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            LOGGER.error("Could not read Tier sorting file " + TIER_MAPPING, e);
            return new JsonArray();
        }
    }

    @Override
    protected void apply(@Nonnull JsonArray data, @Nonnull ResourceManager resourceManager, ProfilerFiller p) {
        tiers.clear();
        for (int i = 0; i < data.size(); i++) {
            tiers.add(ResourceLocation.tryParse(GsonHelper.convertToString(data.get(i), "tiers[" + i + "]")));
        }
    }

    public Tier getTierForPower(int tier) {
        return TierSortingRegistry.byName(tiers.get(Math.min(tier, tiers.size() - 1)));
    }
}
