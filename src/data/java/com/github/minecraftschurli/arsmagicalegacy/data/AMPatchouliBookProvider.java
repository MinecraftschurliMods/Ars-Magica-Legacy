package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.github.minecraftschurli.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import com.github.minecraftschurli.patchouli_datagen.page.TextPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.translated.TranslatedBookBuilder;
import com.github.minecraftschurli.patchouli_datagen.translated.TranslatedCategoryBuilder;
import com.github.minecraftschurli.patchouli_datagen.translated.TranslatedEntryBuilder;
import com.google.gson.JsonObject;
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
        ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
        TranslatedBookBuilder builder = createBookBuilder("arcane_compendium", "Arcane Compendium", "A renewed look into Minecraft with a splash of magic...", lang).setVersion("1").setModel(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium")).setCreativeTab(api.getCreativeModeTab().getRecipeFolderName()).setUseResourcepack();
        builder.addCategory("mechanics", "Mechanics", "", new ItemStack(AMItems.MAGITECH_GOGGLES.get()))
               .setSortnum(0)
               .addEntry("crafting_altar", "Crafting Altar", new ItemStack(AMItems.ALTAR_CORE.get()))
                    .addSpotlightPage(new ItemStack(AMItems.ALTAR_CORE.get())).build()
                    .addSimpleMultiblockPage("Crafting Altar", PatchouliCompat.CRAFTING_ALTAR)
               .build();
        builder.addCategory("blocks", "Blocks", "", new ItemStack(AMItems.CHIMERITE_ORE.get())) // TODO: crystal wrench here instead
               .setSortnum(1)
               .build();
        builder.addCategory("items", "Items", "", new ItemStack(AMItems.VINTEUM_DUST.get()))
               .setSortnum(2)
               .build();
/*
                .addCategory("entities", "Entities", "", new ItemStack(AMItems.PURIFIED_VINTEUM_DUST.get()))
                    .setSortnum(3)
                .build()
*/
        TranslatedCategoryBuilder shapes = builder.addCategory("shapes", "Shapes", "", ArsMagicaAPI.MOD_ID + ":textures/icon/skill/touch.png")
                                                  .setSortnum(4);

        TranslatedCategoryBuilder components = builder.addCategory("components", "Components", "", ArsMagicaAPI.MOD_ID + ":textures/icon/skill/dig.png")
                                                      .setSortnum(5);

        TranslatedCategoryBuilder modifiers = builder.addCategory("modifiers", "Modifiers", "", ArsMagicaAPI.MOD_ID + ":textures/icon/skill/target_non_solid.png")
                                                     .setSortnum(6);

        for (ISpellPart spellPart : api.getSpellPartRegistry()) {
            TranslatedCategoryBuilder b = switch (spellPart.getType()) {
                case COMPONENT -> components;
                case MODIFIER -> modifiers;
                case SHAPE -> shapes;
            };
            ResourceLocation registryName = spellPart.getRegistryName();
            String entryLangKey = "item.%s.%s.%s.%s".formatted(b.getBookId().getNamespace(), b.getBookId().getPath(), b.getId().getPath().replaceAll("/", "."), registryName.getPath().replaceAll("/", "."));
            TranslatedEntryBuilder entry = b.addEntry(new TranslatedEntryBuilder(registryName.getPath(), entryLangKey, registryName.getNamespace()+":textures/icon/skill/"+registryName.getPath()+".png", b){});
            String textLangKey = "%s.page0.text".formatted(entryLangKey);
            entry.addPage(new TextPageBuilder(textLangKey, entry)).build()
                 .addPage(new SpellPartPageBuilder(registryName, entry)).build();
        }

/*
                .addCategory("talents", "Talents", "", ArsMagicaAPI.MOD_ID + ":textures/icon/skill/augmented_casting.png")
                    .setSortnum(7)
                .build()
*/
        builder.build(consumer);
    }

    private static class SpellPartPageBuilder extends AbstractPageBuilder<SpellPartPageBuilder> {
        private final ResourceLocation spellPart;

        protected SpellPartPageBuilder(ResourceLocation spellPart, EntryBuilder<?, ?, ?> parent) {
            super(PatchouliCompat.SPELL_PART_PAGE, parent);
            this.spellPart = spellPart;
        }

        @Override
        protected void serialize(JsonObject jsonObject) {
            jsonObject.addProperty("part", spellPart.toString());
        }
    }
}
