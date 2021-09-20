package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMItemModelProvider extends ItemModelProvider {
    public AMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
