package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Supplier;

public record SpellIngredientType<T extends ISpellIngredient>(MapCodec<T> codec, Lazy<ISpellIngredientRenderer<T>> renderFactory) {
    public static final ResourceKey<Registry<SpellIngredientType<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_ingredient_type"));

    public SpellIngredientType(MapCodec<T> codec, Supplier<ISpellIngredientRenderer<T>> renderFactory) {
        this(codec, Lazy.of(renderFactory));
    }
}
