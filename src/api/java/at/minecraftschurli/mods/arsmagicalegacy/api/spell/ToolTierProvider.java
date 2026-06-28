package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

/// Data provider for tool tiers. In Ars Magica: Legacy, all a tool tier entails is an int -> incorrect block tag mapping. Override [ToolTierProvider#generate()] to generate your entries,
/// and use [ToolTierProvider#add(int, TagKey)] or [ToolTierProvider#add(int, Identifier)] to add a new tool tier.
public abstract class ToolTierProvider implements DataProvider {
    private static final String PATH = "tool_tiers.json";
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final String modId;
    private final Int2ObjectMap<Identifier> contents = new Int2ObjectOpenHashMap<>();

    /// @param output         The [PackOutput] to use. Get this from [GatherDataEvent].
    /// @param lookupProvider The lookup [CompletableFuture] to use. Get this from [GatherDataEvent].
    /// @param modId          Your mod id.
    public ToolTierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        this.output = output;
        this.lookupProvider = lookupProvider;
        this.modId = modId;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return lookupProvider.thenCompose(_ -> {
            generate();
            JsonObject json = new JsonObject();
            for (Int2ObjectMap.Entry<Identifier> entry : contents.int2ObjectEntrySet()) {
                json.addProperty(String.valueOf(entry.getIntKey()), entry.getValue().toString());
            }
            return DataProvider.saveStable(output, json, this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(ArsMagicaApi.MOD_ID).resolve(PATH));
        });
    }

    @Override
    public String getName() {
        return "Tool Tiers: " + modId;
    }

    /// Adds a tool tier.
    ///
    /// @param toolTier           The tool tier to add.
    /// @param incorrectBlocksTag A [TagKey] of incorrect [Block]s for this tool tier.
    public void add(int toolTier, TagKey<Block> incorrectBlocksTag) {
        add(toolTier, incorrectBlocksTag.location());
    }

    /// Adds a tool tier.
    ///
    /// @param toolTier           The tool tier to add.
    /// @param incorrectBlocksTag The id of a [TagKey] of incorrect [Block]s for this tool tier.
    public void add(int toolTier, Identifier incorrectBlocksTag) {
        contents.put(toolTier, incorrectBlocksTag);
    }

    /// Override this to generate your objects.
    public abstract void generate();
}
