package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;

public class TextPageBuilder extends AbstractPageBuilder<TextPageBuilder> {
    private final String text;
    private final String title;

    public TextPageBuilder(String text, String title, EntryBuilder entryBuilder) {
        super("text", entryBuilder);
        this.text = text;
        this.title = title;
    }

    public TextPageBuilder(String text, EntryBuilder entryBuilder) {
        this(text, null, entryBuilder);
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("text", text);
        if (title != null) {
            json.addProperty("title", title);
        }
    }
}
