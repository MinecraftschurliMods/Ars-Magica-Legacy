package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskFuelManager;
import com.google.gson.Gson;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class AMObeliskFuelProvider implements DataProvider {
    private final Map<String, ObeliskFuelManager.ObeliskFuel> map = new HashMap<>();
    private final Logger logger = LogManager.getLogger();
    private final DataGenerator generator;
    private final String modId;

    public AMObeliskFuelProvider(final DataGenerator generator, final String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    protected void addData() {
        forTag("vinteum_dust", AMTags.Items.DUSTS_VINTEUM, 200, 1);
        forTag("vinteum_block", AMTags.Items.STORAGE_BLOCKS_VINTEUM, 900, 2);
        // TODO essence bucket
    }

    protected void forItem(final String name, final Item item, final int burntime, final int valuepertick) {
        forIngredient(name, Ingredient.of(item), burntime, valuepertick);
    }

    protected void forTag(final String name, final Tag.Named<Item> tag, final int burntime, final int valuepertick) {
        forIngredient(name, Ingredient.of(tag), burntime, valuepertick);
    }

    protected void forIngredient(final String name, final Ingredient ingredient, final int burntime, final int valuepertick) {
        map.put(name, new ObeliskFuelManager.ObeliskFuel(ingredient, burntime, valuepertick));
    }

    @Override
    public void run(final HashCache cache) throws IOException {
        addData();
        Path path = generator.getOutputFolder();
        for (Map.Entry<String, ObeliskFuelManager.ObeliskFuel> entry : map.entrySet()) {
            String name = entry.getKey();
            ObeliskFuelManager.ObeliskFuel obeliskFuel = entry.getValue();
            DataProvider.save(new Gson(), cache, ObeliskFuelManager.ObeliskFuel.CODEC.encodeStart(JsonOps.INSTANCE, obeliskFuel).getOrThrow(false, logger::warn), path.resolve("data/" + modId + "/obelisk_fuel/" + name + ".json"));
        }
    }

    @Override
    public String getName() {
        return "ObeliskFuelProvider("+modId+")";
    }
}
