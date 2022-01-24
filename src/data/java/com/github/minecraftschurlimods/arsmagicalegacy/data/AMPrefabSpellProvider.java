package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellProvider;
import net.minecraft.data.DataGenerator;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createPrefabSpells() {
    }
}
