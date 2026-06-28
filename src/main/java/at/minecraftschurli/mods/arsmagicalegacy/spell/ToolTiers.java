package at.minecraftschurli.mods.arsmagicalegacy.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Strictness;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.IdentifierException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Optional;

public final class ToolTiers extends SimplePreparableReloadListener<JsonObject> {
    public static final Identifier ID = ArsMagicaApi.id("tool_tiers");
    public static final ToolTiers INSTANCE = new ToolTiers();
    public static final Identifier PATH = ArsMagicaApi.id("tool_tiers.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setStrictness(Strictness.LENIENT).create();
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolTiers.class);
    private final Int2ObjectMap<TagKey<Block>> contents = new Int2ObjectOpenHashMap<>();

    private ToolTiers() {
    }

    @Override
    protected JsonObject prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Optional<Resource> resource = resourceManager.getResource(PATH);
        if (resource.isEmpty()) return new JsonObject();
        try (Reader reader = resource.get().openAsReader()) {
            return GSON.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read tool tiers file at {}", PATH, e);
            return new JsonObject();
        }
    }

    @Override
    protected void apply(JsonObject object, ResourceManager resourceManager, ProfilerFiller profiler) {
        contents.clear();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            try {
                int i = Integer.parseInt(entry.getKey());
                JsonElement value = entry.getValue();
                if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                    contents.put(i, TagKey.create(Registries.BLOCK, Identifier.parse(value.getAsString())));
                } else {
                    LOGGER.warn("Found non-string value {} in {}, ignoring", entry.getValue(), PATH);
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Found non-integer key {} in {}, ignoring", entry.getKey(), PATH);
            } catch (IdentifierException e) {
                LOGGER.warn("Encountered invalid identifier in {}, ignoring", PATH, e);
            }
        }
        if (contents.isEmpty()) {
            LOGGER.error("Did not register any tool tiers, this will cause issues as soon as the Dig component is cast! See earlier in the log for details.");
        } else if (!contents.containsKey(0)) {
            LOGGER.error("Did not register a fallback tool tier (tool tier with index 0), this is very likely to cause issues! See earlier in the log for details.");
        }
    }

    public TagKey<Block> get(int i) {
        return contents.get(i);
    }
}
