package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public record SpellIngredientType<T extends ISpellIngredient>(Codec<T> codec, Codec<T> networkCodec, Lazy<ISpellIngredientRenderer<T>> renderFactory) {
    public static final ResourceKey<Registry<SpellIngredientType<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_ingredient_type"));

    public SpellIngredientType(Codec<T> codec, Supplier<ISpellIngredientRenderer<T>> renderFactory) {
        this(codec, codec, renderFactory);
    }

    public SpellIngredientType(Codec<T> codec, Codec<T> networkCodec, Supplier<ISpellIngredientRenderer<T>> renderFactory) {
        this(codec, networkCodec, Lazy.of(renderFactory));
    }
}
