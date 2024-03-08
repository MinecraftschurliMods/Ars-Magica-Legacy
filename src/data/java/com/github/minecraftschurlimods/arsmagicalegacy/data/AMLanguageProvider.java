package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

abstract class AMLanguageProvider extends LanguageProvider {
    AMLanguageProvider(PackOutput output, String locale) {
        super(output, ArsMagicaAPI.MOD_ID, locale);
    }
}
