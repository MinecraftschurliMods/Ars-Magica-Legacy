package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.ObeliskFuelProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.world.item.crafting.Ingredient;

class AMObeliskFuelProvider extends ObeliskFuelProvider {
    AMObeliskFuelProvider() {
        super(ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        add("vinteum_dust", new ObeliskFuel(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 200, 1));
        add("vinteum_block", new ObeliskFuel(Ingredient.of(AMTags.Items.STORAGE_BLOCKS_VINTEUM), 900, 2));
        add("liquid_essence_bucket", new ObeliskFuel(Ingredient.of(AMItems.LIQUID_ESSENCE_BUCKET.get()), 1000, 2));
    }
}
