package com.github.minecraftschurli.patchouli_datagen.regular;

import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import com.github.minecraftschurli.patchouli_datagen.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RegularBookBuilder extends BookBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    public RegularBookBuilder(ResourceLocation id, String name, String landingText, PatchouliBookProvider provider) {
        super(id, name, landingText, provider);
    }

    @Override
    public RegularCategoryBuilder addCategory(final String id, final String name, final String description, final ItemStack icon) {
        return this.addCategory(id, name, description, Util.serializeStack(icon));
    }

    @Override
    public RegularCategoryBuilder addCategory(final String id, final String name, final String description, final String icon) {
        return this.addCategory(new RegularCategoryBuilder(id, name, description, icon, this));
    }
}
