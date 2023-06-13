package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

public abstract class PrefabSpellProvider extends AbstractRegistryDataProvider<PrefabSpell, PrefabSpellProvider.Builder> {
    private final LanguageProvider languageProvider;

    public PrefabSpellProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps, @Nullable LanguageProvider languageProvider) {
        super(PrefabSpell.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps, false);
        this.languageProvider = languageProvider;
        generate();
    }

    @Override
    public String getName() {
        return "Prefab Spells[" + namespace + "]";
    }

    /**
     * @param id    The id of the prefab spell.
     * @param name  The name of the prefab spell.
     * @param icon  The icon of the prefab spell.
     * @param spell The spell of the prefab spell.
     */
    public Builder builder(String id, Component name, ResourceLocation icon, ISpell spell) {
        return new Builder(new ResourceLocation(namespace, id), name, icon, spell);
    }

    /**
     * @param id    The id of the prefab spell.
     * @param name  The name of the prefab spell.
     * @param icon  The icon of the prefab spell.
     * @param spell The spell of the prefab spell.
     */
    public Builder builder(String id, String name, ResourceLocation icon, ISpell spell) {
        return builder(id, makeNameComponent(id, name), icon, spell);
    }

    private Component makeNameComponent(String id, String name) {
        if (languageProvider == null) return Component.nullToEmpty(name);
        String key = "prefab_spell." + namespace + "." + id.replace('/', '.') + ".name";
        languageProvider.add(key, name);
        return Component.translatable(key);
    }

    public static class Builder extends AbstractRegistryDataProvider.Builder<PrefabSpell, Builder> {
        private final Component name;
        private final ResourceLocation icon;
        private final ISpell spell;

        public Builder(ResourceLocation id, Component name, ResourceLocation icon, ISpell spell) {
            super(id, PrefabSpell.DIRECT_CODEC);
            this.name = name;
            this.icon = icon;
            this.spell = spell;
        }

        @Override
        protected PrefabSpell build() {
            return new PrefabSpell(name, spell, icon);
        }
    }
}
