package com.github.minecraftschurli.patchouli_datagen.translated;

import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.github.minecraftschurli.patchouli_datagen.page.LinkPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.page.RecipePageBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TranslatedEntryBuilder extends EntryBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    protected TranslatedEntryBuilder(String id, String name, String icon, TranslatedCategoryBuilder parent) {
        super(id, name, icon, parent);
    }

    public TranslatedEntryBuilder addSimpleImagePage(ResourceLocation image, String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addSimpleImagePage(image, text, title);
    }

    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe, String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addRecipePage(type, recipe, text, title);
    }

    public TranslatedEntryBuilder addSimpleSpotlightPage(ItemStack stack, String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addSimpleSpotlightPage(stack, text, title);
    }

    public TranslatedEntryBuilder addSimpleLinkPage(String url, String linkText, String title, String text) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        if (linkText != null) {
            putLangKey(key+".linkText", linkText);
            linkText = key+".linkText";
        }
        return super.addSimpleLinkPage(url, linkText, title, text);
    }

    public LinkPageBuilder addLinkPage(String url, String linkText) {
        var key = getLangKey();
        if (linkText != null) {
            putLangKey(key+".linkText", linkText);
            linkText = key+".linkText";
        }
        return super.addLinkPage(url, linkText);
    }

    private String getLangKey() {
        return "item.%s.%s.%s.%s".formatted(
                parent.getBookId().getNamespace(),
                parent.getBookId().getPath(),
                getId().getPath().replaceAll("/", "."),
                "page" + pageCount());
    }

    protected void putLangKey(String key, String text) {
        parent.putLangKey(key, text);
    }
}
