package com.github.minecraftschurli.patchouli_datagen.regular;

import com.github.minecraftschurli.patchouli_datagen.CategoryBuilder;
import com.github.minecraftschurli.patchouli_datagen.Util;
import net.minecraft.world.item.ItemStack;

public class RegularCategoryBuilder extends CategoryBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    protected RegularCategoryBuilder(String id, String name, String description, String icon, RegularBookBuilder bookBuilder) {
        super(id, name, description, icon, bookBuilder);
    }

    @Override
    public RegularCategoryBuilder addSubCategory(String id, String name, String description, ItemStack icon) {
        return addSubCategory(id, name, description, Util.serializeStack(icon));
    }

    @Override
    public RegularCategoryBuilder addSubCategory(String id, String name, String description, String icon) {
        return addSubCategory(new RegularCategoryBuilder(id, name, description, icon, bookBuilder));
    }

    @Override
    public RegularEntryBuilder addEntry(String id, String name, ItemStack icon) {
        return addEntry(id, name, Util.serializeStack(icon));
    }

    @Override
    public RegularEntryBuilder addEntry(String id, String name, String icon) {
        return addEntry(new RegularEntryBuilder(id, name, icon, this));
    }
}
