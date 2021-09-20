package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

abstract class AMLanguageProvider extends LanguageProvider {
    public AMLanguageProvider(DataGenerator generator, String locale) {
        super(generator, ArsMagicaAPI.MOD_ID, locale);
    }
}
