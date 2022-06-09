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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TierMapping extends SimplePreparableReloadListener<JsonArray> {
    private static final ResourceLocation TIER_MAPPING = new ResourceLocation(ArsMagicaAPI.MOD_ID, "tier_mapping.json");
    private static final Lazy<TierMapping> INSTANCE = Lazy.concurrentOf(TierMapping::new);
    private final Logger LOGGER = LogManager.getLogger();
    private final Gson gson = new GsonBuilder().create();
    private final List<ResourceLocation> tiers = new ArrayList<>();

    private TierMapping() {}

    /**
     * @return The only instance of this class.
     */
    public static TierMapping instance() {
        return INSTANCE.get();
    }

    @Override
    protected JsonArray prepare(ResourceManager resourceManager, ProfilerFiller p) {
        Optional<Resource> resource = resourceManager.getResource(TIER_MAPPING);
        if (resource.isEmpty()) {
            return new JsonArray();
        }
        Resource r = resource.get();
        try (Reader reader = r.openAsReader()) {
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

    /**
     * @param tier The number to get the tier for.
     * @return The harvest tier for the given number.
     */
    @Nullable
    public Tier getTierForPower(int tier) {
        if (tiers.size() == 0) {
            return switch (tier) {
                case 1 -> Tiers.STONE;
                case 2 -> Tiers.IRON;
                case 3 -> Tiers.DIAMOND;
                case 4 -> Tiers.NETHERITE;
                default -> Tiers.WOOD;
            };
        }
        return TierSortingRegistry.byName(tiers.get(Math.min(tier, tiers.size() - 1)));
    }
}
