package com.github.minecraftschurli.patchouli_datagen.regular;

import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;

public class RegularEntryBuilder extends EntryBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    protected RegularEntryBuilder(final String id, final String name, final String icon, final RegularCategoryBuilder parent) {
        super(id, name, icon, parent);
    }
}
