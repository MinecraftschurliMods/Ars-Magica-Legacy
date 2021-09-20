package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.github.minecraftschurli.patchouli_datagen.Util;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;

public class SpotlightPageBuilder extends AbstractPageBuilder<SpotlightPageBuilder> {
    private final String item;
    private String title;
    private Boolean linkRecipe;
    private String text;

    public SpotlightPageBuilder(ItemStack stack, EntryBuilder parent) {
        super("spotlight", parent);
        this.item = Util.serializeStack(stack);
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("item", item);
        if (title != null) {
            json.addProperty("title", title);
        }
        if (linkRecipe != null) {
            json.addProperty("link_recipe", linkRecipe);
        }
        if (text != null) {
            json.addProperty("text", text);
        }
    }

    public SpotlightPageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SpotlightPageBuilder setLinkRecipe(Boolean linkRecipe) {
        this.linkRecipe = linkRecipe;
        return this;
    }

    public SpotlightPageBuilder setText(String text) {
        this.text = text;
        return this;
    }
}
