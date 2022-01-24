package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
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
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class TierMapping extends SimplePreparableReloadListener<JsonArray> {
    private static final ResourceLocation TIER_MAPPING = new ResourceLocation(ArsMagicaAPI.MOD_ID, "tier_mapping.json");
    private static final Lazy<TierMapping> INSTANCE = Lazy.concurrentOf(TierMapping::new);
    private final Logger LOGGER = LogManager.getLogger();
    private final Gson gson = new GsonBuilder().create();
    private final List<ResourceLocation> tiers = new ArrayList<>();

    private TierMapping() {
    }

    public static TierMapping instance() {
        return INSTANCE.get();
    }

    @Override
    protected JsonArray prepare(ResourceManager resourceManager, ProfilerFiller p) {
        if (!resourceManager.hasResource(TIER_MAPPING)) {
            return new JsonArray();
        }
        try (Resource r = resourceManager.getResource(TIER_MAPPING); InputStream stream = r.getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            LOGGER.error("Could not read Tier sorting file " + TIER_MAPPING, e);
            return new JsonArray();
        }
    }

    @Override
    protected void apply(JsonArray data, ResourceManager resourceManager, ProfilerFiller p) {
        tiers.clear();
        for (int i = 0; i < data.size(); i++) {
            tiers.add(ResourceLocation.tryParse(GsonHelper.convertToString(data.get(i), "tiers[" + i + "]")));
        }
    }

    public Tier getTierForPower(int tier) {
        if (this.tiers.size() == 0) {
            return switch (tier) {
                case 0 -> Tiers.WOOD;
                case 1 -> Tiers.STONE;
                case 2 -> Tiers.IRON;
                case 3 -> Tiers.DIAMOND;
                case 4 -> Tiers.NETHERITE;
                default -> null;
            };
        }
        return TierSortingRegistry.byName(tiers.get(Math.min(tier, tiers.size() - 1)));
    }
}
