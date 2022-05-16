package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

abstract class AMLanguageProvider extends LanguageProvider {
    AMLanguageProvider(DataGenerator generator, String locale) {
        super(generator, ArsMagicaAPI.MOD_ID, locale);
    }
}
