package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.ObeliskFuelProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

class AMObeliskFuelProvider extends ObeliskFuelProvider {
    AMObeliskFuelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    protected void generate(Consumer<Builder> consumer) {
        builder("vinteum_dust", AMTags.Items.DUSTS_VINTEUM, 200, 1).build(consumer);
        builder("vinteum_block", AMTags.Items.STORAGE_BLOCKS_VINTEUM, 900, 2).build(consumer);
        builder("liquid_essence_bucket", AMItems.LIQUID_ESSENCE_BUCKET.get(), 1000, 2).build(consumer);
    }
}
