package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;

import java.util.function.Consumer;

public class PrefabSpellBuilder {
    private final ResourceLocation id;
    private ISpell spell;
    private Component name;
    private ResourceLocation icon;

    public PrefabSpellBuilder(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    public PrefabSpellBuilder withSpell(ISpell spell) {
        this.spell = spell;
        return this;
    }

    public PrefabSpellBuilder withName(String name) {
        return withName(Component.nullToEmpty(name));
    }

    public PrefabSpellBuilder withName(Component name) {
        this.name = name;
        return this;
    }

    public PrefabSpellBuilder withIcon(ResourceLocation icon) {
        this.icon = icon;
        return this;
    }

    public PrefabSpellBuilder build(Consumer<PrefabSpellBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public PrefabSpell build() {
        return new PrefabSpell(name, spell, icon);
    }
}
