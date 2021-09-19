package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Consumer;

public class AMPatchouliBookProvider extends PatchouliBookProvider {
    private final LanguageProvider lang;

    public AMPatchouliBookProvider(DataGenerator generator, String modid, final LanguageProvider lang, boolean includeClient, boolean includeServer) {
        super(generator, modid, includeClient, includeServer);
        this.lang = lang;
    }

    @Override
    protected void addBooks(final Consumer<BookBuilder<?,?,?>> consumer) {
        createBookBuilder("arcane_compendium", "Arcane Compendium", "Landing Text", lang)
                .setModel(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"))
                .setCreativeTab(ArsMagicaAPI.get().getItemGroup().getRecipeFolderName())
                .setUseResourcepack()
                .addCategory("test", "TestCategory", "This is a test", new ItemStack(Items.BARRIER))
                    .addEntry("test", "TestEntry", new ItemStack(Items.GLOW_BERRIES))
                        .addSimpleTextPage("Lorem ipsum dolor sit")
                    .build()
                .build()
                .build(consumer);
    }
}
