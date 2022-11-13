package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public abstract class PrefabSpellProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final String namespace;
    private final DataGenerator generator;
    @Nullable
    private final LanguageProvider languageProvider;

    public PrefabSpellProvider(String namespace, DataGenerator generator) {
        this(namespace, generator, null);
    }

    public PrefabSpellProvider(String namespace, DataGenerator generator, @Nullable LanguageProvider languageProvider) {
        this.namespace = namespace;
        this.generator = generator;
        this.languageProvider = languageProvider;
    }

    protected abstract void createPrefabSpells(Consumer<PrefabSpellBuilder> consumer);

    @Override
    public void run(HashCache pCache) throws IOException {
        Set<ResourceLocation> ids = new HashSet<>();
        createPrefabSpells(consumer -> {
            if (!ids.add(consumer.getId()))
                throw new IllegalStateException("Duplicate prefab spell " + consumer.getId());
            else {
                save(pCache, consumer.build(), generator.getOutputFolder().resolve("data/" + consumer.getId().getNamespace() + "/prefab_spells/" + consumer.getId().getPath() + ".json"));
            }
        });
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
        return new TranslatableComponent(key);
    }

    private static void save(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
        try {
            String s = GSON.toJson(pRecipeJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());
                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
                    bufferedwriter.write(s);
                }
            }
            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save prefab spell {}", pPath, ioexception);
        }
    }
}
