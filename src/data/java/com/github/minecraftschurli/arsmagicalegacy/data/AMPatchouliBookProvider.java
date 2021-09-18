package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class AMPatchouliBookProvider extends PatchouliBookProvider {
    public AMPatchouliBookProvider(DataGenerator generator, String modid, String locale, boolean includeClient, boolean includeServer) {
        super(generator, modid, locale, includeClient, includeServer);
    }

    @Override
    protected void addBooks(final Consumer<BookBuilder> consumer) {
        createBookBuilder("arcane_compendium")
                .setModel(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"))
                .setCreativeTab(ArsMagicaAPI.get().getItemGroup().getRecipeFolderName())
                .setUseResourcepack()
                .setUseI18n()
                .build(consumer);
    }
}
