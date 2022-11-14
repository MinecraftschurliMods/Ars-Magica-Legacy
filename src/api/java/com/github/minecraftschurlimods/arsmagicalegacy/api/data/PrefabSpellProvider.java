package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class PrefabSpellProvider implements DataProvider {
    private final String namespace;
    @Nullable
    private final LanguageProvider languageProvider;
    private final Map<ResourceLocation, PrefabSpell> data = new HashMap<>();
    private final JsonCodecProvider<PrefabSpell> provider;

    public PrefabSpellProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this(namespace, generator, existingFileHelper, null);
    }

    public PrefabSpellProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, @Nullable LanguageProvider languageProvider) {
        this.namespace = namespace;
        this.languageProvider = languageProvider;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), PrefabSpell.REGISTRY_KEY, data);
    }

    protected abstract void createPrefabSpells(Consumer<PrefabSpellBuilder> consumer);

    @Override
    public void run(CachedOutput pCache) throws IOException {
        createPrefabSpells(builder -> data.put(builder.getId(), builder.build()));
        provider.run(pCache);
    }

    @Override
    public String getName() {
        return "Prefab Spells[" + namespace + "]";
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param name  The name component of the prefab spell.
     * @param icon  The icon of the prefab spell.
     * @param spell The spell of the prefab spell.
     */
    public PrefabSpellBuilder addPrefabSpell(String id, Component name, ResourceLocation icon, ISpell spell) {
        return new PrefabSpellBuilder(new ResourceLocation(this.namespace, id)).withSpell(spell).withIcon(icon).withName(name);
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param name  The name of the prefab spell.
     * @param icon  The icon of the prefab spell.
     * @param spell The spell of the prefab spell.
     */
    public PrefabSpellBuilder addPrefabSpell(String id, String name, ResourceLocation icon, ISpell spell) {
        return addPrefabSpell(id, makeNameComponent(id, name), icon, spell);
    }

    private Component makeNameComponent(String id, String name) {
        if (languageProvider == null) return Component.nullToEmpty(name);
        String key = "prefab_spell." + namespace + "." + id.replace('/', '.') + ".name";
        languageProvider.add(key, name);
        return Component.translatable(key);
    }
}
