package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

public abstract class PrefabSpellProvider extends AbstractDataProvider<PrefabSpellProvider.Builder> {
    @Nullable
    private final LanguageProvider languageProvider;

    public PrefabSpellProvider(String namespace, DataGenerator generator, @Nullable LanguageProvider languageProvider) {
        super("prefab_spells", namespace, generator);
        this.languageProvider = languageProvider;
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
    public Builder builder(String id, Component name, ResourceLocation icon, ISpell spell) {
        return new Builder(new ResourceLocation(this.namespace, id)).setIcon(icon).setName(name).setSpell(spell);
    }

    /**
     * Adds a new prefab spell.
     *
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
        return new TranslatableComponent(key);
    }

    public static class Builder extends AbstractDataBuilder<Builder> {
        private Component name;
        private ResourceLocation icon;
        private ISpell spell;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the name to use.
         *
         * @param name The name to use.
         * @return This builder, for chaining.
         */
        public Builder setName(String name) {
            return setName(Component.nullToEmpty(name));
        }

        /**
         * Sets the name to use.
         *
         * @param name The name to use.
         * @return This builder, for chaining.
         */
        public Builder setName(Component name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the icon to use.
         *
         * @param icon The icon to use.
         * @return This builder, for chaining.
         */
        public Builder setIcon(ResourceLocation icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Sets the spell to use.
         *
         * @param spell The spell to use.
         * @return This builder, for chaining.
         */
        public Builder setSpell(ISpell spell) {
            this.spell = spell;
            return this;
        }

        @Override
        protected JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.add("name", Component.Serializer.toJsonTree(name));
            json.addProperty("icon", icon.toString());
            json.add("spell", ISpell.CODEC.encodeStart(JsonOps.INSTANCE, spell).getOrThrow(false, LogManager.getLogger()::warn));
            return json;
        }
    }
}
