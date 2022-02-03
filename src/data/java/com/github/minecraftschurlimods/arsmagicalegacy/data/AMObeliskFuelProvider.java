package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.ObeliskFuelProvider;
import net.minecraft.data.DataGenerator;

class AMObeliskFuelProvider extends ObeliskFuelProvider {
    AMObeliskFuelProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    protected void createFuels() {
        forTag("vinteum_dust", AMTags.Items.DUSTS_VINTEUM, 200, 1);
        forTag("vinteum_block", AMTags.Items.STORAGE_BLOCKS_VINTEUM, 900, 2);
        // TODO essence bucket
    }
}
