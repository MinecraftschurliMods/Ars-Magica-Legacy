package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Consumer;

class AMPatchouliBookProvider extends PatchouliBookProvider {
    private final LanguageProvider lang;

    AMPatchouliBookProvider(DataGenerator generator, String modid, final LanguageProvider lang, boolean includeClient, boolean includeServer) {
        super(generator, modid, includeClient, includeServer);
        this.lang = lang;
    }

    @Override
    protected void addBooks(Consumer<BookBuilder<?, ?, ?>> consumer) {
        createBookBuilder("arcane_compendium", "Arcane Compendium", "A renewed look into Minecraft with a splash of magic...", lang)
                .setVersion("1")
                .setModel(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"))
                .setCreativeTab(ArsMagicaAPI.get().getCreativeModeTab().getRecipeFolderName())
                .setUseResourcepack()
                .addCategory("mechanics", "Mechanics", "", new ItemStack(AMItems.BLANK_RUNE.get())) //TODO: magitech goggles here instead
                .setSortnum(0).build()
                .addCategory("blocks", "Blocks", "", new ItemStack(AMItems.CHIMERITE_ORE.get())) //TODO: crystal wrench here instead
                .setSortnum(1).build()
                .addCategory("items", "Items", "", new ItemStack(AMItems.VINTEUM_DUST.get()))
                .setSortnum(2).build()
                .addCategory("entities", "Entities", "", new ItemStack(AMItems.PURIFIED_VINTEUM_DUST.get()))
                .setSortnum(3).build()
                .addCategory("shapes", "Shapes", "", "") //TODO: add icon
                .setSortnum(4).build()
                .addCategory("components", "Components", "", "") //TODO: add icon
                .setSortnum(5).build()
                .addCategory("modifiers", "Modifiers", "", "") //TODO: add icon
                .setSortnum(6).build()
                .addCategory("talents", "Talents", "", "") //TODO: add icon
                .setSortnum(7).build()
                .build(consumer);
    }
}
