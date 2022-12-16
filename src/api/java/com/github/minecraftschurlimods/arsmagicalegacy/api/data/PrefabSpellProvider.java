package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public abstract class PrefabSpellProvider extends AbstractDatapackRegistryProvider<PrefabSpell> {
    private final BiConsumer<String, String> langConsumer;

    public PrefabSpellProvider(String namespace, BiConsumer<String, String> langConsumer) {
        super(PrefabSpell.REGISTRY_KEY, namespace);
        this.langConsumer = langConsumer;
    }

    public void add(String name, String displayName, ResourceLocation icon, ISpell spell) {
        add(name, new PrefabSpell(makeNameComponent(new ResourceLocation(this.namespace, name), displayName), spell, icon));
    }

    private Component makeNameComponent(ResourceLocation id, String name) {
        if (langConsumer == null) return Component.nullToEmpty(name);
        String key = "prefab_spell." + id.getNamespace() + "." + id.getPath().replace('/', '.') + ".name";
        langConsumer.accept(key, name);
        return Component.translatable(key);
    }
}
