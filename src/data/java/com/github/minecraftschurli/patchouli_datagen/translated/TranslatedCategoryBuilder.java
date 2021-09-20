package com.github.minecraftschurli.patchouli_datagen.translated;

import com.github.minecraftschurli.patchouli_datagen.CategoryBuilder;
import com.github.minecraftschurli.patchouli_datagen.Util;
import net.minecraft.world.item.ItemStack;

public class TranslatedCategoryBuilder extends CategoryBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    protected TranslatedCategoryBuilder(String id, String name, String description, String icon, TranslatedBookBuilder bookBuilder) {
        super(id, name, description, icon, bookBuilder);
    }

    @Override
    public TranslatedCategoryBuilder addSubCategory(String id, String name, String description, ItemStack icon) {
        return addSubCategory(id, name, description, Util.serializeStack(icon));
    }

    @Override
    public TranslatedCategoryBuilder addSubCategory(String id, String name, String description, String icon) {
        var key = "item.%s.%s.%s.%s".formatted(getBookId().getNamespace(), getBookId().getPath(), getId().getPath().replaceAll("/", "."), id);
        putLangKey(key+".name", name);
        putLangKey(key+".description", description);
        return addSubCategory(new TranslatedCategoryBuilder(id, key+".name", key+".description", icon, bookBuilder));
    }

    @Override
    public TranslatedEntryBuilder addEntry(String id, String name, ItemStack icon) {
        return addEntry(id, name, Util.serializeStack(icon));
    }

    @Override
    public TranslatedEntryBuilder addEntry(String id, String name, String icon) {
        var key = "item.%s.%s.%s.%s".formatted(getBookId().getNamespace(), getBookId().getPath(), getId().getPath().replaceAll("/", "."), id);
        putLangKey(key+".name", name);
        return addEntry(new TranslatedEntryBuilder(id, key+".name", icon, this));
    }

    public void putLangKey(String key, String text) {
        bookBuilder.putLangKey(key, text);
    }
}
