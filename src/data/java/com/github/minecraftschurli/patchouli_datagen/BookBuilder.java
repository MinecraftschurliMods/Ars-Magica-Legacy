package com.github.minecraftschurli.patchouli_datagen;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class BookBuilder<B extends BookBuilder<B, C, E>, C extends CategoryBuilder<B, C, E>, E extends EntryBuilder<B, C, E>> {

    private final List<C> categories = new ArrayList<>();
    private final PatchouliBookProvider provider;
    private final ResourceLocation id;
    protected String name;
    protected String landingText;
    private ResourceLocation bookTexture;
    private String fillerTexture;
    private String craftingTexture;

    private String model;

    private String textColor;
    private String headerColor;
    private String nameplateColor;
    private String linkColor;
    private String linkHoverColor;

    private Boolean useBlockyFont;

    private String progressBarColor;
    private String progressBarBackground;

    private ResourceLocation openSound;
    private ResourceLocation flipSound;

    private Boolean showProgress;

    private String indexIcon;

    private String version;
    private String subtitle;

    private String creativeTab;

    private ResourceLocation advancementsTab;

    private Boolean dontGenerateBook;

    private String customBookItem;

    private Boolean showToasts;

    private Boolean useResourcepack;

    private Boolean i18n;

    protected BookBuilder(ResourceLocation id, String name, String landingText, PatchouliBookProvider provider) {
        this.id = id;
        this.provider = provider;
        this.name = name;
        this.landingText = landingText;
    }

    public String getLocale() {
        return "en_us";
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("landing_text", landingText);
        if (bookTexture != null) {
            json.addProperty("book_texture", bookTexture.toString());
        }
        if (fillerTexture != null) {
            json.addProperty("filler_texture", fillerTexture);
        }
        if (craftingTexture != null) {
            json.addProperty("crafting_texture", craftingTexture);
        }
        if (model != null) {
            json.addProperty("model", model);
        }
        if (textColor != null) {
            json.addProperty("text_color", textColor);
        }
        if (headerColor != null) {
            json.addProperty("header_color", headerColor);
        }
        if (nameplateColor != null) {
            json.addProperty("nameplate_color", nameplateColor);
        }
        if (linkColor != null) {
            json.addProperty("link_color", linkColor);
        }
        if (linkHoverColor != null) {
            json.addProperty("link_hover_color", linkHoverColor);
        }
        if (progressBarColor != null) {
            json.addProperty("progress_bar_color", progressBarColor);
        }
        if (progressBarBackground != null) {
            json.addProperty("progress_bar_background", progressBarBackground);
        }
        if (openSound != null) {
            json.addProperty("open_sound", openSound.toString());
        }
        if (flipSound != null) {
            json.addProperty("flip_sound", flipSound.toString());
        }
        if (indexIcon != null) {
            json.addProperty("index_icon", indexIcon);
        }
        if (showProgress != null) {
            json.addProperty("show_progress", showProgress);
        }
        if (subtitle != null) {
            json.addProperty("subtitle", subtitle);
        } else if (version != null) {
            json.addProperty("version", version);
        } else {
            json.addProperty("subtitle", "item.%s.%s.subtitle".formatted(id.getNamespace(), id.getPath()));
        }
        if (creativeTab != null) {
            json.addProperty("creative_tab", creativeTab);
        }
        if (advancementsTab != null) {
            json.addProperty("advancements_tab", advancementsTab.toString());
        }
        if (dontGenerateBook != null) {
            json.addProperty("dont_generate_book", dontGenerateBook);
        }
        if (customBookItem != null) {
            json.addProperty("custom_book_item", customBookItem);
        }
        if (showToasts != null) {
            json.addProperty("show_toasts", showToasts);
        }
        if (useBlockyFont != null) {
            json.addProperty("use_blocky_font", useBlockyFont);
        }
        if (i18n != null) {
            json.addProperty("i18n", i18n);
        }
        if (useResourcepack != null) {
            json.addProperty("use_resource_pack", useResourcepack);
        }
        this.serialize(json);
        return json;
    }

    protected void serialize(JsonObject json) {
    }

    protected List<C> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void build(Consumer<BookBuilder<?,?,?>> consumer) {
        consumer.accept(self());
    }

    public abstract C addCategory(String id, String name, String description, ItemStack icon);

    public abstract C addCategory(String id, String name, String description, String icon);

    protected <T extends C> T addCategory(T builder) {
        this.categories.add(builder);
        return builder;
    }

    public B setBookTexture(ResourceLocation bookTexture) {
        this.bookTexture = bookTexture;
        return self();
    }

    public B setFillerTexture(String fillerTexture) {
        this.fillerTexture = fillerTexture;
        return self();
    }

    public B setCraftingTexture(String craftingTexture) {
        this.craftingTexture = craftingTexture;
        return self();
    }

    public B setModel(ResourceLocation model) {
        return this.setModel(model.toString());
    }

    public B setModel(String model) {
        this.model = model;
        return self();
    }

    public B setTextColor(String textColor) {
        this.textColor = textColor;
        return self();
    }

    public B setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
        return self();
    }

    public B setNameplateColor(String nameplateColor) {
        this.nameplateColor = nameplateColor;
        return self();
    }

    public B setLinkColor(String linkColor) {
        this.linkColor = linkColor;
        return self();
    }

    public B setLinkHoverColor(String linkHoverColor) {
        this.linkHoverColor = linkHoverColor;
        return self();
    }

    public B setProgressBarColor(String progressBarColor) {
        this.progressBarColor = progressBarColor;
        return self();
    }

    public B setProgressBarBackground(String progressBarBackground) {
        this.progressBarBackground = progressBarBackground;
        return self();
    }

    public B setOpenSound(SoundEvent openSound) {
        this.openSound = openSound.getRegistryName();
        return self();
    }

    public B setOpenSound(ResourceLocation openSound) {
        this.openSound = openSound;
        return self();
    }

    public B setFlipSound(SoundEvent flipSound) {
        this.flipSound = flipSound.getRegistryName();
        return self();
    }

    public B setFlipSound(ResourceLocation flipSound) {
        this.flipSound = flipSound;
        return self();
    }

    public B setIndexIcon(String indexIcon) {
        this.indexIcon = indexIcon;
        return self();
    }

    public B setIndexIcon(ItemStack indexIcon) {
        this.indexIcon = Util.serializeStack(indexIcon);
        return self();
    }

    public B setVersion(String version) {
        this.version = version;
        return self();
    }

    public B setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return self();
    }

    public B setCreativeTab(String creativeTab) {
        this.creativeTab = creativeTab;
        return self();
    }

    public B setAdvancementsTab(ResourceLocation advancementsTab) {
        this.advancementsTab = advancementsTab;
        return self();
    }

    public B setCustomBookItem(ItemStack customBookItem) {
        this.customBookItem = Util.serializeStack(customBookItem);
        return self();
    }

    public B setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        return self();
    }

    public B setDontGenerateBook(boolean dontGenerateBook) {
        this.dontGenerateBook = dontGenerateBook;
        return self();
    }

    public B setShowToasts(boolean showToasts) {
        this.showToasts = showToasts;
        return self();
    }

    public B setUseBlockyFont(boolean useBlockyFont) {
        this.useBlockyFont = useBlockyFont;
        return self();
    }

    public B setUseI18n() {
        this.i18n = true;
        return self();
    }

    public B setUseResourcepack() {
        this.useResourcepack = true;
        return self();
    }

    public boolean useResourcepack() {
        return useResourcepack;
    }

    public boolean useI18n() {
        return i18n;
    }

    public ResourceLocation getId() {
        return id;
    }

    protected B self() {
        return (B) this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BookBuilder && Objects.equals(((BookBuilder) obj).getId(), this.getId());
    }
}
