package com.github.minecraftschurli.patchouli_datagen;

import com.github.minecraftschurli.patchouli_datagen.page.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EntryBuilder<B extends BookBuilder<B, C, E>, C extends CategoryBuilder<B, C, E>, E extends EntryBuilder<B, C, E>> {

    protected final C parent;
    private final ResourceLocation id;
    private final ResourceLocation category;
    private final String icon;
    private final List<AbstractPageBuilder<?>> pages = new ArrayList<>();
    private String name;
    private ResourceLocation advancement;
    private String flag;
    private Boolean priority;
    private Boolean secret;
    private Boolean readByDefault;
    private Integer sortnum;
    private String turnin;
    private Map<ItemStack, Integer> extraRecipeMappings;

    protected EntryBuilder(String id, String name, String icon, C parent) {
        this.id = new ResourceLocation(parent.getId().getNamespace(), parent.getId().getPath()+'/'+id);
        this.name = name;
        this.category = parent.getId();
        this.icon = icon;
        this.parent = parent;
    }

    public String getLocale() {
        return parent.getLocale();
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("category", category.toString());
        json.addProperty("icon", icon);
        JsonArray pages = new JsonArray();
        for (AbstractPageBuilder<?> page : this.pages) {
            pages.add(page.toJson());
        }
        json.add("pages", pages);
        if (advancement != null) {
            json.addProperty("advancement", advancement.toString());
        }
        if (flag != null) {
            json.addProperty("flag", flag);
        }
        if (priority != null) {
            json.addProperty("priority", priority);
        }
        if (secret != null) {
            json.addProperty("secret", secret);
        }
        if (readByDefault != null) {
            json.addProperty("read_by_default", readByDefault);
        }
        if (sortnum != null) {
            json.addProperty("sortnum", sortnum);
        }
        if (turnin != null) {
            json.addProperty("turnin", turnin);
        }
        if (extraRecipeMappings != null) {
            JsonObject mappings = new JsonObject();
            for (Map.Entry<ItemStack, Integer> entry : extraRecipeMappings.entrySet()) {
                mappings.addProperty(Util.serializeStack(entry.getKey()), entry.getValue());
            }
            json.add("extra_recipe_mappings", mappings);
        }
        this.serialize(json);
        return json;
    }

    protected void serialize(JsonObject json) {
    }

    public C build() {
        return parent;
    }

    public E addSimpleTextPage(String text) {
        return (E) addTextPage(text).build();
    }

    public E addSimpleTextPage(String text, String title) {
        return (E) addTextPage(text, title).build();
    }

    public TextPageBuilder addTextPage(String text) {
        return addTextPage(text, null);
    }

    public TextPageBuilder addTextPage(String text, String title) {
        return addPage(new TextPageBuilder(text, title, this));
    }

    public E addSimpleImagePage(ResourceLocation image, String text, String title) {
        var page = addImagePage(image);
        if (text != null) {
            page.setText(text);
        }
        if (title != null) {
            page.setTitle(title);
        }
        return (E) page.build();
    }

    public ImagePageBuilder addImagePage(ResourceLocation image) {
        return addPage(new ImagePageBuilder(image, this));
    }

    public E addSimpleRecipePage(String type, ResourceLocation recipe) {
        return addSimpleRecipePage(type, recipe, null, null);
    }

    public E addSimpleRecipePage(String type, ResourceLocation recipe, String text) {
        return addSimpleRecipePage(type, recipe, text, null);
    }

    public E addSimpleRecipePage(String type, ResourceLocation recipe, String text, String title) {
        return (E) addRecipePage(type, recipe, text, title).build();
    }

    public E addSimpleDoubleRecipePage(String type, ResourceLocation recipe1, ResourceLocation recipe2) {
        return addSimpleDoubleRecipePage(type, recipe1, recipe2, null, null);
    }

    public E addSimpleDoubleRecipePage(String type, ResourceLocation recipe1, ResourceLocation recipe2, String text) {
        return addSimpleDoubleRecipePage(type, recipe1, recipe2, text, null);
    }

    public E addSimpleDoubleRecipePage(String type, ResourceLocation recipe1, ResourceLocation recipe2, String text, String title) {
        return (E) addRecipePage(type, recipe1, text, title).setRecipe2(recipe2).build();
    }

    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe, String text, String title) {
        var page = addRecipePage(type, recipe);
        if (text != null) {
            page.setText(text);
        }
        if (title != null) {
            page.setTitle(title);
        }
        return page;
    }

    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe) {
        return addPage(new RecipePageBuilder(type, recipe, this));
    }

    public EntityPageBuilder addEntityPage(String entity) {
        return addPage(new EntityPageBuilder(entity, this));
    }

    public EntityPageBuilder addEntityPage(ResourceLocation entity) {
        return addEntityPage(entity.toString());
    }

    public E addSimpleSpotlightPage(ItemStack stack) {
        return addSimpleSpotlightPage(stack, null, null);
    }

    public E addSimpleSpotlightPage(ItemStack stack, String text) {
        return addSimpleSpotlightPage(stack, text, null);
    }

    public E addSimpleSpotlightPage(ItemStack stack, String text, String title) {
        var page = addSpotlightPage(stack);
        if (text != null) {
            page.setText(text);
        }
        if (title != null) {
            page.setTitle(title);
        }
        return (E) page.build();
    }

    public SpotlightPageBuilder addSpotlightPage(ItemStack stack) {
        return addPage(new SpotlightPageBuilder(stack, this));
    }

    public E addSimpleLinkPage(String url, String linkText, String title, String text) {
        var page = addLinkPage(url, linkText);
        if (text != null) {
            page.setText(text);
        }
        if (title != null) {
            page.setTitle(title);
        }
        return (E) page.build();
    }

    public LinkPageBuilder addLinkPage(String url, String linkText) {
        return addPage(new LinkPageBuilder(url, linkText, this));
    }

    public E addSimpleEmptyPage() {
        return (E) addEmptyPage().build();
    }

    public E addSimpleEmptyPage(boolean drawFiller) {
        return (E) addEmptyPage(drawFiller).build();
    }

    public EmptyPageBuilder addEmptyPage() {
        return addPage(new EmptyPageBuilder(true, this));
    }

    public EmptyPageBuilder addEmptyPage(boolean drawFiller) {
        return addPage(new EmptyPageBuilder(drawFiller, this));
    }

    public <T extends AbstractPageBuilder<T>> T addPage(T builder) {
        pages.add(builder);
        return builder;
    }

    public E setAdvancement(Advancement advancement) {
        return setAdvancement(advancement.getId());
    }

    public E setAdvancement(ResourceLocation advancement) {
        this.advancement = advancement;
        return self();
    }

    public E setFlag(String flag) {
        this.flag = flag;
        return self();
    }

    public E setPriority(boolean priority) {
        this.priority = priority;
        return self();
    }

    public E setSecret(boolean secret) {
        this.secret = secret;
        return self();
    }

    public E setReadByDefault(boolean readByDefault) {
        this.readByDefault = readByDefault;
        return self();
    }

    public E setSortnum(int sortnum) {
        this.sortnum = sortnum;
        return self();
    }

    public E setTurnin(String turnin) {
        this.turnin = turnin;
        return self();
    }

    public E addExtraRecipeMapping(ItemStack stack, int index) {
        if (this.extraRecipeMappings == null) {
            this.extraRecipeMappings = new HashMap<>();
        }
        this.extraRecipeMappings.put(stack, index);
        return self();
    }

    protected E self() {
        return (E) this;
    }

    protected int pageCount() {
        return pages.size();
    }

    protected ResourceLocation getId() {
        return id;
    }
}
