package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.ObeliskFuelProvider;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

class AMObeliskFuelProvider extends ObeliskFuelProvider {
    AMObeliskFuelProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    protected void generate(Consumer<Builder> consumer) {
        add(consumer, builder("vinteum_dust", AMTags.Items.DUSTS_VINTEUM, 200, 1));
        add(consumer, builder("vinteum_block", AMTags.Items.STORAGE_BLOCKS_VINTEUM, 900, 2));
        // TODO essence bucket
    }
}
