package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class RecipePageBuilder extends AbstractPageBuilder<RecipePageBuilder> {
    private final String recipe;
    private String recipe2;
    private String title;
    private String text;

    public RecipePageBuilder(String type, ResourceLocation recipe, EntryBuilder parent) {
        super(type, parent);
        this.recipe = recipe.toString();
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("recipe", recipe);
        if (recipe2 != null) {
            json.addProperty("recipe2", recipe2);
        }
        if (title != null) {
            json.addProperty("title", title);
        }
        if (text != null) {
            json.addProperty("text", text);
        }
    }

    public <T extends RecipePageBuilder> T setRecipe2(ResourceLocation recipe2) {
        this.recipe2 = recipe2.toString();
        return self();
    }

    public <T extends RecipePageBuilder> T setTitle(String title) {
        this.title = title;
        return self();
    }

    public <T extends RecipePageBuilder> T setText(String text) {
        this.text = text;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected <T extends RecipePageBuilder> T self() {
        return (T) this;
    }

}
