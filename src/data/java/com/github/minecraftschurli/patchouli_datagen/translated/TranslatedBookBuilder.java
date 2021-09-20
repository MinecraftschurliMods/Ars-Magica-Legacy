package com.github.minecraftschurli.patchouli_datagen.translated;

import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import com.github.minecraftschurli.patchouli_datagen.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.LanguageProvider;

public class TranslatedBookBuilder extends BookBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    private final LanguageProvider languageProvider;

    public TranslatedBookBuilder(ResourceLocation id, String name, String landingText, LanguageProvider languageProvider, PatchouliBookProvider provider) {
        super(id, name, landingText, provider);
        this.languageProvider = languageProvider;
        setUseI18n();
        var key = "item.%s.%s".formatted(id.getNamespace(), id.getPath());
        this.name = putLangKey(key+".name", name);
        this.landingText = putLangKey(key+".landing_text", landingText);
    }

    @Override
    public TranslatedCategoryBuilder addCategory(String id, String name, String description, ItemStack icon) {
        return this.addCategory(id, name, description, Util.serializeStack(icon));
    }

    @Override
    public TranslatedCategoryBuilder addCategory(String id, String name, String description, String icon) {
        var key = "item.%s.%s.%s".formatted(getId().getNamespace(), getId().getPath(), id);
        return this.addCategory(new TranslatedCategoryBuilder(id, putLangKey(key+".name", name), putLangKey(key+".description", description), icon, this));
    }

    public String putLangKey(final String key, final String text) {
        languageProvider.add(key, text);
        return key;
    }
}
